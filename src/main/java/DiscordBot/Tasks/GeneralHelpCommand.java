package DiscordBot.Tasks;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GeneralHelpCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$generalhelp"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("$generalhelp - lists all general commands" +
                    "\n$musichelp - help with music commands" +
                    "\n$rlmafiahelp - help with rlmafia commands" +
                    "\n$memehelp - help with meme commands" +
                    "\n$reactionroles - lets you set up reaction roles(note that this only works when bot is online)" +
                    "\n$getinvite - allows you to get an invite link to run bot on your servers" +
                    "\n$nick - use in format like *$nick blank* to add *blank* to beggining of nickname, owns cant use this due to perms" +
                    "\n$ping - bot responds with pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

        }
    }
}
