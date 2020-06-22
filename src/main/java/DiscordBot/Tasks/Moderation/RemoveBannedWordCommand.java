package DiscordBot.Tasks.Moderation;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class RemoveBannedWordCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        String prefix = "mod" + SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        DatabaseManager.INSTANCE.removeBannedWord(event.getGuild().getIdLong(), content.substring(prefix.length() + 8));
    }
}
