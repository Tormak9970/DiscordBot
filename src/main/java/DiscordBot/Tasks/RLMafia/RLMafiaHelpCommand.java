package DiscordBot.Tasks.RLMafia;

import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RLMafiaHelpCommand {

    private static String prefix = SetPrefixCommand.getPrefix();
    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(prefix + "rlmafiahelp"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage(
                    prefix + "hostmafia - starts game, no other mafia commands will work unless you have started a game, sets sender as host" +
                    "\n" + prefix + "mafiajoin (nickname) - adds you to current mafia game with name (nickname)" +
                    "\n" + prefix + "rlmafiasettings - allows you to customize the experience" +
                    "\n" + prefix + "quitrlmafia - ends game, and resets everything" +
                    "\n" + prefix + "vote - cast your vote on who u think the mafia is" +
                    "\n" + prefix + "mvp - set the round's mvp" +
                    "\n" + prefix + "startrlmafia - starts the first round" +
                    "\n" + prefix + "winner - sets the winning team" +
                    "\n" + prefix + "rename (player's current name) (new nickname) - allows host to change participents nicknames").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

        }
    }
}
