package ist.meic.cmu.server;

import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Manager {
	private DBConnection conn=DBConnection.getInstance();
	private ArrayList<Location> location;
	
	public void reloadLocalization(){
		location=conn.getAllLocation();
	}
	
    @RequestMapping(value="/CREATE", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject createAccount(@RequestBody JSONObject json)
    {
    	String username=(String) json.get("username");
    	String password=(String) json.get("password");
    	System.out.println(username+"\n"+password);
    	if(conn.createUser(username, password)){
    		return json;
    	}
        return null;
    }

    @RequestMapping(value="/LOGIN", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject logIn(@RequestBody JSONObject json)
    {
    	String username=(String) json.get("username");
    	String password=(String) json.get("password");
    	if(conn.verify(username, password)){
    		return json;
    	}
        return null;
    }
    
    @RequestMapping(value="/LOGOUT", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject logOut(@RequestBody JSONObject json)
    {
        return null;
    }

    @RequestMapping(value="/GETPROFILE", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getProfile(@RequestBody JSONObject json)
    {
        return null;
    }
    
    @RequestMapping(value="/SENDPROFILE", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject sendProfile(@RequestBody JSONObject json)
    {
        return null;
    }

    @RequestMapping(value="/addLocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject addLocation(@RequestBody JSONObject json)
    {
    	if(location==null){
    		reloadLocalization();
    	}
		return null;
    }
    
    @RequestMapping(value="/removeLocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject removeLocation(@RequestBody JSONObject json)
    {
    	if(location==null){
    		reloadLocalization();
    	}
		return null;
    }

    @RequestMapping(value="/checkLocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getLocation(@RequestBody JSONObject json)
    {
    	if(location==null){
    		reloadLocalization();
    	}
        return null;
    }
    
//	@RequestMapping("/")
//	@SuppressWarnings("unchecked")
//    public JSONObject getArlindo()
//    {
//    	JSONObject j=new JSONObject();
//    	j.put("name", "arlindo");
//    	j.put("username", "lindo");
//    	j.put("age", 23);
//        return j;
//    }
    
    
	@RequestMapping("/")
	@SuppressWarnings("unchecked")
    public ArrayList<Location> test()
    {
		ArrayList<Location> location=new ArrayList<Location>();
    	location.add(new Location("Sheng", 32.3213,-9.3213, 10));
    	location.add(new Location("Arlindo", 32.3323,-9.5413, 6));
        return location;
    }
    
}
