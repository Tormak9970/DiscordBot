package DiscordBot.Tasks.Roles;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import DiscordBot.Utils.NickNameRoles;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import static DiscordBot.Utils.Utils.deleteHistory;

public class NickNameByRoleCommand extends ListenerAdapter {
    private static Map<Long, List<NickNameRoles>> listOfNickNameRoles = new HashMap<>();
    private EventWaiter eventWaiter;
    private long guildID;
    private long roleID;
    private TextChannel setup;
    private String nickName;
    private int choice;

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
            User botUser = guild.getJDA().getSelfUser();
            setup = event.getTextChannel();
            guildID = guild.getIdLong();

            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Nickname Roles")
                    .setColor(Color.RED)
                    .setThumbnail(botUser.getAvatarUrl())
                    .addField("**Step 1**: ", "please mention role " +
                            "\nthat the user will get.", false)
                    .setFooter("inDev Nickname Roles")
                    ;
            channel.sendMessage(embed.build()).queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)

            initWaiter(event.getChannel().getIdLong(), event.getJDA().getShardManager(), botUser);
        }

    }

    private void initWaiter(long channelID, ShardManager shardManager, User botUser){

        eventWaiter.waitForEvent(
                MessageReceivedEvent.class,
                (event) -> {
                    User user = event.getAuthor();
                    boolean roleMentioned = event.getMessage().getMentionedRoles().size() != 0;

                    return !user.isBot() && roleMentioned && event.getChannel().getIdLong() == channelID && event.getGuild().getIdLong() == guildID;
                },
                (event) -> getType(event, shardManager, botUser, channelID),
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel = shardManager.getTextChannelById(channelID);
                    textChannel.sendMessage("Your setup has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }

    public void getType(MessageReceivedEvent event, ShardManager shardManager, User botUser, long channelID){
        roleID = event.getMessage().getMentionedRoles().get(0).getIdLong();

        deleteHistory(2, setup);

        EmbedBuilder embed = EmbedUtils.defaultEmbed()
                .setTitle("Nickname Roles")
                .setColor(Color.RED)
                .setThumbnail(botUser.getAvatarUrl())
                .addField("**Step 2**: ", "Choose one:" +
                        "\n`1:` nickname is a prefix" +
                        "\n`2:` nickname is a normal nickname" +
                        "\n`3:` nickname is a suffix", false)
                .setFooter("inDev Nickname Roles")
                ;
        event.getChannel().sendMessage(embed.build()).queue();
        // Important to call .queue() on the RestAction returned by sendMessage(...)

        eventWaiter.waitForEvent(
                MessageReceivedEvent.class,
                (event1) -> {
                    User user = event1.getAuthor();
                    boolean isChoice = false;
                    try{
                        int testChoice = Integer.parseInt(event1.getMessage().getContentRaw());
                        if (1 <= testChoice && testChoice <= 3){
                            isChoice = true;
                        }
                    }catch (NumberFormatException ignored){

                    }

                    return !user.isBot() && event1.getChannel().getIdLong() == channelID && event1.getGuild().getIdLong() == guildID && isChoice;
                },
                (event1) -> getNickName(event1, shardManager, botUser, channelID),
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel1 = shardManager.getTextChannelById(channelID);
                    assert textChannel1 != null;
                    textChannel1.sendMessage("Your reaction role has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }

    public void getNickName(MessageReceivedEvent event, ShardManager shardManager, User botUser, long channelID){
        choice = Integer.parseInt(event.getMessage().getContentRaw());

        deleteHistory(2, setup);
        EmbedBuilder embed = EmbedUtils.defaultEmbed()
                .setTitle("Reaction Roles")
                .setColor(Color.RED)
                .setThumbnail(botUser.getAvatarUrl())
                .addField("**Step 3**: ", "Please send" +
                        "\nthe nickname you want this role to give (no space after)", false)
                .setFooter("inDev Reaction Roles")
                ;
        event.getChannel().sendMessage(embed.build()).queue(
                (message) -> {
                    eventWaiter.waitForEvent(
                            MessageReceivedEvent.class,
                            (event1) -> {
                                User user = event1.getAuthor();
                                return !user.isBot() && event1.getChannel().getIdLong() == channelID && event1.getGuild().getIdLong() == guildID;
                            },
                            (event1) -> getSummary(event1, channelID),
                            30, TimeUnit.SECONDS,
                            () -> {
                                TextChannel textChannel1 = shardManager.getTextChannelById(channelID);
                                assert textChannel1 != null;
                                textChannel1.sendMessage("Your reaction role has timed out due to un responsiveness. please restart.").queue();
                            }

                    );
                }
        );
    }

    private void getSummary(MessageReceivedEvent event, long channelID){
        nickName = event.getMessage().getContentRaw();
        deleteHistory(2, setup);
        EmbedBuilder embed = EmbedUtils.defaultEmbed()
                .setTitle("Nickname Roles - Summary")
                .addField("**Role**:", event.getGuild().getRoleById(roleID).getAsMention(), true)
                .addField("**Type**:", "`" + choice + "`", true)
                .addField("**Nickname**:", nickName, false)
                .setFooter("inDev Reaction Roles")
                ;
        event.getChannel().sendMessage(embed.build()).queue();
        NickNameRoles toAdd = new NickNameRoles(nickName, roleID, choice);
        listOfNickNameRoles.computeIfAbsent(guildID, s -> new ArrayList<>()).add(toAdd);
        addNickname(event.getGuild().getIdLong(), toAdd);

    }

    private void addNickname(long guildID, NickNameRoles nickRole){
        DatabaseManager.INSTANCE.addNickRole(guildID, nickRole);
    }
}
