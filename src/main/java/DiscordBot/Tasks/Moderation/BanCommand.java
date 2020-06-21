package DiscordBot.Tasks.Moderation;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.util.concurrent.TimeUnit;

public class BanCommand {

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
        int middleSpaceIndex = 0;
        int numSpaces = 0;
        long memberID = 0;


        for(int i = 0; i < content.length(); i++){
            if(content.charAt(i) == ' '){
                numSpaces++;
                if(numSpaces == 2){
                    middleSpaceIndex = i;
                }
            }
        }
        memberID = event.getGuild().getMembersByEffectiveName(content.substring(content.indexOf(" "), middleSpaceIndex), true).get(0).getIdLong();

        if(numSpaces == 1){
            event.getChannel().sendMessage("You are missing 2 args").queue();
        }else if(event.getAuthor().getIdLong() == memberID){
            event.getChannel().sendMessage("You have got to be pretty stupid to try to ban yourself").queue();
        }else if(numSpaces == 2){
            event.getChannel().sendMessage("You are missing 1 args").queue();
        }else if(numSpaces == 3){
            int delDays = Integer.parseInt(content.substring(middleSpaceIndex), content.lastIndexOf(" "));
            reason = content.substring(content.lastIndexOf(" "));
            event.getChannel().sendMessage("Are you sure you want to ban this person?").queue();
            initWaiter(event.getJDA().getShardManager(), reason, memberID, delDays);
        }

    }

    private static void initWaiter(ShardManager shardManager, String reason, long memberID, int delDays){
        waiter.waitForEvent(
                GuildMessageReceivedEvent.class,
                (event) -> {
                    User user = event.getAuthor();
                    boolean isYes = event.getMessage().getContentRaw().equals("yes");

                    return !user.isBot() && isYes && event.getChannel().getIdLong() == setup && event.getGuild().getIdLong() == guildID;
                },
                (event) -> {
                    Member toBan = event.getGuild().getMemberById(memberID);

                    toBan.ban(delDays, reason).queue();

                    MessageChannel channel = event.getChannel();
                    //channel.sendMessage(toBan.getEffectiveName() + " has been muted for " + reason + " minutes").queue(); // Important to call .queue() on the RestAction returned by sendMessage(...)
                },
                30, TimeUnit.SECONDS,
                () -> {
                    TextChannel textChannel = shardManager.getTextChannelById(setup);
                    textChannel.sendMessage("Your response has timed out due to un responsiveness. please restart.").queue();
                }
        );
    }
}
