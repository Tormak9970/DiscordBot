package DiscordBot.Tasks;

import DiscordBot.Tasks.Moderation.ModerationRunner;
import DiscordBot.Tasks.Moderation.getBannedWordsCommand;
import DiscordBot.Tasks.Music.MusicRunner;
import DiscordBot.Tasks.RLMafia.RLMafiaRunner;
import DiscordBot.Tasks.Roles.JoinRolesCommand;
import DiscordBot.Tasks.Roles.NickNameByRoleCommand;
import DiscordBot.Tasks.Roles.ReactionRolesCommand;
import DiscordBot.Utils.NickNameRoles;
import DiscordBot.Utils.ReactionRoles;
import DiscordBot.Utils.Utils;
import com.jagrosh.jdautilities.commons.waiter.EventWaiter;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;

public class Listener extends ListenerAdapter {
    private final EventWaiter waiter;

    public Listener(EventWaiter waiter){
        this.waiter = waiter;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String prefix = SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        List<String> badWords = getBannedWordsCommand.getListOfBannedWords(event.getGuild().getIdLong());
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );

        if (event.getAuthor().isBot()) {
            return;
        }
        if(badWords.size() > 0){
            boolean isNotAloud = false;
            for(String word:badWords){
                if(event.getMessage().getContentRaw().indexOf(" " + word + " ") > 0){
                    isNotAloud = true;
                }
            }
            if(isNotAloud){
                Utils.deleteHistory(1, event.getChannel());
                event.getChannel().sendMessage("That word is not aloud in this server").queue();
            }
        }
        if (event.getMessage().getContentRaw().indexOf("m" + prefix) == 0) {
            MusicRunner.passEvent(event);
        } else if (event.getMessage().getContentRaw().indexOf("mod" + prefix) == 0) {
            ModerationRunner.passEvent(event, waiter);
        } else if (event.getMessage().getContentRaw().indexOf("rlmafia" + prefix) == 0) {
            RLMafiaRunner.passEvent(event);
        } else {
            CommandsRunner.passEvent(event);
        }
    }

    @Override
    public void onPrivateMessageReceived(@Nonnull PrivateMessageReceivedEvent event) {
        String content = event.getMessage().getContentRaw();
        if(event.getAuthor().isBot()){
            return;
        }else{
            event.getChannel().sendMessage("Why are you messaging me?").queue();
        }

    }

    @Override
    public void onReady(@NotNull ReadyEvent event){
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());

    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent joinEvent) {
        if (joinEvent.getUser().isBot()){
            return;
        }
        Guild guild = joinEvent.getGuild();
        long guildID = guild.getIdLong();
        List<Long> joinRoles = JoinRolesCommand.getListOfJoinRoles(guildID);

            if(joinRoles.size() > 0){
                for (Long roleId : joinRoles) {
                    guild.addRoleToMember(joinEvent.getMember(), guild.getRoleById(roleId)).queue();
                }
            }
    }

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        Guild guild = event.getGuild();
        if(event.getUser().isBot()){
            return;
        }
        List<NickNameRoles> nickNameRoles = NickNameByRoleCommand.getListOfNickNameRoles(guild.getIdLong());
        long highestRoleID = event.getRoles().get(0).getIdLong();
        int size = nickNameRoles.size();
        Member mem = event.getMember();


        if(event.getRoles().size() > 1){
            for(int i = 1; i < event.getRoles().size(); i++){
                if (event.getRoles().get(i).canInteract(event.getRoles().get(i - 1))){
                    highestRoleID = event.getRoles().get(i).getIdLong();
                }
            }
        }

        if(size > 0){
            for(int i = 0; i < size; i++){
                if(nickNameRoles.get(i).getRoleID() == highestRoleID){
                    String newNick = "";
                    if(mem.getRoles().size() > event.getRoles().size()){
                        boolean isHigher = false;


                        for(int j = 0; j < (mem.getRoles().size()); j++){
                            if (mem.getRoles().get(0).canInteract(guild.getRoleById(highestRoleID)) && nickNameRoles.get(j).getRoleID() == mem.getRoles().get(0).getIdLong()){
                                Utils.sendPrivateMessage(mem.getUser(), "Your nickname has been changed already by a higher role");
                                isHigher = true;
                                break;
                            }
                        }

                        if(!isHigher){
                            if(nickNameRoles.get(i).getType() == 1){
                                newNick = nickNameRoles.get(i).getNickName() + " " + mem.getEffectiveName();

                            }else if(nickNameRoles.get(i).getType() == 2){
                                newNick = nickNameRoles.get(i).getNickName();
                            }else{
                                newNick = mem.getEffectiveName() + " " + nickNameRoles.get(i).getNickName();
                            }
                            mem.modifyNickname(newNick).queue();
                        }
                    }else{
                        if(nickNameRoles.get(i).getType() == 1){
                            newNick = nickNameRoles.get(i).getNickName() + " " + mem.getEffectiveName();

                        }else if(nickNameRoles.get(i).getType() == 2){
                            newNick = nickNameRoles.get(i).getNickName();
                        }else{
                            newNick = mem.getEffectiveName() + " " + nickNameRoles.get(i).getNickName();
                        }
                        mem.modifyNickname(newNick).queue();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent reaction) {
        List<ReactionRoles> reactionRoles = ReactionRolesCommand.getListOfReactionRoles(reaction.getGuild().getIdLong());
        if (reaction.getUser().isBot()){
            return;
        }

        Guild guild = reaction.getGuild();
        boolean match;
        if(reactionRoles != null && reactionRoles.size() > 0){
            for (ReactionRoles reactRole : reactionRoles) {

                if(reactRole.isEmote()){
                    match = reactRole.getEmoteID() == reaction.getReactionEmote().getEmote().getIdLong();
                }else{
                    match = reactRole.getEmoji().equals(reaction.getReactionEmote().getEmoji());
                }
                if (reactRole.getChannelID() == reaction.getChannel().getIdLong()
                        && reactRole.getMessageID() == reaction.getMessageIdLong()
                        && match) {
                    guild.addRoleToMember(reaction.getMember(), guild.getRoleById(reactRole.getRoleID())).queue();
                    Utils.sendPrivateMessage(reaction.getUser(), "You have been given the role " + guild.getRoleById(reactRole.getRoleID()).getName() + " in the server " + guild.getName());
                }
            }
        }

    }
}
