package DiscordBot.Tasks.RLMafia;

import DiscordBot.DiscordBotMain;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RLMafiaWinnerCommand {

    public static void getCommand(MessageReceivedEvent event) {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        if (event.getAuthor().isBot()) {
            return;
            // We don't want to respond to other bot accounts, including ourself

            // getContentRaw() is an atomic getter
            // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
            //if (content.indexOf("$winner") == 0)
        }else{
            RLMafia.setWinner(content.substring(8));

            MessageChannel channel = event.getChannel();
            channel.sendMessage("The round has ended").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
            //channel.sendMessage("").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}
