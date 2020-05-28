import Tasks.RLMafia.RLMafiaRunner;
import Tasks.ReactionRolesCommand;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class Main extends ListenerAdapter {
    public static void main(String[] args) throws LoginException{
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        //have to change to JDABuilder.createDefault in future versions.
        String token = "NjQzNDUxNDEwODU1MzYyNTY5.XphbUQ.NbZQtWcA_Bgh_5jr6Z8mCuQtoag";
        builder.setToken(token);
        builder.addEventListeners(new ReactionRolesCommand());
        builder.addEventListeners(new CommandsRunner());
        builder.addEventListeners(new RLMafiaRunner());
        builder.setActivity(Activity.playing("$generalhelp for help"));
        builder.build();


    }


}
