package DiscordBot.Tasks.Moderation;

import DiscordBot.Database.DatabaseManager;
import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.concurrent.TimeUnit;

public class MuteCommand {

    public static void getCommand(GuildMessageReceivedEvent event)
    {
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        int muteLength = 0;
        int numSpaces = 0;
        long memberID = 0;


        for(int i = 0; i < content.length(); i++){
            if(content.charAt(i) == ' '){
                numSpaces++;
            }
        }

        if(numSpaces == 1){
            muteLength = 5;
            memberID = event.getGuild().getMembersByEffectiveName(content.substring(content.indexOf(" ")), true).get(0).getIdLong();
        }else if(numSpaces == 2){
            String stringTime = content.substring(content.lastIndexOf(" "));
            memberID = event.getGuild().getMembersByEffectiveName(content.substring(content.indexOf(" "), content.lastIndexOf(" ")), true).get(0).getIdLong();
            try{
                muteLength = Integer.parseInt(stringTime);
            }catch(NumberFormatException e){
                event.getChannel().sendMessage("Not a valid length").queue();
            }
        }

        Member toMute = event.getGuild().getMemberById(memberID);

        toMute.mute(true).queue();

        MessageChannel channel = event.getChannel();
        channel.sendMessage(toMute.getEffectiveName() + " has been muted for " + muteLength + " minutes").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)

        toMute.mute(false).queueAfter(muteLength, TimeUnit.MINUTES);
    }
}
