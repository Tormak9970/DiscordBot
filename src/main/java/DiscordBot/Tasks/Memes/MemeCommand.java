package DiscordBot.Tasks.Memes;

import DiscordBot.Tasks.SetPrefixCommand;
import com.fasterxml.jackson.databind.JsonNode;
import me.duncte123.botcommons.messaging.EmbedUtils;
import me.duncte123.botcommons.web.WebUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class MemeCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "meme"))
        {
            TextChannel channel = event.getTextChannel();
            WebUtils.ins.getJSONObject("https://apis.duncte123.me/meme").async((json) -> {
                if (!json.get("success").asBoolean()) {
                    channel.sendMessage("Something went wrong, try again later").queue();
                    System.out.println(json);
                    return;
                }

                final JsonNode data = json.get("data");
                final String title = data.get("title").asText();
                final String url = data.get("url").asText();
                final String image = data.get("image").asText();
                final EmbedBuilder embed = EmbedUtils.defaultEmbed()
                        .setColor(0x9d09ed)
                        .setTitle(title, url)
                        .setImage(image)
                        .setFooter("In Dev");

                channel.sendMessage(embed.build()).queue();
            });
        }
    }
}
