package DiscordBot;

import DiscordBot.Database.SQLiteDataSource;
import DiscordBot.Tasks.Listener;
import DiscordBot.Tasks.Roles.NickNameByRoleCommand;
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
import java.sql.SQLException;

public class DiscordBotMain extends ListenerAdapter {

    public static void main(String[] args) throws LoginException, IOException, SQLException {
        String token;

        InputStream in = DiscordBotMain.class.getClassLoader().getResourceAsStream("token.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        token = reader.readLine();
        EventWaiter waiter = new EventWaiter();

        DefaultShardManagerBuilder.createDefault(token)
                .setActivity(Activity.playing("In Dev"))
                .addEventListeners(waiter)
                .addEventListeners(new NickNameByRoleCommand(waiter))
                .addEventListeners(new ReactionRolesCommand(waiter))
                .addEventListeners(new Listener())
                .build();




    }




}
