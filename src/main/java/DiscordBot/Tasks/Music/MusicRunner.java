package DiscordBot.Tasks.Music;

import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class MusicRunner extends Listener {


    public static void passEvent(MessageReceivedEvent event){
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "musichelp") == 0){
            MusicHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "join") == 0){
            MusicJoinCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "leave") == 0){
            MusicLeaveCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "play") == 0){
            try {
                MusicPlayCommand.getCommand(event);
            } catch (IOException e) {
                event.getChannel().sendMessage("encountered an IOException while reading apikey").queue();
                e.printStackTrace();
            }
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "stop") == 0){
            MusicStopCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "queue") == 0){
            MusicQueueCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "skip") == 0){
            MusicSkipCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "clear") == 0){
            MusicClearCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "nowplaying") == 0){
            MusicNowPlayingCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "pause") == 0){
            MusicPauseCommand.getCommand(event);
        }
    }

    /*
    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );

    }

     */

}
