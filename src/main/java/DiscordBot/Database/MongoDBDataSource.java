package DiscordBot.Database;

import DiscordBot.Utils.NickNameRoles;

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
    public List<NickNameRoles> getNickRoles(long guildId) {
        return null;
    }

    @Override
    public void addNickRole(long guildId, NickNameRoles nickRole) {

    }


}
