package DiscordBot.Tasks;

import DiscordBot.Tasks.RLMafia.RLMafiaRunner;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Listener extends ListenerAdapter {

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );
        if(event.getAuthor().isBot()){
            return;
        }
        CommandsRunner.passEvent(event);
        RLMafiaRunner.passEvent(event);

    }
}
