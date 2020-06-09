package DiscordBot.Tasks;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GeneralHelpCommand {

    private static String prefix = SetPrefixCommand.getPrefix();
    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(prefix + "generalhelp"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage(prefix + "generalhelp - lists all general commands" +
                    "\n" + prefix + "setprefix - allows you to change the bot's prefix for commands" +
                    "\n" + prefix + "musichelp - help with music commands" +
                    "\n" + prefix + "moderationhelp - help with mod commands" +
                    "\n" + prefix + "rlmafiahelp - help with rlmafia commands" +
                    "\n" + prefix + "memehelp - help with meme commands" +
                    "\n" + prefix + "reactionroles - lets you set up reaction roles(note that this only works when bot is online)" +
                    "\n" + prefix + "nick - use in format like *$nick blank* to add *blank* to beggining of nickname, owners cant use this due to perms" +
                    "\n" + prefix + "serverinfo - gets server info" +
                    "\n" + prefix + "botinfo - gets bot info" +
                    "\n" + prefix + "uptime - gets how long bot has been online" +
                    "\n" + prefix + "addroleonjoin - lets you set up a role to add to users when they join" +
                    "\n" + prefix + "ping - bot responds with pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

        }
    }
}
