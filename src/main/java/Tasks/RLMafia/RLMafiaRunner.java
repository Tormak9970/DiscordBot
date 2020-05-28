package Tasks.RLMafia;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RLMafiaRunner extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().equals("$rlmafiahelp")){
            RLMafiaHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$join")){
            JoinCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$vote")){

        }else if(event.getMessage().getContentRaw().equals("$rlmafiasettings")){

        }else if(event.getMessage().getContentRaw().equals("$hostmafia")){

        }else if(event.getMessage().getContentRaw().equals("$quitrlmafia")){

        }else{
            return;
        }

    }
}
