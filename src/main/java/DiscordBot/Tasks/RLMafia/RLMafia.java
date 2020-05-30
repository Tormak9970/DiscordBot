package DiscordBot.Tasks.RLMafia;

import DiscordBot.Tasks.RLMafia.RLMafiaUtils.Player;
import DiscordBot.Tasks.RLMafia.RLMafiaUtils.Vote;
import net.dv8tion.jda.api.entities.Member;

import java.util.ArrayList;

public class RLMafia {

    static ArrayList<Player> currentPlayers = new ArrayList<>();
    static ArrayList<Vote> playerVotes = new ArrayList<>();
    static Member host;
    static int round = 0;
    static Player mvp;
    static Player mafia;

    public static ArrayList<Player> getCurrentPlayers(){
        return currentPlayers;
    }

    public static void addPlayer(Player newPlayer){
        currentPlayers.add(newPlayer);
    }

    public static void resetPlayers(){
        currentPlayers = new ArrayList<>();
    }



    public static Player getMVP(){
        return mvp;
    }

    public static void setMVP(Player roundMVP){
        mvp = roundMVP;
    }

    public static void resetMVP(){
        mvp = null;
    }



    public static Player getMafia(){
        return mafia;
    }

    public static void setMafia(Player roundMafia){
        mafia = roundMafia;
    }

    public static void resetMafia(){
        mafia = null;
    }



    public static ArrayList<Vote> getCurrentVotes(){
        return playerVotes;
    }

    public static void addVote(Vote newVote){
        playerVotes.add(newVote);
    }

    public static void resetVotes(){
        playerVotes = new ArrayList<>();
    }



    public static void setHost(Member mem){
        host = mem;
    }

    public static Member getHost(){
        return host;
    }

    public static void clearHost(){
        host = null;
    }



    public static int getRound(){
        return round;
    }

    public static void setRound(int newRound){
        round = newRound;
    }
}
