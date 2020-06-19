package DiscordBot.Tasks.Info;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;

public class ServerInfoCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        Guild guild = event.getGuild();

        String generalInfo = String.format(
                "**Owner**: <@%s>\n**Region**: %s\n**Creation Date**: %s\n**Verification Level**: %s",
                guild.getOwnerId(),
                guild.getRegion().getName(),
                guild.getTimeCreated().format(DateTimeFormatter.RFC_1123_DATE_TIME),
                convertVerificationLevel(guild.getVerificationLevel())
        );

        String memberInfo = String.format(
                "**Total Roles**: %s\n**Total Members**: %s\n**Online Members**: %s\n**Offline Members**: %s\n**Bot Count**: %s",
                guild.getRoleCache().size(),
                guild.getMemberCache().size(),
                guild.getMemberCache().stream().filter((m) -> m.getOnlineStatus() == OnlineStatus.ONLINE).count(),
                guild.getMemberCache().stream().filter((m) -> m.getOnlineStatus() == OnlineStatus.OFFLINE).count(),
                guild.getMemberCache().stream().filter((m) -> m.getUser().isBot()).count()
        );

        EmbedBuilder embed = EmbedUtils.defaultEmbed()
                .setTitle("Server info for " + guild.getName())
                .setThumbnail(guild.getIconUrl())
                .addField("General Info", generalInfo, false)
                .addField("Role And Member Counts", memberInfo, false)
                .setFooter("inDev serverInfo")
                ;

        event.getChannel().sendMessage(embed.build()).queue();
    }

    private static String convertVerificationLevel(Guild.VerificationLevel lvl) {
        String[] names = lvl.name().toLowerCase().split("_");
        StringBuilder out = new StringBuilder();

        for (String name : names) {
            out.append(Character.toUpperCase(name.charAt(0))).append(name.substring(1)).append(" ");
        }

        return out.toString().trim();
    }
}
