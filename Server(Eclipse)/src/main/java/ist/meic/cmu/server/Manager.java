package ist.meic.cmu.server;
import ist.meic.cmu.server.storage.GPSLocation;
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
    public ArrayList<GPSLocation> test()
    {
		ArrayList<GPSLocation> location=new ArrayList<GPSLocation>();
    	location.add(new GPSLocation("Sheng", 32.3213,-9.3213, 10));
    	location.add(new GPSLocation("Arlindo", 32.3323,-9.5413, 6));
        return location;
    }
	
    /*
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
	
	@RequestMapping("/jonhypeter")
	@SuppressWarnings("unchecked")
    public String forJokes()
    {
		File htmlTemplateFile = new File("page.html");
    	String page="<!DOCTYPE html>"+
    	"<html>"+
    	"<head>"+
    	"<style>"+
    	"code { "+
    	"   font-family: monospace; }"+
    	"</style>"+
    	"</head>"+
    	"<body>"+
    	"<p>I'm looking for a chicken! :)</p>"+
    	"<img src=\"https://scontent.flis7-1.fna.fbcdn.net/v/t1.0-9/14639631_1122255697829888_4999803540062379478_n.jpg?oh=640c8c0a8e3d7512488f3d89a1778910&oe=59745E1D\"height=\"300\" width=\"300\">"+
    	"<p>Hello! My name is Jonhy Peter, I'm such a badboy, I like to get some chickens to eat.</p>"+
    	"<p>If you are a chicken, just call me maybe.</p>"+
    	"<p>Thanks.</p>"+
    	"<p>Oh! By the way! You can just add me on my facebook: </p>"+
    	"<p>https://www.facebook.com/profile.php?id=100001364065596&fref=hovercard </p>"+
    	"</body>"+
    	"</html>";
        return page;
    }
	*/
    
}
