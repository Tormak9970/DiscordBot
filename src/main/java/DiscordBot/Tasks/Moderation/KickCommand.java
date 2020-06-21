package DiscordBot.Tasks.Moderation;

import DiscordBot.Tasks.SetPrefixCommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.concurrent.TimeUnit;

public class KickCommand {

    private static EventWaiter waiter;
    private static long guildID;
    private static long setup;
    public static void getCommand(GuildMessageReceivedEvent event, EventWaiter eventWaiter)
    {
        waiter = eventWaiter;
        guildID = event.getGuild().getIdLong();
        setup = event.getChannel().getIdLong();
        Message message = event.getMessage();
        String content = message.getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        String reason = "";
        int numSpaces = 0;
        long memberID = 0;


        for(int i = 0; i < content.length(); i++){
            if(content.charAt(i) == ' '){
                numSpaces++;
            }
        }
        memberID = event.getGuild().getMembersByEffectiveName(content.substring(content.indexOf(" "), content.lastIndexOf(" ")), true).get(0).getIdLong();

        if(numSpaces == 1){
            event.getChannel().sendMessage("Please provide a reason").queue();
        }else if(event.getAuthor().getIdLong() == memberID){
            event.getChannel().sendMessage("You have got to be pretty stupid to try to kick yourself").queue();
        }else if(numSpaces == 2){
            reason = content.substring(content.lastIndexOf(" "));
            event.getChannel().sendMessage("Are you sure you want to ban this person?").queue();
            initWaiter(event.getJDA().getShardManager(), reason, memberID);
        }

    }

    private static void initWaiter(ShardManager shardManager, String reason, long memberID){
        waiter.waitForEvent(
                GuildMessageReceivedEvent.class,
                (event) -> {
                    User user = event.getAuthor();
                    boolean isYes = event.getMessage().getContentRaw().equals("yes");

                    return !user.isBot() && isYes && event.getChannel().getIdLong() == setup && event.getGuild().getIdLong() == guildID;
                },
                (event) -> {
                    Member toKick = event.getGuild().getMemberById(memberID);

                    toKick.kick(reason).queue();

                    MessageChannel channel = event.getChannel();
                    //channel.sendMessage(toKick.getEffectiveName() + " has been muted for " + reason + " minutes").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
                    },
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel = shardManager.getTextChannelById(setup);
                    textChannel.sendMessage("Your response has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }
}
