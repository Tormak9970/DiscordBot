package DiscordBot.Tasks.Roles;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class NickNameByRoleCommand {


    public static void getCommand(MessageReceivedEvent event)
    {
        /*
        get guildID, store it
        get role by mention, store it's ID
        ask weather its a prefix, suffix, or replacement
        get nickname
        send summary
         */

        MessageChannel channel = event.getChannel();
        channel.sendMessage("").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }


}
