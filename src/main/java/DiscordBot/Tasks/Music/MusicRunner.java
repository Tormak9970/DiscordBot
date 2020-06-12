package DiscordBot.Tasks.Music;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.IOException;

public class MusicRunner extends Listener {


    public static void passEvent(MessageReceivedEvent event){
        String prefix = "m" + SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().equals(prefix + "help")){
            MusicHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "join")){
            MusicJoinCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "leave")){
            MusicLeaveCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(prefix + "play") == 0){
            try {
                MusicPlayCommand.getCommand(event);
            } catch (IOException e) {
                event.getChannel().sendMessage("encountered an IOException while reading apikey").queue();
                e.printStackTrace();
            }
        }else if(event.getMessage().getContentRaw().equals(prefix + "stop")){
            MusicStopCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "queue")){
            MusicQueueCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "skip")){
            MusicSkipCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "clear")){
            MusicClearCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "nowplaying")){
            MusicNowPlayingCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "pause")){
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
