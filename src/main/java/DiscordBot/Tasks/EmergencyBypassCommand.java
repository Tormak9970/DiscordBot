package DiscordBot.Tasks;

import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import static DiscordBot.Tasks.SetPrefixCommand.updatePrefix;

public class EmergencyBypassCommand {

    public static void getCommand(GuildMessageReceivedEvent event){

        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        long guildID = event.getGuild().getIdLong();
        MessageChannel channel = event.getChannel();

        updatePrefix(guildID, "$");
        channel.sendMessage("prefix has been reset to $").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }

}
