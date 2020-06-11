package DiscordBot.Tasks.Moderation;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ModerationRunner extends Listener {

    public static void passEvent(MessageReceivedEvent event){
        String prefix = "mod" + DatabaseManager.INSTANCE.getPrefix(event.getGuild().getIdLong());
        if(event.getAuthor().isBot()){
            return;
        }else if(SetAdminCommand.getIDs().get(event.getGuild().getIdLong()) != null){
            boolean hasAdmin = false;

            for(int i = 0; i < event.getMember().getRoles().size(); i++){
                if(SetAdminCommand.getIDs().get(event.getGuild().getIdLong()).contains(event.getMember().getRoles().get(i).getIdLong())){
                    hasAdmin = true;
                }
            }

            if(hasAdmin){
                if(event.getMessage().getContentRaw().indexOf(prefix + "clear") == 0){
                    ModerationClearMessagesCommand.getCommand(event);
                }else if(event.getMessage().getContentRaw().equals(prefix + "help")){
                    ModerationHelpCommand.getCommand(event);
                }else if(event.getMessage().getContentRaw().indexOf(prefix + "mute") == 0){
                    MuteCommand.getCommand(event);
                }else if(event.getMessage().getContentRaw().indexOf(prefix + "setadmin") == 0){
                    SetAdminCommand.getCommand(event);
                }
            }

        }else if (SetAdminCommand.getIDs().get(event.getGuild().getIdLong()) == null){
            if(event.getMessage().getContentRaw().indexOf(prefix + "setadmin") == 0){
                SetAdminCommand.getCommand(event);
            }else if(event.getMessage().getContentRaw().equals(prefix + "help")){
                ModerationHelpCommand.getCommand(event);
            }
        }
    }
}
