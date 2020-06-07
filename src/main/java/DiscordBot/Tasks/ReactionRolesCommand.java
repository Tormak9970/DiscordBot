package DiscordBot.Tasks;

import DiscordBot.Utils.ReactionRoles;
import DiscordBot.Utils.Utils;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import me.duncte123.botcommons.messaging.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.List;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static DiscordBot.Utils.Utils.deleteHistory;

public class ReactionRolesCommand extends ListenerAdapter {
    private EventWaiter eventWaiter;
    private TextChannel setup;
    private ArrayList<ReactionRoles> listOfSetupRoles = new ArrayList<>();
    private TextChannel msgChannel;
    private String messageID = "";
    private Emote emote;

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

            initWaiterForChannelMentions(event.getChannel().getIdLong(), event.getJDA().getShardManager());
        }
        /*
        else if(event.getTextChannel().equals(setup) && ){

            messageID = event.getMessage().getContentDisplay();
            deleteHistory(2, setup);

            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Reaction Roles")
                    .setColor(Color.RED)
                    .setThumbnail(user.getAvatarUrl())
                    .addField("**Step 3**: ", "please @mention the role " +
                            "\nthat will be given, you may have to enable pinging of" +
                            "\nit, but u can turn it off later.", false)
                    .setFooter("Quarantine Bot Reaction Roles")
                    ;
            channel.sendMessage(embed.build()).queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)

        }else if(event.getTextChannel().equals(setup) && ){

            deleteHistory(2, setup);

            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Reaction Roles")
                    .setColor(Color.RED)
                    .setThumbnail(user.getAvatarUrl())
                    .addField("**Step 4**: ", "Choose one:" +
                            "\n`1:` adding reaction only gives role" +
                            "\n`2:` adding reaction only removes role" +
                            "\n`3:` adding/removing reaction adds/removes role", false)
                    .setFooter("Quarantine Bot Reaction Roles")
                    ;
            channel.sendMessage(embed.build()).queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)

        }else if(event.getTextChannel().equals(setup) && ){

            deleteHistory(2, setup);

            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Reaction Roles")
                    .setColor(Color.RED)
                    .setThumbnail(user.getAvatarUrl())
                    .addField("**Step 5**: ", "Please react to" +
                            "\nthis message with your desired emote.", false)
                    .setFooter("Quarantine Bot Reaction Roles")
                    ;
            channel.sendMessage(embed.build()).queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)

            msgChannel.retrieveMessageById(messageID).queue((message1 -> {
                message1.addReaction(emote).queue();
            }));
        }

         */

    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent reaction) {
        if (reaction.getUser().isBot()){
            return;
        }

        String channelName = reaction.getChannel().getName();
        for (ReactionRoles listOfSetupRole : listOfSetupRoles) {
            if (channelName.equals(listOfSetupRole.getChannel())
                    && reaction.getMessageId().equals(listOfSetupRole.getMessageID())
                    && (reaction.getReactionEmote().getEmote().getName().equals(listOfSetupRole.getEmote().getName()) || reaction.getReactionEmote().getEmoji().equals(listOfSetupRole.getEmote().getName()))) {
                try {
                    Utils.addRole(reaction.getMember(), listOfSetupRole.getRole());
                    Utils.sendPrivateMessage(reaction.getUser(), "you have been given the role " + listOfSetupRole.getRole().getName() + " in the server " + reaction.getGuild().getName());
                } catch (IllegalStateException ignored) {

                }
            }
        }
    }

    private void initWaiterForChannelMentions(long channelID, ShardManager shardManager){
        eventWaiter.waitForEvent(
                MessageReceivedEvent.class,
                (event) -> {
                    User user = event.getAuthor();
                    boolean channelMentioned = event.getMessage().getMentionedChannels().size() != 0;

                    return !user.isBot() && channelMentioned && event.getChannel().getIdLong() == channelID;
                },
                (event) -> {
                    TextChannel textChannel = shardManager.getTextChannelById(channelID);
                    Guild guild = event.getGuild();
                    User botUser = guild.getJDA().getSelfUser();

                    //code to execute
                    msgChannel = event.getMessage().getMentionedChannels().get(0);
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
                },
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel = shardManager.getTextChannelById(channelID);
                    textChannel.sendMessage("Your reaction role has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }

    private void initWaiterForMessageId(String msgID, long channelID, ShardManager shardManager){

    }
}
