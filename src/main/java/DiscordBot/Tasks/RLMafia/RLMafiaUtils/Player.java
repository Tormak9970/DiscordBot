package DiscordBot.Tasks.RLMafia.RLMafiaUtils;

import net.dv8tion.jda.api.entities.User;

public class Player {
    private String role;
    private String team;
    private String nickname;
    private int score;
    private User thisPlayer;

    public Player(String role, String team, String nickname, int score, User thisPlayer){
        this.role = role;
        this.team = team;
        this.nickname = nickname;
        this.score = score;
        this.thisPlayer = thisPlayer;

    }

    public Player(){
        this.role = "none";
        this.team = "none";
        this.nickname = "none";
        this.score = 0;

    }

    public String getRole(){
        return role;
    }

    public void setRole(String newRole){
        role = newRole;
    }


    public String getTeam(){
        return team;
    }

    public void setTeam(String newTeam){
        role = newTeam;
    }


    public int getScore(){
        return score;
    }

    public void setScore(int newScore){
        score = newScore;
    }


    public String getName(){
        return nickname;
    }

    public void setName(String newName){
        nickname = newName;
    }


    public User getUser(){
        return thisPlayer;
    }
}
