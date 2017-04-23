package ist.meic.cmu.server;
import ist.meic.cmu.server.storage.Location;
import ist.meic.cmu.server.storage.Storage;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RestController
public class Manager {
	
	private Storage storage=new Storage();
	/*
	private DBConnection conn=DBConnection.getInstance();
	public void reloadLocalization(){
		location=conn.getAllLocation();
	}
	*/
    @RequestMapping(value="/create", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject createAccount(@RequestBody JSONObject json)
    {
    	String username=(String) json.get("username");
    	String password=(String) json.get("password");
    	if(storage.createUser(username, password)){
    		System.out.println(username+"==> Created");
    		return json;
    	}
        return null;
    }

    @RequestMapping(value="/login", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject logIn(@RequestBody JSONObject json)
    {
    	String username=(String) json.get("username");
    	String password=(String) json.get("password");
    	if(storage.verifyUser(username, password)){
    		System.out.println(username+"==> Login");
    		return json;
    	}
        return null;
    }
    
    @RequestMapping(value="/logout", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject logOut(@RequestBody JSONObject json)
    {
        return null;
    }

    @RequestMapping(value="/getProfile", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getProfile(@RequestBody JSONObject json)
    {
        return null;
    }
    
    @RequestMapping(value="/sendProfile", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject sendProfile(@RequestBody JSONObject json)
    {
        return null;
    }

    @RequestMapping(value="/addLocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject addLocation(@RequestBody JSONObject json)
    {
		return null;
    }
    
    @RequestMapping(value="/removeLocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject removeLocation(@RequestBody JSONObject json)
    {
		return null;
    }

    @RequestMapping(value="/checkLocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getLocation(@RequestBody JSONObject json)
    {
        return null;
    }
    
	@RequestMapping("/")
	@SuppressWarnings("unchecked")
    public JSONObject getArlindo()
    {
    	JSONObject j=new JSONObject();
    	j.put("Tiago", "Cruz");
    	j.put("estupidamente", "lindo");
    	j.put("dava-lhe de 0 a 5", 1);
        return j;
    }
    
    
//	@RequestMapping("/")
//	@SuppressWarnings("unchecked")
//    public ArrayList<Location> test()
//    {
//		ArrayList<Location> location=new ArrayList<Location>();
//    	location.add(new Location("Sheng", 32.3213,-9.3213, 10));
//    	location.add(new Location("Arlindo", 32.3323,-9.5413, 6));
//        return location;
//    }
//    
}
