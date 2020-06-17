package DiscordBot.Database;

import DiscordBot.Utils.NickNameRoles;
import DiscordBot.Utils.ReactionRoles;

import java.util.List;
import java.util.Map;

public class MongoDBDataSource implements DatabaseManager{
    @Override
    public String getPrefix(long guildId) {
        return null;
    }

    @Override
    public void setPrefix(long guildId, String newPrefix) {

    }

    @Override
    public List<Long> getJoinRoles(long guildId) {
        return null;
    }

    @Override
    public void addJoinRole(long guildId, long roleID) {

    }

    @Override
    public void removeJoinRole(long guildId, long roleID) {

    }

    @Override
    public List<NickNameRoles> getNickRoles(long guildId) {
        return null;
    }

    @Override
    public void addNickRole(long guildId, NickNameRoles nickRole) {

    }

    @Override
    public void removeNickRole(long guildId, NickNameRoles nickRole) {

    }

    @Override
    public List<ReactionRoles> getReactionRoles(long guildId) {
        return null;
    }

    @Override
    public void addReactionRole(long guildId, ReactionRoles reactRole) {

    }

    @Override
    public void removeReactionRole(long guildId, ReactionRoles reactRole) {

    }


}
