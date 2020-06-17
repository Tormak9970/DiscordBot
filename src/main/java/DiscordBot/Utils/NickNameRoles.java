package DiscordBot.Utils;

public class NickNameRoles{

    private String nickName;
    private long roleID;
    private int type;

    public NickNameRoles(String nickName, long roleID, int type){
        this.nickName = nickName;
        this.roleID = roleID;
        this.type = type;
    }

    public String getNickName() {
        return nickName;
    }

    public long getRoleID() {
        return roleID;
    }

    public int getType() {
        return type;
    }
}
