package DiscordBot.Tasks;

import DiscordBot.Utils.ReactionRoles;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.ArrayList;

public class ReactionRolesCommand extends ListenerAdapter {
    /*check if command sent
    prompt user for message the want to put role on
    prompt user for emoji, telling them to react to current message with it
    prompt user for role via @mention
    DM user that they have role if they react
    say in channel "success!"
     */

    ArrayList<ReactionRoles> listOfSetupRoles = new ArrayList<>();
    String theChannel = "";
    String messageID = "";
    String emojiName = "";

    @Override
    public void onMessageReceived(MessageReceivedEvent event)
    {


        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$reactionroles"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("please mention channel in response to this message. Format like $channel #general").queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)

        }else if(content.indexOf("$channel") == 0 && event.getMessage().getMentionedChannels().size() > 0){
            MessageChannel channel = event.getChannel();
            theChannel = event.getMessage().getMentionedChannels().get(0).getName();
            channel.sendMessage("please send message id in response to this message.format like this $messageID 999999999").queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)
            //tag channel you want the reaction role to be in.
        }else if(content.indexOf("$messageID") == 0){
            MessageChannel channel = event.getChannel();
            messageID = content.substring(11);
            channel.sendMessage("please send message formatted like the following: $emojiname emojiname(thing between the ::)").queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)
            //get message id from their message
        }else if(content.indexOf("$emojiname") == 0){
            MessageChannel channel = event.getChannel();
            emojiName = content.substring(11);
            channel.sendMessage("please send message formatted like the following: $role @role").queue();
            // Important to call .queue() on the RestAction returned by sendMessage(...)
            //ask for person to react to

        }else if(content.indexOf("$role") == 0 && event.getMessage().getMentionedRoles().size() > 0){
            MessageChannel channel = event.getChannel();
            Role role = event.getMessage().getMentionedRoles().get(0);
            channel.sendMessage("congrats, you set up a reaction role!").queue();
            ReactionRoles reactRole = new ReactionRoles(messageID, theChannel, emojiName, role);
            listOfSetupRoles.add(reactRole);
        }

    }

    @Override
    public void onMessageReactionAdd(MessageReactionAddEvent emote) {
        if (emote.getUser().isBot()){
            return;
        }else{
            String channelName = emote.getChannel().getName();
            for(int i = 0; i < listOfSetupRoles.size(); i++){
                if ( channelName.equals(listOfSetupRoles.get(i).getChannel())
                        && emote.getMessageId().equals(listOfSetupRoles.get(i).getMessageID())
                        && emote.getReactionEmote().getEmote().getName().equals(listOfSetupRoles.get(i).getEmoji())){
                    try{
                        DiscordBot.Utils.Utils.addRole(emote.getMember(), listOfSetupRoles.get(i).getRole());
                    }catch(IllegalStateException e){

                    }
                }
            }
        }
    }

    @Override
    public String toString(){
        return "$reactionroles - allows you to set an emoji to a message to give people a role if they react with it";
    }
}