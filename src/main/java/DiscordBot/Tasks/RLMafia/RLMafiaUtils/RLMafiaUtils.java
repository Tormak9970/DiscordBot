package DiscordBot.Tasks.RLMafia.RLMafiaUtils;

import DiscordBot.Tasks.RLMafia.RLMafia;

import java.util.ArrayList;

public abstract class RLMafiaUtils {

    public static void generateTeams(ArrayList<Player> players){

    }

    public static Player getCorrectGuesses(){
        return new Player();
    }



    public static String getSummary(){
        String summary = "";
        //ArrayList<Player> guessedCorrect1 = ;
        Player mvpPlayer = RLMafia.getMVP();
        Player mafiaPlayer = RLMafia.getMafia();
        int round = RLMafia.getRound();
        String WinningTeam = RLMafia.getWinner();
        String mafiaScore = "";

        if(RLMafia.getMafia().getTeam().equals(WinningTeam)){
            mafiaScore = "-7 Points for failed Mafia, aka winning the round in-game: " + mafiaPlayer + "\n";
        }else{
            mafiaScore = "+2 Points for succeeded Mafia, aka losing the round in-game: " + mafiaPlayer + "\n";
        }
        //guessedCorrect1.stream().map(Player::getName)

        summary = "!! Summary Round " + round + " !!\n" +
                "\n" +
                "// Roles\n" +
                "Mafia: " + mafiaPlayer + "\n" +
                "\n" +
                "// Points \n" +
                "+2 Points for Town winning the round in-game: snok, alex, hytak\n" +
                "+1 Points for Town becoming the MVP of the round in-game: hytak\n" +
                mafiaScore +
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
