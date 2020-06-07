package DiscordBot.Utils;

import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.sharding.ShardManager;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;
import java.util.function.Consumer;

public abstract class Utils{

    public static void sendPrivateMessage(User user, String content)
    {
        // notice that we are not placing a semicolon (;) in the callback this time!
        user.openPrivateChannel().queue( (channel) -> channel.sendMessage(content).queue() );
    }

    public static void deleteHistory(int numMsg, TextChannel channel){
        Consumer<List<Message>> callback = channel::purgeMessages;
        channel.getHistory().retrievePast(numMsg).queue(callback);
    }

    public static void sendAndLog(MessageChannel channel, String message)
    {
        // Here we use a lambda expressions which names the callback parameter -response- and uses that as a reference
        // in the callback body -System.out.printf("Sent Message %s\n", response)-
        Consumer<Message> callback = (response) -> System.out.printf("Sent Message %s\n", response);
        channel.sendMessage(message).queue(callback); // ^ calls that
    }

    public static String getUptime(){
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        long uptime = runtimeMXBean.getUptime();
        long uptimeInSeconds = uptime / 1000;
        long numberOfHours = uptimeInSeconds / (60 * 60);
        long numberOfMinutes = (uptimeInSeconds / 60) - (numberOfHours * 60);
        long numberOfSeconds = uptimeInSeconds % 60;

        return String.format("Uptime is `%s hours, %s minutes, %s seconds`",
                numberOfHours, numberOfMinutes, numberOfSeconds);
    }

    public static void addRole(Member mem, Role role){
        mem.getRoles().add(role);
    }
}
