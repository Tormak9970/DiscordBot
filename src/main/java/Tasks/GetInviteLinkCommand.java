package Tasks;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GetInviteLinkCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$getinvite"))
        {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("https://discordapp.com/api/oauth2/authorize?client_id=643451410855362569&permissions=8&scope=bot").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }

    @Override
    public String toString(){
        return "$invitelink - bot gives you link to invite it to your server";
    }
}
