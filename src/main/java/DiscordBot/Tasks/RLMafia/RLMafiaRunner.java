package DiscordBot.Tasks.RLMafia;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RLMafiaRunner extends ListenerAdapter{

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );
        if(RLMafia.getHost() != null){
            if(event.getAuthor().isBot()){
                return;
            }else if(event.getMessage().getContentRaw().equals("$join")){
                RLMafiaJoinCommand.getCommand(event);
            }else if(event.getMessage().getContentRaw().equals("$vote")){

            }else if(event.getMessage().getContentRaw().equals("$rlmafiasettings")){

            }else if(event.getMessage().getContentRaw().equals("$quitrlmafia")){
                RLMafiaQuitCommand.getCommand(event);
            }else{
                return;
            }
        }

    }
}
