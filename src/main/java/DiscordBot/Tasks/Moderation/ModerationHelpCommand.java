package DiscordBot.Tasks.Moderation;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class ModerationHelpCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        String prefix = "mod" + SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        MessageChannel channel = event.getChannel();
        channel.sendMessage(prefix + "help - lists moderation commands" +
                "\n" + prefix + "clear (specify amount) - deletes specified amount of messages from channel command is sent in" +
                "\n" + prefix + "mute (member) (optional length) - if not specified, defaults to 5 minutes" +
                "\n" + prefix + "kicks (member) (reason) - asks for confirmation, and if so, kicks member specified" +
                "\n" + prefix + "ban (member) (delete messages from last **X** days would be **X**) (reason)- asks for confirmation, and if so, bans member specified" +
                "\n" + prefix + "banword (word) - adds that word to the banned words list" +
                "\n" + prefix + "getbannedwords - returns a list of all banned words for current server" +
                "\n" + prefix + "removebannedword (word) - removes word from ban list").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

    }
}
