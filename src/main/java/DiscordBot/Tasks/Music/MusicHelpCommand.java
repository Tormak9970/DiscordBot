package DiscordBot.Tasks.Music;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class MusicHelpCommand {


    public static void getCommand(GuildMessageReceivedEvent event)
    {
        String prefix = "m" + SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        MessageChannel channel = event.getChannel();
        channel.sendMessage(
                prefix + "play (url)- Im disappointed, use ur brain sometimes" +
                        "\n" + prefix + "join - bot joins your voice channel" +
                        "\n" + prefix + "leave - bot leaves the voice channel" +
                        "\n" + prefix + "stop - stops the music player and clears the queue" +
                        "\n" + prefix + "queue - bot sends an embed with the current song queue" +
                        "\n" + prefix + "pause - pauses and unpauses music/audio" +
                        "\n" + prefix + "skip - skips current track" +
                        "\n" + prefix + "clear - clears queue" +
                        "\n" + prefix + "loop (queue) - loops song, if queue, loops queue" +
                        "\n" + prefix + "nowplaying - gets information on whats currently playing").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }
}
