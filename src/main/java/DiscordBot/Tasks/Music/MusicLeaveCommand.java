package DiscordBot.Tasks.Music;

import DiscordBot.Tasks.Music.MusicUtils.PlayerManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.VoiceChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class MusicLeaveCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf("$leave") == 0)
        {
            TextChannel channel = event.getTextChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();

            if (!audioManager.isConnected()) {
                channel.sendMessage("I'm not connected to a voice channel").queue();
                return;
            }

            VoiceChannel voiceChannel = audioManager.getConnectedChannel();

            assert voiceChannel != null;
            if (!voiceChannel.getMembers().contains(event.getMember())) {
                channel.sendMessage("You have to be in the same voice channel as me to use this").queue();
                return;
            }

            audioManager.closeAudioConnection();
            channel.sendMessage("Disconnected from your channel").queue();

        }
    }
}
