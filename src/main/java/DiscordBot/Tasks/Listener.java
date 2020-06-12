package DiscordBot.Tasks;

import DiscordBot.Tasks.Moderation.ModerationRunner;
import DiscordBot.Tasks.Music.MusicRunner;
import DiscordBot.Tasks.RLMafia.RLMafiaRunner;
import DiscordBot.Tasks.Roles.JoinRolesCommand;
import DiscordBot.Tasks.Roles.ReactionRolesCommand;
import DiscordBot.Utils.ReactionRoles;
import DiscordBot.Utils.Utils;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRoleAddEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Map;

public class Listener extends ListenerAdapter {
    private static final Logger LOGGER = LoggerFactory.getLogger(Listener.class);

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        String prefix = SetPrefixCommand.getPrefix(event.getGuild().getIdLong());
        System.out.println("We received a message from " +
                event.getAuthor().getName() + ": " +
                event.getMessage().getContentDisplay()
        );
        if(event.getAuthor().isBot()){
            return;
        }
        if(event.getMessage().getContentRaw().indexOf("m" + prefix) == 0){
            MusicRunner.passEvent(event);
        }else if (event.getMessage().getContentRaw().indexOf("mod" + prefix) == 0){
            ModerationRunner.passEvent(event);
        }else if (event.getMessage().getContentRaw().indexOf("rlmafia" + prefix) == 0){
            RLMafiaRunner.passEvent(event);
        }else{
            CommandsRunner.passEvent(event);
        }

    }

    @Override
    public void onReady(@NotNull ReadyEvent event){
        LOGGER.info("{} is ready", event.getJDA().getSelfUser().getAsTag());

    }

    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent joinEvent) {
        Map<Long, List<Long>> joinRoles = JoinRolesCommand.getListOfJoinRoles();
        if (joinEvent.getUser().isBot()){
            return;
        }
        Guild guild = joinEvent.getGuild();
        long guildID = guild.getIdLong();


            if(joinRoles.get(guildID).size() > 0){
                for(int i = 0; i < joinRoles.get(guildID).size(); i++){
                    guild.addRoleToMember(joinEvent.getMember(), guild.getRoleById(joinRoles.get(guildID).get(i))).queue();
                }
            }
    }

    @Override
    public void onGuildMemberRoleAdd(@Nonnull GuildMemberRoleAddEvent event) {
        //
    }

    @Override
    public void onGuildMessageReactionAdd(GuildMessageReactionAddEvent reaction) {
        Map<Long, List<ReactionRoles>> reactionRoles = ReactionRolesCommand.getListOfReactionRoles();
        if (reaction.getUser().isBot()){
            return;
        }

        Guild guild = reaction.getGuild();
        boolean match;
        if(reactionRoles != null && reactionRoles.get(guild.getIdLong()) != null){
            for (ReactionRoles reactRole : reactionRoles.get(guild.getIdLong())) {

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
