package DiscordBot.Tasks.Music;

import DiscordBot.Tasks.Music.MusicUtils.GuildMusicManager;
import DiscordBot.Tasks.Music.MusicUtils.PlayerManager;
import DiscordBot.Tasks.SetPrefixCommand;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import java.util.concurrent.BlockingQueue;

public class MusicClearCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(SetPrefixCommand.getPrefix() + "clear"))
        {
            TextChannel channel = event.getTextChannel();
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

            if (queue.isEmpty()) {
                channel.sendMessage("The queue is already empty").queue();

                return;
            }else{
                queue.clear();
                channel.sendMessage("The queue has been cleared").queue();
            }

        }
    }
}
