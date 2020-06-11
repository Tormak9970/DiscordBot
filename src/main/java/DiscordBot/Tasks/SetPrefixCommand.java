package DiscordBot.Tasks;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class SetPrefixCommand {

    private static Map<Long, String> prefixes = new HashMap<>();

    public static void getCommand(MessageReceivedEvent event){
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf(getPrefix(event.getGuild().getIdLong()) + "setprefix") == 0)
        {
            MessageChannel channel = event.getChannel();

            prefixes.replace(event.getGuild().getIdLong(), event.getMessage().getContentRaw().substring(getPrefix(event.getGuild().getIdLong()).length() + 10));
            channel.sendMessage("prefix has been set to `" + getPrefix(event.getGuild().getIdLong()) + "`").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

        }
    }

    public static String getPrefix(long guildID){
        prefixes.putIfAbsent(guildID, "$");
        return prefixes.get(guildID);
    }

    public static String getDefaultPrefix(){
        return "$";
    }
}
