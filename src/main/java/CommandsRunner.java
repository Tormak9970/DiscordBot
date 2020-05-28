import Tasks.GetInviteLinkCommand;
import Tasks.GeneralHelpCommand;
import Tasks.NickNameChangerCommand;
import Tasks.PingPongCommand;
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
        }else{
            return;
        }

    }
}
