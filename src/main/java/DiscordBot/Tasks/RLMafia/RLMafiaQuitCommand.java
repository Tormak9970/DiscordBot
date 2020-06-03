package DiscordBot.Tasks.RLMafia;

import DiscordBot.DiscordBotMain;
import DiscordBot.Tasks.RLMafia.RLMafiaUtils.RLMafiaUtils;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RLMafiaQuitCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf("$quitrlmafia") == 0)
        {
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
}
