package DiscordBot.Tasks.Moderation;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ModerationRunner extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().indexOf("$clearmessages") == 0){
            ModerationClearMessagesCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$moderationhelp") == 0){
            ModerationHelpCommand.getCommand(event);
        }


    }
}
