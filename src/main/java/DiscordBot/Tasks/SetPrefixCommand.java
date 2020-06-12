package DiscordBot.Tasks;

import DiscordBot.Database.DatabaseManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.HashMap;
import java.util.Map;

public class SetPrefixCommand {

    private static Map<Long, String> prefixes = new HashMap<>();
    private static String defaultPrefix = "$";

    public static void getCommand(MessageReceivedEvent event){

        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        long guildID = event.getGuild().getIdLong();
        MessageChannel channel = event.getChannel();

        updatePrefix(guildID, event.getMessage().getContentRaw().substring(SetPrefixCommand.getPrefix(guildID).length() + 10));
        channel.sendMessage("prefix has been set to `" + SetPrefixCommand.getPrefix(guildID) + "`").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }


    public static String getPrefix(long guildID){
        return  prefixes.computeIfAbsent(guildID, DatabaseManager.INSTANCE::getPrefix);
    }



    public static String getDefaultPrefix(){
        return defaultPrefix;
    }

    private static void updatePrefix(long guildId, String newPrefix) {
        prefixes.replace(guildId, newPrefix);
        DatabaseManager.INSTANCE.setPrefix(guildId, newPrefix);
    }
}
