package DiscordBot.Utils;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.MessageReaction;
import net.dv8tion.jda.api.entities.Role;

public class ReactionRoles {

    private String messageID;
    private String channel;
    private MessageReaction.ReactionEmote emoji;
    private Role role;

    public ReactionRoles(String mID, String channel, MessageReaction.ReactionEmote emoji, Role role){
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

    public MessageReaction.ReactionEmote getEmote(){
        return emoji;
    }

    public Role getRole(){ return role;}
}
