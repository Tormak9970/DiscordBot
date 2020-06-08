package DiscordBot.Utils;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.Role;

public class ReactionRoles {

    private long messageID;
    private long channel;
    private long emoji;
    private long role;

    public ReactionRoles(long mID, long channel, long emoji, long role){
        messageID = mID;
        this.channel = channel;
        this.emoji = emoji;
        this.role = role;
    }

    public long getMessageID(){
        return messageID;
    }

    public long getChannelID(){
        return channel;
    }

    public long getEmoteID(){
        return emoji;
    }

    public long getRoleID(){ return role;}
}
