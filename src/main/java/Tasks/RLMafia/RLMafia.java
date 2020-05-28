package Tasks.RLMafia;

import Utils.Player;

import java.util.ArrayList;

public class RLMafia {

    static ArrayList<Player> currentPlayers = new ArrayList<>();

    public static ArrayList<Player> getCurrentPlayers(){
        return currentPlayers;
    }

    public static void addPlayer(Player newPlayer){
        currentPlayers.add(newPlayer);
    }

    public static void resetPlayers(){
        currentPlayers = new ArrayList<>();
    }

    public static String getSummary(){
        String summary = "";
        Player guessedCorrect1 = new Player();
        Player mvpPlayer;
        Player mafiaPlayer;

        summary = "!! Summary Round 5 !!\n" +
                "\n" +
                "// Roles\n" +
                "Mafia: twoback\n" +
                "\n" +
                "// Points\n" +
                "+2 Points for Town winning the round in-game: snok, alex, hytak\n" +
                "+1 Points for Town becoming the MVP of the round in-game: hytak\n" +
                "+2 Points for succeeded Mafia, aka losing the round in-game: twoback\n" +
                "\n" +
                "+5 Points for Town voting correctly on the Mafia: " + guessedCorrect1.getName() + ", " + guessedCorrect1.getTeam() + "\n" +
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
