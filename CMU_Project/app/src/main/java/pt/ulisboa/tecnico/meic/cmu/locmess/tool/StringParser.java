package pt.ulisboa.tecnico.meic.cmu.locmess.tool;

/**
 * Created by Sheng on 30/04/2017.
 */

public class StringParser {
    public static String[] getLocation(String input){
        String tmp=input.substring(input.indexOf("[") + 1, input.indexOf("]"));
        String[] output=tmp.split(",");

        return output;
    }
    public static String[] getProfile(String input){
        String tmp=input.substring(input.indexOf("[") + 1, input.indexOf("]"));
        String[] output=tmp.split(",");
        return output;
    }
}
