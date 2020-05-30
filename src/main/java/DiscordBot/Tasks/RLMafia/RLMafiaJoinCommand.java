package DiscordBot.Tasks.RLMafia;

import DiscordBot.Tasks.RLMafia.RLMafiaUtils.Player;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RLMafiaJoinCommand extends RLMafia{

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
         // Important to call .queue() on the RestAction returned by sendMessage(...)
        if(content.indexOf("$join") == 0) {
            User sender = event.getAuthor();
            String nickname = content.substring(6);
            Player p1 = new Player("none", "none", nickname, 0, sender);
            RLMafia.addPlayer(p1);
            MessageChannel channel = event.getChannel();
            channel.sendMessage("joined successfully!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}
