package DiscordBot.Tasks.RLMafia.RLMafiaUtils;

import DiscordBot.Tasks.RLMafia.RLMafia;

import java.util.ArrayList;

public abstract class RLMafiaUtils {

    public static void generateTeams(ArrayList<Player> players){

    }

    public static ArrayList<Player> getCorrectGuesses(){
        ArrayList<Vote> votes = RLMafia.getCurrentVotes();

        return ;
    }



    public static String getSummary(){
        String summary;
        String mafiaScore = "";
        String mvpScore;
        StringBuilder winnerScore = new StringBuilder("+2 Points for Town winning the round in-game: ");

        ArrayList<Player> players = RLMafia.getCurrentPlayers();
        ArrayList<Vote> votes = RLMafia.getCurrentVotes();
        ArrayList<Player> guessedCorrect1 = getCorrectGuesses();
        Player mvpPlayer = RLMafia.getMVP();
        Player mafiaPlayer = RLMafia.getMafia();
        int round = RLMafia.getRound();
        String winningTeam = RLMafia.getWinner();

        //guessedCorrect1.stream().map(Player::getName);



        if(mvpPlayer.getRole().equals("Town")){
            mvpScore = "+1 Points for Town becoming the MVP of the round in-game: " + mvpPlayer.getName() + "\n";
        }else{
            mvpScore = "";
        }

        for(int i = 0; i < players.size(); i++){
            Player playerIndex = players.get(i);
            if(playerIndex.getTeam().equals(winningTeam) && playerIndex.getRole().equals("Town")){
                winnerScore.append(playerIndex.getName()).append(" ");
                playerIndex.setScore(playerIndex.getScore() + 2);

            }else if(playerIndex.getRole().equals("Mafia")){
                if(playerIndex.getTeam().equals(winningTeam)){
                    mafiaScore = "-7 Points for failed Mafia, aka winning the round in-game: " + playerIndex;
                    playerIndex.setScore(playerIndex.getScore() - 7);
                }else{
                    mafiaScore = "+7 Points for succeeded Mafia, aka losing the round in-game: " + playerIndex;
                    playerIndex.setScore(playerIndex.getScore() + 7);
                }

            }else if(playerIndex.getRole().equals("Jester")){

            }
        }

        for(int i = 0; i < votes.size(); i++){

        }


        summary = "!! Summary Round " + round + " !!\n" +
                "\n" +
                "// Roles\n" +
                "Mafia: " + mafiaPlayer + "\n" +
                "\n" +
                "// Points \n" +
                winnerScore + "\n" +
                mafiaScore + "\n" +
                mvpScore + "\n" +
                "\n" +
                //"+5 Points for Town voting correctly on the Mafia: " + guessedCorrect1 + "\n" +
                "-2 Points for Mafia being caught by a Towns vote: twoback\n" +
                "+3 Points for Mafia escaping from a Towns vote: twoback\n" +
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
        return summary;
    }
}
