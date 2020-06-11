package DiscordBot.Tasks.Moderation;

import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ModerationRunner extends Listener {

    public static void passEvent(MessageReceivedEvent event){
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "clearmessages") == 0){
            ModerationClearMessagesCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "moderationhelp") == 0){
            ModerationHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "mute") == 0){
            MuteCommand.getCommand(event);
        }
    }
}
