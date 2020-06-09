package DiscordBot.Tasks.Moderation;

import DiscordBot.Tasks.SetPrefixCommand;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.time.OffsetDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class ModerationClearMessagesCommand {

    public static void getCommand(MessageReceivedEvent event)
    {
        if (event.getAuthor().isBot()) return;
        // We don't want to respond to other bot accounts, including ourself
        String content = event.getMessage().getContentRaw();
        // getContentRaw() is an atomic getter
        // getContentDisplay() is a lazy getter which modifies the content for e.g. console view (strip discord formatting)
        if (content.indexOf(SetPrefixCommand.getPrefix() + "clearmessages") == 0)
        {
            TextChannel channel = event.getTextChannel();
            Member member = event.getMember();
            Member selfMember = event.getGuild().getSelfMember();

            assert member != null;
            if (!member.hasPermission(Permission.MESSAGE_MANAGE)) {
                channel.sendMessage("You need the `Manage Messages` permission to use this command").queue();

                return;
            }

            if (!selfMember.hasPermission(Permission.MESSAGE_MANAGE)) {
                channel.sendMessage("I need the `Manage Messages` permission for this command").queue();

                return;
            }

            int amount;
            String arg = content.substring(SetPrefixCommand.getPrefix().length() + 14);

            try {
                amount = Integer.parseInt(arg);
            } catch (NumberFormatException ignored) {
                channel.sendMessageFormat("`%s` is not a valid number", arg).queue();

                return;
            }


            if (amount < 2 || amount > 100) {
                channel.sendMessage("Amount must be at least 2 and at most 100").queue();

                return;
            }

            channel.getIterableHistory()
                    .takeAsync(amount)
                    .thenApplyAsync((messages) -> {
                        List<Message> goodMessages = messages.stream()
                                .filter((m) -> m.getTimeCreated().isBefore(
                                        OffsetDateTime.now().plus(2, ChronoUnit.WEEKS)
                                ))
                                .collect(Collectors.toList());

                        channel.purgeMessages(goodMessages);

                        return goodMessages.size();
                    })
                    .whenCompleteAsync(
                            (count, thr) -> channel.sendMessageFormat("Deleted `%d` messages", count).queue(
                                    (message) -> message.delete().queueAfter(10, TimeUnit.SECONDS)
                            )
                    )
                    .exceptionally((thr) -> {
                        String cause = "";

                        if (thr.getCause() != null) {
                            cause = " caused by: " + thr.getCause().getMessage();
                        }

                        channel.sendMessageFormat("Error: %s%s", thr.getMessage(), cause).queue();

                        return 0;
                    });

        }
    }
}
