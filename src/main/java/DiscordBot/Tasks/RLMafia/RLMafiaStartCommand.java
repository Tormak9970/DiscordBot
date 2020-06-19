package DiscordBot.Tasks.RLMafia;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.RLMafia.RLMafiaUtils.RLMafiaUtils;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RLMafiaStartCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        RLMafiaUtils.generateTeams(RLMafia.getCurrentPlayers(), event);
        RLMafiaUtils.generateRoles(RLMafia.getCurrentPlayers(), event);

        MessageChannel channel = event.getChannel();
        channel.sendMessage("Round " + RLMafia.getRound() + " has started").queue();
    }
}
