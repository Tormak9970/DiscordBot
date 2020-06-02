package DiscordBot.Tasks.Music;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

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
            //MusicHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$join") == 0){
            MusicJoinCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$leave") == 0){
            MusicLeaveCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$play") == 0){
            MusicPlayCommand.getCommand(event);
        }
    }

}
