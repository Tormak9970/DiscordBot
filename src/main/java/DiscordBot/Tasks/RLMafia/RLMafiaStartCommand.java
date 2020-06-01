package DiscordBot.Tasks.RLMafia;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RLMafiaStartCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$startrlmafia"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("$rename (player's current name) (new nickname) - allows host to change participents nicknames").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

        }
    }
}
