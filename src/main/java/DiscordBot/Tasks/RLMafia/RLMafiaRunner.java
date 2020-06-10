package DiscordBot.Tasks.RLMafia;

import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class RLMafiaRunner extends Listener {

    public static void passEvent(MessageReceivedEvent event){
        if(RLMafia.getHost() != null){
            if(event.getAuthor().isBot()){
                return;
            }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "mafiajoin") == 0){
                RLMafiaJoinCommand.getCommand(event);
            }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "vote") == 0){
                RLMafiaVoteCommand.getCommand(event);
            }

            if(RLMafia.getHost().equals(event.getAuthor())){
                if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "mvp") == 0){

                }else if(event.getMessage().getContentRaw().indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "quitrlmafia") == 0){
                    RLMafiaQuitCommand.getCommand(event);
                }else if(event.getMessage().getContentRaw().equals(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "startrlmafia")){
                    RLMafiaStartCommand.getCommand(event);
                }
            }
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
