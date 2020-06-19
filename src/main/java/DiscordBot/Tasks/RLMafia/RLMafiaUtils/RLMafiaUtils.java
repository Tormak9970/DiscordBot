package DiscordBot.Tasks.RLMafia.RLMafiaUtils;

import DiscordBot.Tasks.RLMafia.RLMafia;
import DiscordBot.Utils.Utils;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import javax.print.DocFlavor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public abstract class RLMafiaUtils {

    public static void generateTeams(ArrayList<Player> players, GuildMessageReceivedEvent event){
        StringBuilder blueTeam = new StringBuilder("");
        StringBuilder orangeTeam = new StringBuilder("");
        Collections.shuffle(players);

        for (int i = 0; i < players.size(); i++) {
            if(i < players.size()/2){
                players.get(i).setTeam("blue");
                blueTeam.append(players.get(i).getName()).append(" ");
            }else{
                players.get(i).setTeam("orange");
                orangeTeam.append(players.get(i).getName()).append(" ");
            }
        }

        RLMafia.updatePlayers(players);
        event.getMessage().getChannel().sendMessage("Blue Team: " + blueTeam + " || Orange Team: " + orangeTeam).queue();
    }

    public static void generateRoles(ArrayList<Player> players, GuildMessageReceivedEvent event){
        int mafiaIndex = players.size() - 2;
        int jesterIndex = 1;
        double randNum = Math.random() * 10;
        boolean isJester = true;

        if(randNum > 5){
            isJester = false;
        }

        if(isJester){
            players.get(jesterIndex);
        }
        for(int i = 0; i < players.size(); i++){
            if(i == mafiaIndex){
                players.get(i).setRole("Mafia");
                Utils.sendPrivateMessage(players.get(i).getUser(), "You are the Mafia!");
            }else if(isJester && i == jesterIndex){
                players.get(i).setRole("Jester");
                Utils.sendPrivateMessage(players.get(i).getUser(), "You are the Jester!");
            }else{
                players.get(i).setRole("Town");
                Utils.sendPrivateMessage(players.get(i).getUser(), "You are a towns person");
            }
        }

        RLMafia.updatePlayers(players);
        event.getMessage().getChannel().sendMessage("Roles have been selected.").queue();
    }



    public static String getSummary(){
        String summary;
        String mafiaScore = "";
        String mvpStandings = "";
        String jesterStandings = "";

        StringBuilder getStandings = new StringBuilder("");
        StringBuilder winnerScore = new StringBuilder("+2 Points for Town winning the round in-game: ");
        StringBuilder correctVoters = new StringBuilder("");

        ArrayList<Player> players = RLMafia.getCurrentPlayers();
        ArrayList<Vote> votes = RLMafia.getCurrentVotes();
        Player mvpPlayer = RLMafia.getMVP();
        Player mafiaPlayer = RLMafia.getMafia();
        Player jesterPlayer = RLMafia.getJester();
        int round = RLMafia.getRound();
        int caughtPoints = 0;
        int jesterScore = 0;
        String winningTeam = RLMafia.getWinner();



        if(mvpPlayer.getRole().equals("Town")){
            mvpStandings = "+1 Points for Town becoming the MVP of the round in-game: " + mvpPlayer.getName() + "\n";
        }else{
            mvpStandings = "";
        }

        for (Vote voteIndex : votes) {
            if (voteIndex.getChoice().equals(mafiaPlayer.getName())) {
                correctVoters.append(voteIndex.getVoter().getUser().getName()).append(" ");
                caughtPoints++;
            } else if(voteIndex.getChoice().equals(jesterPlayer.getName())){
                jesterScore++;
            }
        }


        for (Player playerIndex : players) {
            if(correctVoters.indexOf(playerIndex.getName()) >= 0){
                playerIndex.setScore(playerIndex.getScore() + 5);
            }

            if (playerIndex.getTeam().equals(winningTeam) && playerIndex.getRole().equals("Town")) {
                winnerScore.append(playerIndex.getName()).append(" ");
                playerIndex.setScore(playerIndex.getScore() + 2);

            } else if (playerIndex.getRole().equals("Mafia")) {
                if (playerIndex.getTeam().equals(winningTeam)) {
                    mafiaScore = "-7 Points for failed Mafia, aka winning the round in-game: " + playerIndex;
                    playerIndex.setScore(playerIndex.getScore() - 7);
                } else {
                    mafiaScore = "+7 Points for succeeded Mafia, aka losing the round in-game: " + playerIndex;
                    playerIndex.setScore(playerIndex.getScore() + 7);
                }

            } else if (playerIndex.getRole().equals("Jester")) {
                playerIndex.setScore(playerIndex.getScore() + jesterScore);
            }
        }

        Comparator<Player> byScore = Comparator.comparingInt(Player::getScore);

        players.sort(byScore);

        for(int i = 0; i < players.size(); i++){
            String score = "";
            String name = "";
            StringBuilder spacesOffset = new StringBuilder("");

            for(int j = 0; j < (7 - players.get(i).getName().length()); j++){
                spacesOffset.append(" ");
            }
            if(players.get(i).getName().length() < 7){
                name = spacesOffset + players.get(i).getName();
            }else{
                name = players.get(i).getName();
            }

            if(players.get(i).getScore() < 10){
                score = " " + players.get(i).getScore();
            }else{
                score = "" + players.get(i).getScore();
            }

            getStandings.append(i).append(" | ").append(name).append(" | ").append(score).append("\n");
        }

        summary = "!! Summary Round " + round + " !!\n" +
                "\n" +
                "// Roles\n" +
                "Mafia: " + mafiaPlayer + "\n" +
                "\n" +
                "// Points \n" +
                winnerScore + "\n" +
                mafiaScore + "\n" +
                mvpStandings + "\n" +
                "\n" +
                "+5 Points for Town voting correctly on the Mafia: " + correctVoters + "\n" +
                "-" + caughtPoints + " Points for Mafia being caught by a Towns vote: " + mafiaPlayer + "\n" +
                (players.size() - (caughtPoints + 1)) + " Points for Mafia escaping from a Towns vote: " + mafiaPlayer + "\n" +
                jesterStandings + "\n" +
                "\n" +
                "// Standings\n" +
                "# | Player  | Points \n" +
                "--+---------+--------\n" +
                getStandings;
        RLMafia.updatePlayers(players);
        RLMafia.resetVotes();
        return summary;
    }
}
