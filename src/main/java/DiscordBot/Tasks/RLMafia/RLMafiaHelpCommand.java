package DiscordBot.Tasks.RLMafia;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RLMafiaHelpCommand {


    public static void getCommand(MessageReceivedEvent event)
    {
        String prefix = "rlmafia" + SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        MessageChannel channel = event.getChannel();
        channel.sendMessage(
                prefix + "host - starts game, no other mafia commands will work unless you have started a game, sets sender as host" +
                        "\n" + prefix + "join (nickname) - adds you to current mafia game with name (nickname)" +
                        "\n" + prefix + "settings - allows you to customize the experience" +
                        "\n" + prefix + "quit - ends game, and resets everything" +
                        "\n" + prefix + "vote - cast your vote on who u think the mafia is" +
                        "\n" + prefix + "mvp - set the round's mvp" +
                        "\n" + prefix + "start - starts the first round" +
                        "\n" + prefix + "winner - sets the winning team" +
                        "\n" + prefix + "rename (player's current name) (new nickname) - allows host to change participents nicknames").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }
}
