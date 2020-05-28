package Utils;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Role;

import java.nio.channels.Channel;

public class ReactionRoles {

    private String messageID;
    private String channel;
    private String emoji;
    private Role role;

    public ReactionRoles(String mID, String channel, String emoji, Role role){
        messageID = mID;
        this.channel = channel;
        this.emoji = emoji;
        this.role = role;
    }

    public String getMessageID(){
        return messageID;
    }

    public String getChannel(){
        return channel;
    }

    public String getEmoji(){
        return emoji;
    }

    public Role getRole(){ return role;}
}
