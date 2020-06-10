package DiscordBot.Tasks.RLMafia;

import DiscordBot.Tasks.RLMafia.RLMafiaUtils.RLMafiaUtils;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class RLMafiaStartCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.equals(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()) + "startrlmafia") && RLMafia.getCurrentPlayers().size()%2 == 0)
        {

            RLMafiaUtils.generateTeams(RLMafia.getCurrentPlayers(), event);
            RLMafiaUtils.generateRoles(RLMafia.getCurrentPlayers(), event);

            MessageChannel channel = event.getChannel();
            channel.sendMessage("Round " + RLMafia.getRound() + " has started").queue();




        }
    }
}
