package DiscordBot.Tasks.Music;

import DiscordBot.Tasks.Music.MusicUtils.GuildMusicManager;
import DiscordBot.Tasks.Music.MusicUtils.PlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class MusicQueueCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$queue"))
        {
            TextChannel channel = event.getTextChannel();
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            BlockingQueue<AudioTrack> queue = musicManager.scheduler.getQueue();

            if (queue.isEmpty()) {
                channel.sendMessage("The queue is empty").queue();

                return;
            }

            int trackCount = Math.min(queue.size(), 20);
            List<AudioTrack> tracks = new ArrayList<>(queue);
            EmbedBuilder builder = EmbedUtils.defaultEmbed()
                    .setTitle("Current Queue (Total: " + queue.size() + ")");

            for (int i = 0; i < trackCount; i++) {
                AudioTrack track = tracks.get(i);
                AudioTrackInfo info = track.getInfo();

                builder.appendDescription(String.format(
                        "%s - %s\n",
                        info.title,
                        info.author
                ));
            }

            channel.sendMessage(builder.build()).queue();

        }
    }
}
