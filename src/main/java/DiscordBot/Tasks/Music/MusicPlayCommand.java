package DiscordBot.Tasks.Music;

import DiscordBot.Tasks.Music.MusicUtils.PlayerManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MusicPlayCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf("$play") == 0)
        {
            String url = content.substring(6);
            PlayerManager manager = PlayerManager.getInstance();

            manager.loadAndPlay(event.getTextChannel(), "https://youtu.be/b6QHGfphoXU");

            if(!Music.isURL(url) && !url.startsWith("ytsearch:")){
                //later modify to add youtube api if starts with ytsearch:
            }

        }
    }
}
