package DiscordBot.Tasks.Moderation;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ModerationRunner extends Listener {

    public static void passEvent(MessageReceivedEvent event){
        String prefix = "mod" + DatabaseManager.INSTANCE.getPrefix(event.getGuild().getIdLong());
        String content = event.getMessage().getContentRaw();
        if(event.getAuthor().isBot()){
            return;
        }else if(content.indexOf(prefix + "clear") == 0){
            ModerationClearMessagesCommand.getCommand(event);
        }else if(content.equals(prefix + "help")){
            ModerationHelpCommand.getCommand(event);
        }else if(content.indexOf(prefix + "mute") == 0 && event.getMember().hasPermission(Permission.ADMINISTRATOR)){
            MuteCommand.getCommand(event);
        }
    }
}
