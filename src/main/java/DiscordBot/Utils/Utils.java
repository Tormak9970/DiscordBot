package DiscordBot.Utils;

import net.dv8tion.jda.api.entities.*;

import java.util.function.Consumer;

public abstract class Utils{

    public static void sendPrivateMessage(User user, String content)
    {
        // notice that we are not placing a semicolon (;) in the callback this time!
        user.openPrivateChannel().queue( (channel) -> channel.sendMessage(content).queue() );
    }

    public static void sendAndLog(MessageChannel channel, String message)
    {
        // Here we use a lambda expressions which names the callback parameter -response- and uses that as a reference
        // in the callback body -System.out.printf("Sent Message %s\n", response)-
        Consumer<Message> callback = (response) -> System.out.printf("Sent Message %s\n", response);
        channel.sendMessage(message).queue(callback); // ^ calls that
    }

    public static void addRole(Member mem, Role role){
        mem.getRoles().add(role);
    }
}
