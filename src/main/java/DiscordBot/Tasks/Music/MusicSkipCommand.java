package DiscordBot.Tasks.Music;

import DiscordBot.Tasks.Music.MusicUtils.GuildMusicManager;
import DiscordBot.Tasks.Music.MusicUtils.PlayerManager;
import DiscordBot.Tasks.Music.MusicUtils.TrackScheduler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MusicSkipCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$skip"))
        {
            TextChannel channel = event.getTextChannel();
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
            TrackScheduler trackScheduler = musicManager.scheduler;
            AudioPlayer audioPlayer = musicManager.player;

            if(audioPlayer.getPlayingTrack() == null){
                channel.sendMessage("you are not playing anything right now").queue();

                return;
            }

            trackScheduler.nextTrack();

            channel.sendMessage("Skipping current track").queue();

        }
    }
}
