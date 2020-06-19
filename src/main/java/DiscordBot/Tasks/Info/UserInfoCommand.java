package DiscordBot.Tasks.Info;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.time.format.DateTimeFormatter;

public class UserInfoCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        User toGetInfo = event.getJDA().getUsersByName(content.substring(DatabaseManager.INSTANCE.getPrefix(event.getGuild().getIdLong()).length() + 9), true).get(0);
        Member memInfo = event.getGuild().getMember(toGetInfo);
        Guild guild = event.getGuild();
        StringBuilder roles = new StringBuilder("");
        String nickname = "";

        try{
            nickname = memInfo.getNickname();
        }catch(NullPointerException ignored){

        }

        for (int i = 0; i < memInfo.getRoles().size(); i++){
            Role role = memInfo.getRoles().get(i);
            if(role.isMentionable()){
                roles.append(role.getAsMention());
            }else{
                roles.append(role.getName());
            }
            if(i == memInfo.getRoles().size()){

            }else{
                roles.append(", ");
            }
        }

        String generalInfo = String.format(
                "**Name**: %s\n**Account created on**: %s",
                toGetInfo.getName(),
                toGetInfo.getTimeCreated().getMonth() + " " + toGetInfo.getTimeCreated().getDayOfMonth() + ", " + toGetInfo.getTimeCreated().getYear()
        );

        String memberInfo = String.format(
                "**Nickname**: %s\n**Joined on**: %s\n**Online**: %s\n**Roles**: %s",
                nickname,
                memInfo.getTimeJoined().getMonth() + " " + memInfo.getTimeJoined().getDayOfMonth() + ", " + memInfo.getTimeJoined().getYear(),
                memInfo.getOnlineStatus(),
                roles
        );

        EmbedBuilder embed = EmbedUtils.defaultEmbed()
                .setTitle("User info on " + toGetInfo.getName())
                .setThumbnail(toGetInfo.getAvatarUrl())
                .addField("General Info", generalInfo, false)
                .addField("Server Related", memberInfo, false)
                .setFooter("inDev serverInfo")
                ;

        event.getChannel().sendMessage(embed.build()).queue();
    }
}
