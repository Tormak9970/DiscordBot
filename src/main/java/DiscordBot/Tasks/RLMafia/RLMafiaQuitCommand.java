package DiscordBot.Tasks.RLMafia;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.DiscordBotMain;
import DiscordBot.Tasks.RLMafia.RLMafiaUtils.RLMafiaUtils;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RLMafiaQuitCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        RLMafia.resetWinner();
        RLMafia.clearHost();
        RLMafia.resetPlayers();
        RLMafia.resetVotes();
        RLMafia.setRound(0);
        RLMafia.resetMVP();
        RLMafia.resetMafia();
        RLMafia.resetJester();

        MessageChannel channel = event.getChannel();
        channel.sendMessage("The game has ended. Thanks for playing!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }
}
