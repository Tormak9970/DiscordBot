package DiscordBot.Tasks;

import DiscordBot.Tasks.Moderation.ModerationRunner;
import DiscordBot.Tasks.Music.MusicRunner;
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
        if(event.getMessage().getContentRaw().indexOf("m") == 0){
            MusicRunner.passEvent(event);
        }else if (event.getMessage().getContentRaw().indexOf("mod") == 0){
            ModerationRunner.passEvent(event);
        }else if (event.getMessage().getContentRaw().indexOf("rlmafia") == 0){
            RLMafiaRunner.passEvent(event);
        }else{
            CommandsRunner.passEvent(event);
        }

    }
}
