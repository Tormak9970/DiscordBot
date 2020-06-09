package DiscordBot.Tasks.Music;

import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class MusicRunner extends Listener {


    public static void passEvent(MessageReceivedEvent event){
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "musichelp") == 0){
            MusicHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "join") == 0){
            MusicJoinCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "leave") == 0){
            MusicLeaveCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "play") == 0){
            try {
                MusicPlayCommand.getCommand(event);
            } catch (IOException e) {
                event.getChannel().sendMessage("encountered an IOException while reading apikey").queue();
                e.printStackTrace();
            }
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "stop") == 0){
            MusicStopCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "queue") == 0){
            MusicQueueCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "skip") == 0){
            MusicSkipCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "clear") == 0){
            MusicClearCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "nowplaying") == 0){
            MusicNowPlayingCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix() + "pause") == 0){
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
