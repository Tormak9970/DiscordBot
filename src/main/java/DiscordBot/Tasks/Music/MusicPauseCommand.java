package DiscordBot.Tasks.Music;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.Music.MusicUtils.GuildMusicManager;
import DiscordBot.Tasks.Music.MusicUtils.PlayerManager;
import DiscordBot.Tasks.SetPrefixCommand;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class MusicPauseCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        TextChannel channel = event.getChannel();
        PlayerManager playerManager = PlayerManager.getInstance();
        GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());
        AudioPlayer player = musicManager.player;

        if (player.getPlayingTrack() == null) {
            channel.sendMessage("The player is not playing any song. nothing to pause").queue();

            return;
        } else if(player.isPaused()){
            player.setPaused(false);
        }else if(!player.isPaused()){
            player.setPaused(true);
        }
    }
}
