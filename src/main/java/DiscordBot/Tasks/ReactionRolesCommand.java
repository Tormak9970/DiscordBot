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

import java.awt.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class ReactionRolesCommand extends ListenerAdapter {
    private EventWaiter eventWaiter;
    private TextChannel setup;
    private ArrayList<ReactionRoles> listOfSetupRoles = new ArrayList<>();
    private TextChannel msgChannel;
    private String messageID = "";
    private MessageReaction.ReactionEmote emote;
    Collection<Message> toRemove;
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
    public ReactionRolesCommand(EventWaiter waiter){
        eventWaiter = waiter;
    }



    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {

        TextChannel textChannel = event.getTextChannel();
        Guild guild = event.getGuild();
        User user = guild.getJDA().getSelfUser();
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$reactionroles"))
        {
            TextChannel setupChannel = event.getTextChannel();
            setup = setupChannel;



            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Reaction Roles")
                    .setColor(Color.RED)
                    .setThumbnail(user.getAvatarUrl())
                    .addField("**Step 1**: ", "please mention channel " +
                            "\nthat the reaction role will be in.", false)
                    .setFooter("Quarantine Bot Reaction Roles")
                    ;
            textChannel.sendMessage(embed.build()).queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)


        }else if(event.getTextChannel().equals(setup) && event.getMessage().getMentionedChannels().size() != 0){
            msgChannel = event.getMessage().getMentionedChannels().get(0);
            setup.getHistory().retrievePast(2).queue();
            toRemove.add();
            toRemove.add();
            setup.deleteMessages(toRemove).queue();
            toRemove.clear();

        }

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
                } catch (IllegalStateException e) {

                }
            }
        }
    }

    private void initWaiter(long messageID, long channelID, ShardManager shardManager){
        //eventWaiter.waitForEvent();
    }

    @Override
    public String toString(){
        return "$reactionroles - allows you to set an emoji to a message to give people a role if they react with it";
    }
}
