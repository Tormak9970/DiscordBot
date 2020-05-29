package DiscordBot.Tasks;


import java.util.ArrayList;

public class Commands {

    static ArrayList<String> helpIndex = new ArrayList<>();


    public static void addCommand(String newCommand){
        if(helpIndex.indexOf(newCommand) == -1){
            helpIndex.add(newCommand);
        }
    }

}
