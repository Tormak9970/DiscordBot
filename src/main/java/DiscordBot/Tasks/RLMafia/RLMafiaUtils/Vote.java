package DiscordBot.Tasks.RLMafia.RLMafiaUtils;

import net.dv8tion.jda.api.entities.Member;

public class Vote {

    private Member voter;
    private String choice;

    public Vote(Member mem, String vote){
        voter = mem;
        choice = vote;
    }

    public String getChoice() {
        return choice;
    }

    public void setChoice(String choice) {
        this.choice = choice;
    }

    public Member getVoter() {
        return voter;
    }

    public void setVoter(Member voter) {
        this.voter = voter;
    }
}
