package DiscordBot.Tasks.RLMafia;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RLMafiaRunner extends Listener {

    public static void passEvent(MessageReceivedEvent event){
        String prefix = "rlmafia" + DatabaseManager.INSTANCE.getPrefix(event.getGuild().getIdLong());
        if(RLMafia.getHost() != null){
            if(event.getAuthor().isBot()){
                return;
            }else if(event.getMessage().getContentRaw().indexOf(prefix + "join") == 0){
                RLMafiaJoinCommand.getCommand(event);
            }else if(event.getMessage().getContentRaw().indexOf(prefix + "vote") == 0){
                RLMafiaVoteCommand.getCommand(event);
            }

            if(RLMafia.getHost().equals(event.getAuthor())){
                if(event.getMessage().getContentRaw().indexOf(prefix + "mvp") == 0){

                }else if(event.getMessage().getContentRaw().equals(prefix + "quit")){
                    RLMafiaQuitCommand.getCommand(event);
                }else if(event.getMessage().getContentRaw().equals(prefix + "start")){
                    RLMafiaStartCommand.getCommand(event);
                }
            }
        }
    }
}
