package DiscordBot.Tasks;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class SetPrefixCommand {

    private static String prefix = "$";

    public static void getCommand(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(prefix + "setprefix"))
        {
            MessageChannel channel = event.getChannel();

            prefix = event.getMessage().getContentRaw().substring((prefix.length() - 1) + 10);
            channel.sendMessage("prefix has been set to `" + prefix + "`").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

        }
    }

    public static String getPrefix(){
        return prefix;
    }
}
