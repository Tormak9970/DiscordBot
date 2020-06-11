package DiscordBot.Tasks.Moderation;

import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ModerationRunner extends Listener {

    public static void passEvent(MessageReceivedEvent event){
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
                if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "clearmessages") == 0){
                    ModerationClearMessagesCommand.getCommand(event);
                }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "moderationhelp") == 0){
                    ModerationHelpCommand.getCommand(event);
                }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "mute") == 0){
                    MuteCommand.getCommand(event);
                }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "setadmin") == 0){
                    SetAdminCommand.getCommand(event);
                }
            }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "clearmessages") == 0
                    || event.getMessage().getContentRaw().equals(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "moderationhelp")
                    || event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "mute") == 0
                    || event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "setadmin") == 0){
                event.getChannel().sendMessage("You are not allowed to use that command").queue();
            }

        }else if (SetAdminCommand.getIDs().get(event.getGuild().getIdLong()) == null){
            if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "setadmin") == 0){
                SetAdminCommand.getCommand(event);
            }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "moderationhelp") == 0){
                ModerationHelpCommand.getCommand(event);
            }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "clearmessages") == 0
                    || event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "mute") == 0){
                event.getChannel().sendMessage("You are not allowed to use that command, please see moderationhelp for explination").queue();
            }
        }
    }
}
