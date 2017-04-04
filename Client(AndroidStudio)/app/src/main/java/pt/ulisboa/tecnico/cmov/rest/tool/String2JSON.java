package pt.ulisboa.tecnico.cmov.rest.tool;


import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 * Created by Sheng on 04/04/2017.
 */

public class String2JSON {
    public JSONObject getJSON(String string){
        JSONParser parser = new JSONParser();
        JSONObject json = null;
        try {
            json = (JSONObject) parser.parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return json;
    }
}
