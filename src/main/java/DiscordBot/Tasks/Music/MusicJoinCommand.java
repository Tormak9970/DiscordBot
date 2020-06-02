package DiscordBot.Tasks.Music;

import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.managers.AudioManager;

import java.util.Objects;

public class MusicJoinCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf("$join") == 0)
        {
            TextChannel channel = event.getTextChannel();
            AudioManager audioManager = event.getGuild().getAudioManager();

            if (audioManager.isConnected()) {
                channel.sendMessage("I'm already connected to a channel").queue();
                return;
            }

            GuildVoiceState memberVoiceState = Objects.requireNonNull(event.getMember()).getVoiceState();

            assert memberVoiceState != null;
            if (!memberVoiceState.inVoiceChannel()) {
                channel.sendMessage("Please join a voice channel first").queue();
                return;
            }

            VoiceChannel voiceChannel = memberVoiceState.getChannel();
            Member selfMember = event.getGuild().getSelfMember();

            assert voiceChannel != null;
            if (!selfMember.hasPermission(voiceChannel, Permission.VOICE_CONNECT)) {
                channel.sendMessageFormat("I am missing permission to join %s", voiceChannel).queue();
                return;
            }

            audioManager.openAudioConnection(voiceChannel);
            channel.sendMessage("Joining your voice channel").queue();

        }
    }
}
