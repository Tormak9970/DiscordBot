package DiscordBot.Tasks.RLMafia;

import DiscordBot.DiscordBotMain;
import DiscordBot.Utils.Player;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

import static java.util.function.Predicate.isEqual;

public class RLMafiaVoteCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)

        if (event.getAuthor().isBot()){
            return;
            // We don't want to respond to other bot accounts, including ourself
        }else if (content.indexOf("$vote") == 0){
            ArrayList<Player> players = RLMafia.getCurrentPlayers();
            String playerVotedFor = event.getMessage().getContentRaw().substring(6);
            MessageChannel channel = event.getChannel();
            if(players.stream().map(Player::getName).anyMatch(isEqual(playerVotedFor))){
                channel.sendMessage("Your vote has been registered").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
                
            }else{
                channel.sendMessage("Please re-vote, the vote u cast was invalid").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

            }
        }else{
            return;
        }
    }
}
