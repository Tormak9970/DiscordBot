package DiscordBot.Tasks;


import DiscordBot.Tasks.Info.BotInfoCommand;
import DiscordBot.Tasks.Info.ServerInfoCommand;
import DiscordBot.Tasks.Info.UptimeCommand;
import DiscordBot.Tasks.RLMafia.RlMafiaHostCommand;
import DiscordBot.Tasks.RLMafia.RLMafiaHelpCommand;
import DiscordBot.Tasks.Roles.JoiningAddsRoleCommand;
import DiscordBot.Tasks.Roles.NickNameByRoleCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandsRunner extends Listener {



    public static void passEvent(MessageReceivedEvent event){
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().equals("$ping")){
            PingPongCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf("$nick") == 0 && !event.getMessage().getContentRaw().contains("$help")){
            NickNameChangerCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$generalhelp")){
            GeneralHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$rlmafiahelp")){
            RLMafiaHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$hostrlmafia")) {
            RlMafiaHostCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$rlmafiasettings")){

        }else if(event.getMessage().getContentRaw().equals("$addroleonjoin")){
            JoiningAddsRoleCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$changenickwithrole")){
            NickNameByRoleCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$serverinfo")){
            ServerInfoCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$uptime")){
            UptimeCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$botinfo")){
            BotInfoCommand.getCommand(event);
        }else if(event.getMessage().getMentionedUsers().size() != 0 && event.getMessage().getMentionedUsers().get(0).equals(event.getJDA().getSelfUser())){
            event.getChannel().sendMessage("https://tenor.com/view/ping-who-pinged-me-disturbed-gif-14162073").queue();
        }
    }

    /*
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
        }else if(event.getMessage().getContentRaw().equals("$generalhelp")){
            GeneralHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$rlmafiahelp")){
            RLMafiaHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$hostrlmafia")) {
            RlMafiaHostCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$rlmafiasettings")){

        }else if(event.getMessage().getContentRaw().equals("$addroleonjoin")){
            JoiningAddsRoleCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$changenickwithrole")){
            NickNameByRoleCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$serverinfo")){
            ServerInfoCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$uptime")){
            UptimeCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals("$botinfo")){
            BotInfoCommand.getCommand(event);
        }else if(event.getMessage().getMentionedUsers().size() != 0 && event.getMessage().getMentionedUsers().get(0).equals(event.getJDA().getSelfUser())){
            event.getChannel().sendMessage("https://tenor.com/view/ping-who-pinged-me-disturbed-gif-14162073").queue();
        }

    }

     */
}
