package DiscordBot.Tasks.Moderation;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ModerationHelpCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        String prefix = "mod" + SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        MessageChannel channel = event.getChannel();
        channel.sendMessage(prefix + "help - lists moderation commands" +
                "\n" + prefix + "clear (specify amount) - deletes specified amount of messages from channel command is sent in" +
                "\n" + prefix + "mute (member) (optional length) - if not specified, defaults to 5 minutes").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

    }
}
