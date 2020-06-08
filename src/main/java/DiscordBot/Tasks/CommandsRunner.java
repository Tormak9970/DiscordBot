package DiscordBot.Tasks;


import DiscordBot.Tasks.RLMafia.RlMafiaHostCommand;
import DiscordBot.Tasks.RLMafia.RLMafiaHelpCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandsRunner extends ListenerAdapter {



    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().equals("$ping")){
            PingPongCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$nick") == 0 && !event.getMessage().getContentRaw().contains("$help")){
            NickNameChangerCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$getinvite")){
            GetInviteLinkCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$generalhelp")){
            GeneralHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$rlmafiahelp")){
            RLMafiaHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$hostrlmafia")) {
            RlMafiaHostCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$rlmafiasettings")){

        }else if(event.getMessage().getContentRaw().equals("$serverinfo")){
            ServerInfoCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$uptime")){
            UptimeCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$botinfo")){
            BotInfoCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().contains(event.getJDA().getSelfUser().getAsMention())){
            event.getChannel().sendMessage("https://tenor.com/view/ping-who-pinged-me-disturbed-gif-14162073").queue();
        }

    }
}
