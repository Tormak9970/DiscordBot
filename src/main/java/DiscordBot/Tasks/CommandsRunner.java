package DiscordBot.Tasks;


import DiscordBot.Tasks.Info.BotInfoCommand;
import DiscordBot.Tasks.Info.ServerInfoCommand;
import DiscordBot.Tasks.Info.UptimeCommand;
import DiscordBot.Tasks.Info.UserInfoCommand;
import DiscordBot.Tasks.Memes.MemeCommand;
import DiscordBot.Tasks.Roles.JoinRolesCommand;
import DiscordBot.Tasks.Roles.NickNameByRoleCommand;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class CommandsRunner extends Listener {



    public static void passEvent(GuildMessageReceivedEvent event){
        String prefix = SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        if(event.getAuthor().isBot()){
            return;
        }else if(event.getMessage().getContentRaw().equals(prefix + "ping")){
            PingPongCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(prefix + "nickname") == 0 && !event.getMessage().getContentRaw().contains(prefix + "help")){
            NickNameChangerCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "help")){
            GeneralHelpCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(prefix + "joinroles") == 0){
            JoinRolesCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(prefix + "serverinfo") == 0){
            ServerInfoCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "uptime")){
            UptimeCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "botinfo")){
            BotInfoCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().indexOf(prefix + "setprefix") == 0){
            SetPrefixCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "meme")){
            MemeCommand.getCommand(event);
        }else if(event.getMessage().getContentRaw().equals(prefix + "userinfo")){
            UserInfoCommand.getCommand(event);
        }else if(event.getMessage().getMentionedUsers().size() != 0 && event.getMessage().getMentionedUsers().get(0).equals(event.getJDA().getSelfUser())){
            event.getChannel().sendMessage("https://tenor.com/view/ping-who-pinged-me-disturbed-gif-14162073").queue();
        }
    }


}
