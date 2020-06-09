package DiscordBot;

import DiscordBot.Tasks.CommandsRunner;
import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.Moderation.ModerationRunner;
import DiscordBot.Tasks.Music.MusicRunner;
import DiscordBot.Tasks.RLMafia.RLMafiaRunner;
import DiscordBot.Tasks.Roles.ReactionRolesCommand;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;

import javax.security.auth.login.LoginException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class DiscordBotMain extends ListenerAdapter {

    public static void main(String[] args) throws LoginException, IOException {

        //have to change to JDABuilder.createDefault in future versions.
        String token = "";

        InputStream in = DiscordBotMain.class.getClassLoader().getResourceAsStream("token.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        token = reader.readLine();
        EventWaiter waiter = new EventWaiter();

        new DefaultShardManagerBuilder()
                .setToken(token)
                .setActivity(Activity.playing("$generalhelp for help"))
                .addEventListeners(waiter)
                .addEventListeners(new ReactionRolesCommand(waiter))
                .addEventListeners(new Listener())
                .build();



    }




}
