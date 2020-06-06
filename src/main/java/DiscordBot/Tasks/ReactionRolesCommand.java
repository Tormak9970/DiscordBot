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

import java.awt.*;
import java.util.ArrayList;

public class ReactionRolesCommand extends ListenerAdapter {
    private EventWaiter eventWaiter;
    /*check if command sent
    prompt user for message the want to put role on
    prompt user for emoji, telling them to react to current message with it
    prompt user for role via @mention
    DM user that they have role if they react
    say in channel "success!"
     */
    ReactionRolesCommand(EventWaiter waiter){
        eventWaiter = waiter;
    }

    ArrayList<ReactionRoles> listOfSetupRoles = new ArrayList<>();
    String theChannel = "";
    MessageChannel msgChannel;
    String messageID = "";
    Emote emote;

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {

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
            MessageChannel channel = event.getChannel();
            EmbedBuilder embed = EmbedUtils.defaultEmbed()
                    .setTitle("Reaction Roles")
                    .setColor(Color.RED)
                    .setThumbnail(user.getAvatarUrl())
                    .addField("**Step 1**: ", "please mention channel " +
                            "\nthat the reaction role will be in.", false)
                    .setFooter("Quarantine Bot Reaction Roles")
                    ;
            channel.sendMessage(embed.build()).queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)

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

    @Override
    public String toString(){
        return "$reactionroles - allows you to set an emoji to a message to give people a role if they react with it";
    }
}
