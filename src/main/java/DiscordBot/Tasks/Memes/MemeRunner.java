package DiscordBot.Tasks.Memes;

import DiscordBot.Tasks.Listener;

import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MemeRunner extends Listener {

    public static void passEvent(MessageReceivedEvent event){
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "meme") == 0){
            MemeCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "memehelp") == 0){
            MemeHelpCommand.getCommand(event);
        }
    }
}
