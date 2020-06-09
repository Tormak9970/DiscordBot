package DiscordBot.Tasks.RLMafia;

import DiscordBot.Tasks.RLMafia.RLMafiaUtils.Player;
import DiscordBot.Tasks.RLMafia.RLMafiaUtils.RLMafiaUtils;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class RLMafiaMVPCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf(SetPrefixCommand.getPrefix() + "mvp") == 0)
        {
            ArrayList<Player> players = RLMafia.getCurrentPlayers();
            for(Player playerIndex : players){
                if(playerIndex.getName().equals(content.substring(SetPrefixCommand.getPrefix().length() - 1 + 4))){
                    RLMafia.setMVP(playerIndex);
                    RLMafia.setWinner(playerIndex.getTeam());
                }
            }

            event.getChannel().sendMessage(RLMafiaUtils.getSummary()).queue();

            RLMafiaUtils.generateTeams(RLMafia.getCurrentPlayers(), event);
            RLMafiaUtils.generateRoles(RLMafia.getCurrentPlayers(), event);

            MessageChannel channel = event.getChannel();
            channel.sendMessage("Round " + RLMafia.getRound() + " has started").queue();

        }
    }
}
