package DiscordBot.Tasks.RLMafia;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RlMafiaHostCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        RLMafia.setHost(event.getAuthor());
        MessageChannel channel = event.getChannel();
        channel.sendMessage(event.getAuthor().getAsMention() + " has started a Rocket League Mafia game,").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }
}
