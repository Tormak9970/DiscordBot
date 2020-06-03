package DiscordBot.Tasks.Music;

import DiscordBot.DiscordBotMain;
import DiscordBot.Tasks.Music.MusicUtils.PlayerManager;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.SearchResult;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import javax.annotation.Nullable;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class MusicPlayCommand {
    private static YouTube youTube;

    public static void getCommand(MessageReceivedEvent event) throws IOException {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf("$play") == 0)
        {
            YouTube temp = null;


            try {
                temp = new YouTube.Builder(
                        GoogleNetHttpTransport.newTrustedTransport(),
                        JacksonFactory.getDefaultInstance(),
                        null
                )
                        .setApplicationName("Quarantine Bot")
                        .build();
            } catch (Exception e) {
                e.printStackTrace();
            }

            youTube = temp;

            TextChannel channel = event.getTextChannel();
            String input = content.substring(6);
            PlayerManager manager = PlayerManager.getInstance();

            if(!isUrl(input)){
                String ytSearched = searchYoutube(input);

                if (ytSearched == null) {
                    channel.sendMessage("Youtube returned no results").queue();

                    return;
                }


                input = ytSearched;
            }

            manager.loadAndPlay(event.getTextChannel(), input);



        }
    }

    @Nullable
    private static String searchYoutube(String input) throws IOException {
        InputStream in = DiscordBotMain.class.getClassLoader().getResourceAsStream("youtubeapitoken.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String yttoken = reader.readLine();
        try {
            List<SearchResult> results = youTube.search()
                    .list("id,snippet")
                    .setQ(input)
                    .setMaxResults(1L)
                    .setType("video")
                    .setFields("items(id/kind,id/videoId,snippet/title,snippet/thumbnails/default/url)")
                    .setKey(yttoken)
                    .execute()
                    .getItems();

            if (!results.isEmpty()) {
                String videoId = results.get(0).getId().getVideoId();

                return "https://www.youtube.com/watch?v=" + videoId;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    private static boolean isUrl(String input) {
        try {
            new URL(input);

            return true;
        } catch (MalformedURLException ignored) {
            return false;
        }
    }
}
