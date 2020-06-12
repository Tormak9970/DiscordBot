package DiscordBot.Tasks;

import DiscordBot.Database.DatabaseManager;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class GeneralHelpCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        String prefix = SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        MessageChannel channel = event.getChannel();
        channel.sendMessage(prefix + "help - lists all general commands" +
                "\nmod" + prefix + "help - help with mod commands" +
                "\nrlmafia" + prefix + "help - help with rlmafia commands" +
                "\nm" + prefix + "help - help with music commands" +
                "\n" + prefix + "reactionroles - lets you set up reaction roles(note that this only works when bot is online)" +
                "\n" + prefix + "setnickwithrole - lets you set up a role that when a user receives it, changes their nickname(note that this only works when bot is online)" +
                "\n" + prefix + "addroleonjoin - lets you set up a role to add to users when they join" +
                "\n" + prefix + "setprefix - allows you to change the bot's prefix for commands" +
                "\n" + prefix + "meme - sends a meme" +
                "\n" + prefix + "nick - use in format like *$nick blank* to add *blank* to beggining of nickname, owners cant use this due to perms" +
                "\n" + prefix + "serverinfo - gets server info" +
                "\n" + prefix + "userinfo - gets user info" +
                "\n" + prefix + "botinfo - gets bot info" +
                "\n" + prefix + "uptime - gets how long bot has been online" +
                "\n" + prefix + "ping - bot responds with pong!").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
    }
}
