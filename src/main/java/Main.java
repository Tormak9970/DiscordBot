import Tasks.RLMafia.RLMafiaRunner;
import Tasks.ReactionRolesCommand;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException{
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        //have to change to JDABuilder.createDefault in future versions.
        String token = "";
        try{
            final Path pathToToken = Paths.get(Main.class.getResource("token.txt").toURI());
            final String tokenFromFile = new String(Files.readAllBytes(pathToToken));
            token = tokenFromFile.trim();
        }catch(IOException | URISyntaxException e){
            e.printStackTrace();
        }
        builder.setToken(token);
        builder.addEventListeners(new ReactionRolesCommand());
        builder.addEventListeners(new CommandsRunner());
        builder.addEventListeners(new RLMafiaRunner());
        builder.setActivity(Activity.playing("$generalhelp for help"));
        builder.build();


    }


}
