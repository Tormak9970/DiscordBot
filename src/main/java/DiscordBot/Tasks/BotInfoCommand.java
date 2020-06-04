package DiscordBot.Tasks;

import DiscordBot.Utils.Utils;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Objects;

public class BotInfoCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$botinfo"))
        {
            Guild guild = event.getGuild();
            User user = event.getJDA().getSelfUser();
            String botName = Objects.requireNonNull(guild.getMember(user)).getEffectiveName();
            StringBuilder roles = new StringBuilder("");
            List<Role> roleList = guild.getMember(user).getRoles();
            String update = "";

            for(Role role : roleList){
                roles.append(role.getName()).append(", ");
            }
            roles.delete(roles.length() - 3, roles.length() - 1);

            String generalInfo = String.format(
                    "**Name**: %s\n**Region**: %s\n**Creation Date**: January 17, 2020",
                    botName,
                    guild.getRegion().getName()
            );

            String helpServerUrl = "https://discord.gg/dKAQDCe";
            String memberInfo = String.format(
                    "**Uptime**: %s\n**Code Type**: Open Source(GitHub)\n**Developers**: Tormak9970\n**Member since**: %s\n**Roles**: " + roles + "\n**Goal in Life**: overthrow the humans\n**Next Update**: " + update + "\n**Need help? Join**: %s",
                    Utils.getUptime(),
                    guild.getMember(user).getTimeJoined().getDayOfWeek().getDisplayName(TextStyle.FULL, Locale.ENGLISH) + " " + guild.getMember(user).getTimeJoined().getDayOfMonth() + ", " + guild.getMember(user).getTimeJoined().getYear(),
                    helpServerUrl
            );

            String desc = "Quarantine Bot is a discord bot that I started " +
                        "working on during the Corona virus pandemic. " +
                        "It can do a multitude of tasks including: " +
                        "Moderation, Reaction Roles, add prefixes to a " +
                        "username, play music, play rocket league mafia, " +
                        "and of course, send memes from r/dankmemes.";
            String gitHubUrl = "https://github.com/Tormak9970/DiscordBot";


            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Info for " + botName)
                    .setThumbnail(user.getAvatarUrl())
                    .addField("**General Info**", generalInfo, false)
                    .addField("**Other info**", memberInfo, true)
                    .addField("**Description**", desc, true)
                    .addField("**GitHub**", gitHubUrl, false)
                    .setColor(Color.RED)
                    .setFooter("Tormak9970, 2020")
                    ;

            event.getChannel().sendMessage(embed.build()).queue();

        }
    }
}
