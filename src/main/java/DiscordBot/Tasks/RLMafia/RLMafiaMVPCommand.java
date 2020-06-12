package DiscordBot.Tasks.RLMafia;

import DiscordBot.Database.DatabaseManager;
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
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        ArrayList<Player> players = RLMafia.getCurrentPlayers();
        for(Player playerIndex : players){
            if(playerIndex.getName().equals(content.substring(7 + SetPrefixCommand.getPrefix(event.getGuild().getIdLong()).length() + 4))){
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
