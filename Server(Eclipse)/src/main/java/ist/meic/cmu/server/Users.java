package ist.meic.cmu.server;

import java.util.HashMap;

import com.google.gson.Gson;

public class Users {
	
    private HashMap<String, userInfo> accounts = new HashMap<>();
    private HashMap<String, Integer> locations = new HashMap<>();
    private Gson gson = new Gson();
	
	public Users(){
		
	}

	public boolean createAccount(String username, String password, String name) {
		if (!accounts.containsKey(username))
        {
            userInfo _userInfo = new userInfo(password, name);
            accounts.put(username, _userInfo);
            return true;
        }
        else
            return false;
	}

	public String logIn(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public void addLocation(String username, int newPoints) {
		// TODO Auto-generated method stub
		
	}

	public String getProfile(String username) {
		// TODO Auto-generated method stub
		return null;
	}

	public int getLocation(String username) {
		// TODO Auto-generated method stub
		return 0;
	}
}
