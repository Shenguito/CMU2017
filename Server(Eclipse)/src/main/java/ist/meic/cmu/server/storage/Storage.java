package ist.meic.cmu.server.storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

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
	public String giveSessionID(String username, String password){
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getPassword().equals(password)&&tmpUser.getSessionID()==""){
					String random = new SimpleDateFormat("dd-MM-yyyy").format(new Date());
					for(int i=0;i<4;i++){
						random+=""+(new Random().nextInt(9 - 0 + 1) + 0);
					}
					
					tmpUser.setSessionID(random);
					return random;
				}
			}
		return "";
	}
}
