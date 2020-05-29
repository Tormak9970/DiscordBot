package DiscordBot.Tasks;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class NickNameChangerCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (event.getMember().isOwner()) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Unfortunately I cant change your name because you are the owner").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }else if(content.indexOf("$nick") == 0) {
            Member toChange = event.getMember();
            toChange.modifyNickname(content.substring(6)+ " " + toChange);
            MessageChannel channel = event.getChannel();
            channel.sendMessage(event.getAuthor().getAsMention() + ", your nickname has been changed to " + event.getAuthor().getName()).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }

    @Override
    public String toString(){
        return "$nick phrase- bot adds phrase to beginning of your nickname!";
    }
}
