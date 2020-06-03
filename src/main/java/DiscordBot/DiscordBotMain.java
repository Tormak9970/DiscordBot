package DiscordBot;

import DiscordBot.Tasks.CommandsRunner;
import DiscordBot.Tasks.Music.MusicRunner;
import DiscordBot.Tasks.RLMafia.RLMafiaRunner;
import DiscordBot.Tasks.ReactionRolesCommand;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
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
        /*
        build = new JDABuilder(AccountType.BOT);
        build.setToken(token);
        build.addEventListeners(new ReactionRolesCommand());
        build.addEventListeners(new CommandsRunner());
        build.addEventListeners(new RLMafiaRunner());
        build.addEventListeners(new MusicRunner());
        build.setActivity(Activity.playing("$generalhelp for help"));
        build.build();
         */

        new DefaultShardManagerBuilder()
                .setToken(token)
                .setActivity(Activity.playing("$generalhelp for help"))
                .addEventListeners(new ReactionRolesCommand())
                .addEventListeners(new CommandsRunner())
                .addEventListeners(new RLMafiaRunner())
                .addEventListeners(new MusicRunner())
                .build();



    }




}
