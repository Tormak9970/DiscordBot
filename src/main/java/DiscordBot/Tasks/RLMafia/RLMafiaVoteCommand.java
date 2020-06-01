package DiscordBot.Tasks.RLMafia;

import DiscordBot.Tasks.RLMafia.RLMafiaUtils.Player;
import DiscordBot.Tasks.RLMafia.RLMafiaUtils.Vote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

import static java.util.function.Predicate.isEqual;

public class RLMafiaVoteCommand {

    public static void getCommand(MessageReceivedEvent event)
    {


        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        ArrayList<Vote> votes = RLMafia.getCurrentVotes();

        if (content.indexOf("$vote") == 0){
            ArrayList<Player> players = RLMafia.getCurrentPlayers();
            String playerVotedFor = event.getMessage().getContentRaw().substring(6);
            MessageChannel channel = event.getChannel();
            if(players.stream().map(Player::getName).anyMatch(isEqual(playerVotedFor))){
                channel.sendMessage("Your vote has been registered").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
                RLMafia.addVote(new Vote(event.getMember(), playerVotedFor));
            }else if(RLMafia.getCurrentVotes().size() > 0 && votes.stream().map(Vote::getVoter).anyMatch(isEqual(event.getMember()))){
                channel.sendMessage("invalid vote, you have voted before").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

            }else{
                channel.sendMessage("invalid vote, your victim isn't playing").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

            }
        }
    }
}
