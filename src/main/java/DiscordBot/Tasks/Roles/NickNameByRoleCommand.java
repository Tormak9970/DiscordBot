package DiscordBot.Tasks.Roles;

import DiscordBot.Tasks.SetPrefixCommand;
import DiscordBot.Utils.NickNameRoles;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class NickNameByRoleCommand extends ListenerAdapter {
    private static Map<Long, List<NickNameRoles>> listOfNickNameRoles = new HashMap<>();
    private EventWaiter eventWaiter;
    private long guildID;
    private long roleID;
    private TextChannel setup;
    private String neckName;
    private int type;

    public NickNameByRoleCommand(EventWaiter waiter){
        eventWaiter = waiter;
    }

    public static Map<Long, List<NickNameRoles>> getListOfNickNameRoles(){
        return listOfNickNameRoles;
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        /*
        get guildID, store it
        get role by mention, store it's ID
        ask weather its a prefix, suffix, or replacement
        get nickname
        send summary
         */

        if(event.getAuthor().isBot()){
            return;
        }
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if(content.equals(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "nickroles")){
            TextChannel channel = event.getTextChannel();
            Guild guild = event.getGuild();
            User user = guild.getJDA().getSelfUser();
            setup = event.getTextChannel();
            guildID = guild.getIdLong();

            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Nickname Roles")
                    .setColor(Color.RED)
                    .setThumbnail(user.getAvatarUrl())
                    .addField("**Step 1**: ", "please mention role " +
                            "\nthat the user will get.", false)
                    .setFooter("Quarantine Bot Nickname Roles")
                    ;
            channel.sendMessage(embed.build()).queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)

            initWaiter(event.getChannel().getIdLong(), event.getJDA().getShardManager());
        }

    }

    private void initWaiter(long channelID, ShardManager shardManager){
        eventWaiter.waitForEvent(
                MessageReceivedEvent.class,
                (event) -> {
                    User user = event.getAuthor();
                    boolean roleMentioned = event.getMessage().getMentionedRoles().size() != 0;

                    return !user.isBot() && roleMentioned && event.getChannel().getIdLong() == channelID && event.getGuild().getIdLong() == guildID;
                },
                (event) -> getType(event, shardManager, channelID),
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel = shardManager.getTextChannelById(channelID);
                    textChannel.sendMessage("Your setup has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }

    public static void getType(MessageReceivedEvent event, ShardManager shardManager, long channelID){

    }

    public static void getNickName(MessageReceivedEvent event, ShardManager shardManager, long channelID){

    }
}
