package DiscordBot.Tasks;

import DiscordBot.Utils.ReactionRoles;
import DiscordBot.Utils.Utils;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import static DiscordBot.Utils.Utils.deleteHistory;

public class ReactionRolesCommand extends ListenerAdapter {
    private EventWaiter eventWaiter;
    private TextChannel setup;
    private ArrayList<ReactionRoles> listOfSetupRoles = new ArrayList<>();
    private int choice;
    private long guildID;
    private long roleID;
    private long msgChannelID;
    private long messageID;
    private long emoteID;

    public ReactionRolesCommand(EventWaiter waiter){
        eventWaiter = waiter;
    }



    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {
        /*check if command sent
    prompt user for a channel mention of the message's channel
    prompt user for messageID the want to put role on
    prompt user for role via @mention
    prompt user for emoji, telling them to react to current message with it
    ask if they want it to only add, only remove, or both via 1 2 3
    say in channel "success!"
    DM user that they have role if they react
    (delete user's response and previous embed when next embed sent)
     */

        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$reactionroles"))
        {
            TextChannel channel = event.getTextChannel();
            Guild guild = event.getGuild();
            User user = guild.getJDA().getSelfUser();
            setup = event.getTextChannel();
            guildID = guild.getIdLong();

                    EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Reaction Roles")
                    .setColor(Color.RED)
                    .setThumbnail(user.getAvatarUrl())
                    .addField("**Step 1**: ", "please mention channel " +
                            "\nthat the reaction role will be in." +
                            "\n(you need to enable developer mode)" +
                            "\nSettings -> Appearance -> Advanced", false)
                    .setFooter("Quarantine Bot Reaction Roles")
                    ;
            channel.sendMessage(embed.build()).queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)

            initWaiter(event.getChannel().getIdLong(), event.getJDA().getShardManager());
        }
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent reaction) {
        if (reaction.getUser().isBot()){
            return;
        }

        String channelName = reaction.getChannel().getName();
        for (ReactionRoles listOfSetupRole : listOfSetupRoles) {
            if (channelName.equals(listOfSetupRole.getChannelID())
                    && reaction.getMessageId().equals(listOfSetupRole.getMessageID())
                    && (reaction.getReactionEmote().getEmote().getName().equals(listOfSetupRole.getEmoteID().getName()) || reaction.getReactionEmote().getEmoji().equals(listOfSetupRole.getEmoteID().getName()))) {
                try {
                    Utils.addRole(reaction.getMember(), listOfSetupRole.getRoleID());
                    Utils.sendPrivateMessage(reaction.getUser(), "you have been given the role " + listOfSetupRole.getRoleID().getName() + " in the server " + reaction.getGuild().getName());
                } catch (IllegalStateException ignored) {

                }
            }
        }
    }




    private void initWaiter(long channelID, ShardManager shardManager){
        eventWaiter.waitForEvent(
                MessageReceivedEvent.class,
                (event) -> {
                    User user = event.getAuthor();
                    boolean channelMentioned = event.getMessage().getMentionedChannels().size() != 0;

                    return !user.isBot() && channelMentioned && event.getChannel().getIdLong() == channelID && event.getGuild().getIdLong() == guildID;
                },
                (event) -> {
                    getRRChannelID(event, shardManager, channelID);
                },
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel = shardManager.getTextChannelById(channelID);
                    textChannel.sendMessage("Your reaction role has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }




    private void getRRChannelID(MessageReceivedEvent event, ShardManager shardManager, long channelID){
        TextChannel textChannel = shardManager.getTextChannelById(channelID);
        Guild guild = event.getGuild();
        User botUser = guild.getJDA().getSelfUser();

        //code to execute
        msgChannelID = event.getMessage().getMentionedChannels().get(0).getIdLong();
        deleteHistory(2, setup);

        EmbedBuilder embed = EmbedUtils.defaultEmbed()
                .setTitle("Reaction Roles")
                .setColor(Color.RED)
                .setThumbnail(botUser.getAvatarUrl())
                .addField("**Step 2**: ", "please send message id " +
                        "\nthat the reaction role will be on.", false)
                .setFooter("Quarantine Bot Reaction Roles")
                ;
        textChannel.sendMessage(embed.build()).queue();


        //new event waiter
        eventWaiter.waitForEvent(
                MessageReceivedEvent.class,
                (event1) -> {
                    User user = event1.getAuthor();
                    return !user.isBot() && event1.getChannel().getIdLong() == channelID && event1.getGuild().getIdLong() == guildID;
                },
                (event1) -> {
                    getRRMessageID(event1, shardManager, botUser, channelID);

                },
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel1 = shardManager.getTextChannelById(channelID);
                    textChannel1.sendMessage("Your reaction role has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }




    private void getRRMessageID(MessageReceivedEvent event, ShardManager shardManager, User botUser, long channelID){
        event.getChannel().retrieveMessageById(event.getMessage().getContentRaw()).queue(
                message -> {
                    messageID = message.getIdLong();

                    deleteHistory(2, setup);

                    EmbedBuilder embed2 = EmbedUtils.defaultEmbed()
                            .setTitle("Reaction Roles")
                            .setColor(Color.RED)
                            .setThumbnail(botUser.getAvatarUrl())
                            .addField("**Step 3**: ", "please @mention the role " +
                                    "\nthat will be given, you may have to enable pinging of" +
                                    "\nit, but u can turn it off later.", false)
                            .setFooter("Quarantine Bot Reaction Roles")
                            ;
                    event.getChannel().sendMessage(embed2.build()).queue();
                    // Important to call .queue() on the RestAction returned by sendMessage(...)

                    eventWaiter.waitForEvent(
                            MessageReceivedEvent.class,
                            (event1) -> {
                                User user = event1.getAuthor();
                                boolean hasRole = event1.getMessage().getMentionedChannels().size() != 0;
                                return !user.isBot() && event1.getChannel().getIdLong() == channelID && event1.getGuild().getIdLong() == guildID && hasRole;
                            },
                            (event1) -> {
                                getRRRoleID(event1, shardManager, botUser, channelID);

                            },
                            30, TimeUnit.SECONDS,
                            () -> {
                                TextChannel textChannel1 = shardManager.getTextChannelById(channelID);
                                textChannel1.sendMessage("Your reaction role has timed out due to un responsiveness. please restart.").queue();
                            }
                    );

                },
                error -> {
                }
        );
    }

    private void getRRRoleID(MessageReceivedEvent event, ShardManager shardManager, User botUser, long channelID){
        roleID = event.getMessage().getMentionedRoles().get(0).getIdLong();

        deleteHistory(2, setup);

        EmbedBuilder embed = EmbedUtils.defaultEmbed()
                .setTitle("Reaction Roles")
                .setColor(Color.RED)
                .setThumbnail(botUser.getAvatarUrl())
                .addField("**Step 4**: ", "Choose one:" +
                        "\n`1:` adding reaction only gives role" +
                        "\n`2:` adding reaction only removes role" +
                        "\n`3:` adding/removing reaction adds/removes role", false)
                .setFooter("Quarantine Bot Reaction Roles")
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
                        }catch (NumberFormatException e){
                            e.printStackTrace();

                        }

                    return !user.isBot() && event1.getChannel().getIdLong() == channelID && event1.getGuild().getIdLong() == guildID && isChoice;
                },
                (event1) -> {
                    getRRType(event1, shardManager, botUser, channelID);

                },
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel1 = shardManager.getTextChannelById(channelID);
                    textChannel1.sendMessage("Your reaction role has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }

    private void getRRType(MessageReceivedEvent event, ShardManager shardManager, User botUser, long channelID){
        choice = Integer.parseInt(event.getMessage().getContentRaw());

        deleteHistory(2, setup);

        EmbedBuilder embed = EmbedUtils.defaultEmbed()
                .setTitle("Reaction Roles")
                .setColor(Color.RED)
                .setThumbnail(botUser.getAvatarUrl())
                .addField("**Step 5**: ", "Please react to" +
                        "\nthis message with your desired emote.", false)
                .setFooter("Quarantine Bot Reaction Roles")
                ;
        event.getChannel().sendMessage(embed.build()).queue();
        // Important to call .queue() on the RestAction returned by sendMessage(...)

        eventWaiter.waitForEvent(
                GuildMessageReactionAddEvent.class,
                (event1) -> {
                    User user = event1.getUser();
                    return !user.isBot() && event1.getChannel().getIdLong() == channelID && event1.getGuild().getIdLong() == guildID;
                },
                (event1) -> {
                    getRREmoteID(event1, shardManager, botUser, channelID);

                },
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel1 = shardManager.getTextChannelById(channelID);
                    textChannel1.sendMessage("Your reaction role has timed out due to un responsiveness. please restart.").queue();
                }

        );

    }

    private void getRREmoteID(GuildMessageReactionAddEvent event, ShardManager shardManager, User botUser, long channelID){
        emoteID = event.getReactionEmote().getIdLong();
        /*
        event.getGuild().getTextChannelById(msgChannelID).retrieveMessageById(messageID).queue((message1 -> {
            message1.addReaction().queue();
        }));

         */

    }
}
