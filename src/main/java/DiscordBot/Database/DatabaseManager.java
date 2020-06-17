package DiscordBot.Database;

import DiscordBot.Utils.NickNameRoles;
import DiscordBot.Utils.ReactionRoles;

import java.util.List;
import java.util.Map;

public interface DatabaseManager {
    DatabaseManager INSTANCE = new SQLiteDataSource();

    String getPrefix(long guildId);
    void setPrefix(long guildId, String newPrefix);

    List<Long> getJoinRoles(long guildId);
    void addJoinRole(long guildId, long roleID);
    void removeJoinRole(long guildId, long roleID);

    List<NickNameRoles> getNickRoles(long guildId);
    void addNickRole(long guildId, NickNameRoles nickRole);
    void removeNickRole(long guildId, NickNameRoles nickRole);

    List<ReactionRoles> getReactionRoles(long guildId);
    void addReactionRole(long guildId, ReactionRoles reactRole);
    void removeReactionRole(long guildId, ReactionRoles reactRole);
}
