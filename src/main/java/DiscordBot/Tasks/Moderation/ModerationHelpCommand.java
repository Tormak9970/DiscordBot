package DiscordBot.Tasks.Moderation;

import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class ModerationHelpCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "moderationhelp"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage(
                    SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "clearmessages (specify amount) - deletes specified amount of messages from channel command is sent in").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

        }
    }
}
