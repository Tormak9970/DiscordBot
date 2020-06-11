package DiscordBot.Tasks.Moderation;

import DiscordBot.Database.DatabaseManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SetAdminCommand {

    private static Map<Long, ArrayList<Long>> adminRoles = new HashMap<>();

    public static void getCommand(MessageReceivedEvent event)
    {
        Message message = event.getMessage();
        MessageChannel channel = event.getChannel();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if(message.getMentionedRoles().size() > 0){
            long roleID = message.getMentionedRoles().get(0).getIdLong();
            long guildID = event.getGuild().getIdLong();

            adminRoles.computeIfAbsent(guildID, s -> new ArrayList<>()).add(roleID);

            channel.sendMessage(message.getMentionedRoles().get(0).getName() + " has been registered as an Admin role.").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }else{
            channel.sendMessage("You need to specify a role.").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }

    public static Map<Long, ArrayList<Long>> getIDs(){
        return adminRoles;
    }
}
