package DiscordBot.Tasks;

import DiscordBot.Database.DatabaseManager;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class NickNameChangerCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (event.getMember().isOwner()) {
            MessageChannel channel = event.getChannel();
            channel.sendMessage("Unfortunately I cant change your name because you are the owner").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }else{
            Member toChange = event.getMember();
            toChange.modifyNickname(content.substring(SetPrefixCommand.getPrefix(event.getGuild().getIdLong()).length() + 9)+ " " + toChange).queue();
            MessageChannel channel = event.getChannel();
            channel.sendMessage(event.getAuthor().getAsMention() + ", your nickname has been changed to " + event.getAuthor().getName()).queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
        }
    }
}
