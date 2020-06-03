package DiscordBot.Tasks.Music;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.io.IOException;

public class MusicRunner extends ListenerAdapter {


    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().indexOf("$musichelp") == 0){
            MusicHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$join") == 0){
            MusicJoinCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$leave") == 0){
            MusicLeaveCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$play") == 0){
            try {
                MusicPlayCommand.getCommand(event);
            } catch (IOException e) {
                event.getChannel().sendMessage("encountered an IOException while reading apikey").queue();
                e.printStackTrace();
            }
        }else if(event.getMessage().getContentRaw().indexOf("$stop") == 0){
            MusicStopCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$queue") == 0){
            MusicQueueCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$skip") == 0){
            MusicSkipCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$clear") == 0){
            MusicClearCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$nowplaying") == 0){
            MusicNowPlayingCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$pause") == 0){
            MusicPauseCommand.getCommand(event);
        }
    }

}
