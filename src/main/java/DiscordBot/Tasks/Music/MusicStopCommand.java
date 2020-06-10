package DiscordBot.Tasks.Music;

import DiscordBot.Tasks.Music.MusicUtils.GuildMusicManager;
import DiscordBot.Tasks.Music.MusicUtils.PlayerManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MusicStopCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "stop") == 0)
        {
            PlayerManager playerManager = PlayerManager.getInstance();
            GuildMusicManager musicManager = playerManager.getGuildMusicManager(event.getGuild());

            musicManager.scheduler.getQueue().clear();
            musicManager.player.stopTrack();
            musicManager.player.setPaused(false);

            event.getChannel().sendMessage("Stopping the player and clearing the queue").queue();
        }
    }
}
