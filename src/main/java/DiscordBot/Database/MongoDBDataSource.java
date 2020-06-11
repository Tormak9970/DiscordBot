package DiscordBot.Database;

public class MongoDBDataSource implements DatabaseManager{
    @Override
    public String getPrefix(long guildId) {
        return null;
    }

    @Override
    public void setPrefix(long guildId, String newPrefix) {

    }
}
