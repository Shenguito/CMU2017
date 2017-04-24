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
    	System.out.println("Preparing for register...");
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
    	System.out.println("Preparing for login...");
    	if(storage.verifyUser(username, password)){
    		System.out.println(username+"==> Login");
    		String sessionid=storage.giveSessionID(username, password);
    		json.put("sessionid", sessionid);
    		System.out.println("sessionID: "+json.get("sessionid"));
    		return json;
    	}
        return null;
    }
    
    @RequestMapping(value="/logout", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject logOut(@RequestBody JSONObject json)
    {
    	String username=(String) json.get("username");
    	String sessionid=(String) json.get("sessionid");
    	System.out.println("Preparing for logout...");
    	if(storage.logout(username, sessionid)){
    		System.out.println(username+"==> Logout");
    		System.out.println("sessionID: "+json.get("sessionid"));
    		return json;
    	}
        return null;
    }
    
    @RequestMapping(value="/addlocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject addLocation(@RequestBody JSONObject json)
    {
    	String name=(String) json.get("name");
    	String lat=(String) json.get("latitude");
    	String lon=(String) json.get("longitude");
    	String radius=(String) json.get("radius");
    	System.out.println("Preparing for adding location...");
    	if(storage.addLocation(name, lat, lon, radius)){
    		System.out.println("Location "+name+" added");
    		return json;
    	}
		return null;
    }
    
    @RequestMapping(value="/removelocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject removeLocation(@RequestBody JSONObject json)
    {
    	String name=(String) json.get("name");
    	String lat=(String) json.get("latitude");
    	String lon=(String) json.get("longitude");
    	System.out.println("Preparing for removing location...");
    	if(storage.removeLocation(name, lat, lon)){
    		System.out.println("Location "+name+" removed");
    		return json;
    	}
		return null;
    }

    @RequestMapping(value="/checklocation", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getLocation(@RequestBody JSONObject json)
    {
    	String lat=(String) json.get("latitude");
    	String lon=(String) json.get("longitude");
    	String radius=(String) json.get("radius");
    	System.out.println("Preparing for adding location...");
		return storage.getLocation(lat, lon, radius);
    }

    @RequestMapping(value="/getprofile", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject getProfile(@RequestBody JSONObject json)
    {
        return null;
    }
    
    @RequestMapping(value="/sendprofile", method={ RequestMethod.GET, RequestMethod.POST })
    @ResponseBody
    public JSONObject sendProfile(@RequestBody JSONObject json)
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
	@RequestMapping("/madje")
	@SuppressWarnings("unchecked")
    public JSONObject forJokes()
    {
    	JSONObject j=new JSONObject();
    	j.put("JP", "Jonhy Peter");
    	j.put("From", "Guarda");
    	j.put("Sex", "Unisex");
    	j.put("Hobby", "Engatatão");
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
