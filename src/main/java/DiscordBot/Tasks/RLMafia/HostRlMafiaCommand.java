package DiscordBot.Tasks.RLMafia;

import DiscordBot.DiscordBotMain;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class HostRlMafiaCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals("$hostrlmafia"))
        {
            RLMafia.setHost(event.getMember());
            MessageChannel channel = event.getChannel();
            channel.sendMessage(event.getAuthor().getAsMention() + " has started a Rocket League Mafia game,").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
            DiscordBotMain.setStatus("RLMafia");
        }
    }
}
