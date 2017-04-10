package ist.meic.cmu.server;

import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Manager {
	DBConnection conn=DBConnection.getInstance();
    //Function to create account
    //If it returns true    -> Account was created successfully!
    //If it returns false   -> Username already exists!
    @RequestMapping("/create")
    public boolean createAccount(@RequestParam(value="username") String username, @RequestParam(value="password") String password)
    {
    	if(conn.createUser("username", "password")){
    		return true;
    	}
        return false;
    }

    @RequestMapping("/logIn")
    public boolean logIn(@RequestParam(value="username") String username, @RequestParam(value="password") String password)
    {
    	if(conn.verify(username, password)){
    		return true;
    	}
        return false;
    }

    @RequestMapping("/getProfile")
    public String getProfile(@RequestParam(value="username") String username)
    {
    	
        return null;
    }
    
    @RequestMapping("/sendProfile")
    @SuppressWarnings("unchecked")
    public JSONObject sendProfile(String username)
    {
    	JSONObject j=new JSONObject();
    	j.put("name", username);
    	j.put("username", "lindo");
    	j.put("age", 23);
        return j;
    }

    @RequestMapping("/addLocation")
    public void addPoints(@RequestParam(value="username") String username, @RequestParam(value="points") int newLocations)
    {
    }

    @RequestMapping("/checkLocation")
    public int getPoints(@RequestParam(value="username") String username)
    {
        return 0;
    }
    
	@RequestMapping("/")
	@SuppressWarnings("unchecked")
    public JSONObject getArlindo()
    {
    	JSONObject j=new JSONObject();
    	j.put("name", "arlindo");
    	j.put("username", "lindo");
    	j.put("age", 23);
        return j;
    }
    
}
