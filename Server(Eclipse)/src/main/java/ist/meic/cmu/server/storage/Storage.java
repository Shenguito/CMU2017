package ist.meic.cmu.server.storage;

import java.util.ArrayList;

public class Storage {

	private ArrayList<Location> location;
	private ArrayList<User> user;
	public Storage(){
		location=new ArrayList<Location>();
		user=new ArrayList<User>();
	}
	public boolean createUser(String username, String password){
		if(user.size()!=0)
		for (User tmpUser : user) {
			if(tmpUser.getUsername().equals(username)){
				return false;
			}
		}
		user.add(new User(username, password));
		return true;
		
	}
	public boolean verifyUser(String username, String password){
		if(user.size()!=0)
		for (User tmpUser : user) {
			if(tmpUser.getUsername().equals(username)&&tmpUser.getPassword().equals(password)){
				return true;
			}
		}
		return false;
	}
}
