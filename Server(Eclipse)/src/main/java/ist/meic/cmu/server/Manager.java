package ist.meic.cmu.server;

import org.springframework.web.bind.annotation.RestController;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class Manager {
    Users users = new Users();


    //Function to create account
    //If it returns true    -> Account was created successfully!
    //If it returns false   -> Username already exists!
    @RequestMapping("/create")
    public boolean createAccount(@RequestParam(value="username") String username, @RequestParam(value="name") String name,
                          @RequestParam(value="age") String age, @RequestParam(value="password") String password)
    {
        return users.createAccount(username, password, name);
    }

    @RequestMapping("/logIn")
    public String logIn(@RequestParam(value="username") String username)
    {
        return users.logIn(username);
    }

    @RequestMapping("/getProfile")
    public String getProfile(@RequestParam(value="username") String username)
    {
        return users.getProfile(username);
    }
    
    @RequestMapping("/sendProfile")
    @SuppressWarnings("unchecked")
    public JSONObject sendProfile(String username)
    {
    	JSONObject j=new JSONObject();
    	j.put("name", "arlindo");
    	j.put("username", "lindo");
    	j.put("age", 23);
        return j;
    }

    @RequestMapping("/addLocation")
    public void addPoints(@RequestParam(value="username") String username, @RequestParam(value="points") int newLocations)
    {
        users.addLocation(username, newLocations);
    }

    @RequestMapping("/checkLocation")
    public int getPoints(@RequestParam(value="username") String username)
    {
        return users.getLocation(username);
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
