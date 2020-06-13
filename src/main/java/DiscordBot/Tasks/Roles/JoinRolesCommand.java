package DiscordBot.Tasks.Roles;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import DiscordBot.Utils.ReactionRoles;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JoinRolesCommand {
    private static long roleID;
    private static Map<Long, List<Long>> listOfJoinRoles = new HashMap<>();

    public static Map<Long, List<Long>> getListOfJoinRoles(){
        return listOfJoinRoles;
    }

    public static void getCommand(MessageReceivedEvent event)
    {
        if(event.getMessage().getMentionedRoles().size() > 0){
            roleID = event.getMessage().getMentionedRoles().get(0).getIdLong();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy HH:mm:ss");
            LocalDateTime now = LocalDateTime.now();
            String date = dtf.format(now);

            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Join Roles - Summary")
                    .setColor(Color.RED)
                    .addField("**Date**:", date, false)
                    .addField("**Role**: ", "" + event.getGuild().getRoleById(roleID).getAsMention(), false)
                    .setFooter("inDev Join Roles")
                    ;
            MessageChannel channel = event.getChannel();
            listOfJoinRoles.computeIfAbsent(event.getGuild().getIdLong(), s -> new ArrayList<>()).add(roleID);
            channel.sendMessage(embed.build()).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }else{
            event.getChannel().sendMessage("Please mention a role in your command call.").queue();
        }
    }

    //public void
}