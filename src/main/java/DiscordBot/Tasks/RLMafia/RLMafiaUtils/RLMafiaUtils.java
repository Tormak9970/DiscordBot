package DiscordBot.Tasks.RLMafia.RLMafiaUtils;

import DiscordBot.Tasks.RLMafia.RLMafia;

import java.util.ArrayList;

public abstract class RLMafiaUtils {

    public static void generateTeams(ArrayList<Player> players){

    }



    public static String getSummary(){
        String summary;
        String mafiaScore = "";
        String mvpStandings = "";
        String jesterStandings = "";

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

        //guessedCorrect1.stream().map(Player::getName);



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
            }else{

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
                "1 |    east | 20 (+5)\n" +
                "2 | twoback | 19 (+3)\n" +
                "3 |    blue | 15 (+5)\n" +
                "4 |   hytak | 10 (+3)\n" +
                "5 |    snok | 10 (+2)\n" +
                "6 |    alex |  5 (+2)";
        RLMafia.updatePlayers(players);
        RLMafia.resetVotes();
        return summary;
    }
}
