package DiscordBot.Tasks.Music;

import java.net.MalformedURLException;
import java.net.URL;

public class Music {

    public static boolean isURL(String url){
        try{
            new URL(url);
            return true;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
