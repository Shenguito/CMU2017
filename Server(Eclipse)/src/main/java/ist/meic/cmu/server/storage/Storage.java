package ist.meic.cmu.server.storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

public class Storage {

	private ArrayList<GPSLocation> location;
	private ArrayList<User> user;
	private ArrayList<Post> post;
	public Storage(){
		location=new ArrayList<GPSLocation>();
		user=new ArrayList<User>();
		user.add(new User("a", "a"));
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
				if(tmpUser.getUsername().equals(username)&&tmpUser.getPassword().equals(password)&&tmpUser.getSessionID().equals("")){
					String random = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
					for(int i=0;i<4;i++){
						random+=""+(new Random().nextInt(9 - 0 + 1) + 0);
					}
					tmpUser.setSessionID(random);
					return random;
				}
			}
		return "";
	}
	public boolean logout(String username, String session){
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(session)){
					tmpUser.setSessionID("");
					return true;
				}
			}
		return false;
	}
	public boolean addLocation(String name, String lat, String lon, String radius){

		if(location.size()!=0)
			for (GPSLocation tmplocation : location) {
				if(tmplocation.getName().equals(name)||
						(tmplocation.getLatitude()==Double.parseDouble(lat)&&
						tmplocation.getLongitude()==Double.parseDouble(lon))){
					return false;
				}
			}
		System.out.println("Location added:\n"+"name: "+name+
			"latitude: "+Double.parseDouble(lat)+
				"longitude: "+Double.parseDouble(lon)+
			"radius: "+Integer.parseInt(radius));
		location.add(new GPSLocation(name, Double.parseDouble(lat), Double.parseDouble(lon), Integer.parseInt(radius)));
		return true;
	}
	public boolean removeLocation(String name, String lat, String lon) {
		if(location.size()!=0)
			for (int i=0;i<location.size();i++) {
				if(location.get(i).getName().equals(name)&&
						location.get(i).getLatitude()==Double.parseDouble(lat)&&
								location.get(i).getLongitude()==Double.parseDouble(lon)){
					location.remove(i);
					return true;
				}
			}
		return false;
	}
	public JSONObject getLocation(String lat, String lon) {
		JSONObject json=new JSONObject();
		if(location.size()!=0)
			for (int i=0;i<location.size();i++) {
				JSONArray jsonLocation = new JSONArray();
				System.out.println("Distance between: "+location.get(i).getLatitude()+" --- "+location.get(i).getLongitude());
				System.out.println("and: "+Double.parseDouble(lat)+" --- "+Double.parseDouble(lon));
				System.out.println("is: "+Algorithm.distFrom(location.get(i).getLatitude(), location.get(i).getLongitude(), Double.parseDouble(lat), Double.parseDouble(lon))+"m");
				if(Algorithm.distFrom(location.get(i).getLatitude(), location.get(i).getLongitude(), Double.parseDouble(lat), Double.parseDouble(lon))<=
								location.get(i).getRadius()){
					
					jsonLocation.add(location.get(i).getName());
					jsonLocation.add(location.get(i).getLatitude());
					jsonLocation.add(location.get(i).getLongitude());
					jsonLocation.add(location.get(i).getRadius());
					json.put("location"+i, jsonLocation);
				}
			}
			System.out.println("json output: "+json.toJSONString());
		return json;
	}
	public boolean userLocation(String username, String lat, String lon, String sessionid) {
		
		return true;
	}
	public boolean sendPost(String username, String lat, String lon, String radius, String type) {
		if(post.size()!=0)
			for(Post p:post){
				if(p.getUsername().equals("username")
						&& String.valueOf(p.getLatitude()).equals(lat)
						&& String.valueOf(p.getLongitude()).equals(lon)){
					return false;
				}
			}
		post.add(new Post(username, Double.parseDouble(lat), Double.parseDouble(lon), Integer.parseInt(radius), type));
		return true;
	}
	public boolean deletePost(String username, String lat, String lon) {
		if(post.size()!=0)
			for(Post p:post){
				if(p.getUsername().equals("username")
						&& String.valueOf(p.getLatitude()).equals(lat)
						&& String.valueOf(p.getLongitude()).equals(lon)){
					post.remove(p);
					return true;
				}
			}
		return false;
	}
	
	//maybe it's not necessary-2 (there is 1)
	public JSONObject getPost(String lat, String lon, JSONObject type) {
		JSONObject json=new JSONObject();
		if(post.size()!=0)
			for (int i=0;i<post.size();i++) {
				JSONArray jsonPost = new JSONArray();
				if(type.toJSONString().split(post.get(i).getType()).length>1)
				if(Algorithm.distFrom(post.get(i).getLatitude(), post.get(i).getLongitude(), Double.parseDouble(lat), Double.parseDouble(lon))<=
								post.get(i).getRadius()){
					jsonPost.add(post.get(i).getUsername());
					jsonPost.add(post.get(i).getLatitude());
					jsonPost.add(post.get(i).getLongitude());
					jsonPost.add(post.get(i).getRadius());
					json.put("post"+i, jsonPost);
				}
			}
		return json;
	}
	public boolean addProfile(String username, String sessionid, String key, String value) {
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(sessionid)){
					for(Profile profile:tmpUser.getProfile()){
						if(profile.getKey().equals(key)&&profile.getValue().equals(value))
						return false;
					}
					System.out.println("add: "+key+" : "+value);
					tmpUser.addProfile(new Profile(key, value));
					return true;
				}
			}
		return false;
	}
	public JSONObject getProfile(String username, String sessionid) {
		JSONObject json=new JSONObject();
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(sessionid)){
					for(int i=0; i<tmpUser.getProfile().size();i++){
						JSONArray jsonProfile=new JSONArray();
						jsonProfile.add(tmpUser.getProfile().get(i).getKey());
						jsonProfile.add(tmpUser.getProfile().get(i).getValue());
						json.put("profile"+i, jsonProfile);
					}
				}
			}
		return json;
	}
	public boolean removeProfile(String username, String sessionid, String key, String value) {
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(sessionid)){
					for(Profile profile:tmpUser.getProfile()){
						if(profile.getKey().equals(key)&&profile.getValue().equals(value)){
							tmpUser.removeProfile(key, value);
							System.out.println("remove: "+key+" : "+value);
							return true;
						}
					}
				}
			}
		return false;
	}
	public boolean editProfile(String username, String sessionid, String oldkey, String oldvalue, String newkey,
			String newvalue) {
		JSONObject json=new JSONObject();
		if(user.size()!=0)
			for (User tmpUser : user) {
				if(tmpUser.getUsername().equals(username)&&tmpUser.getSessionID().equals(sessionid)){
					for(int i=0; i<tmpUser.getProfile().size();i++){
						if(tmpUser.getProfile().get(i).getKey().equals(oldkey)&&tmpUser.getProfile().get(i).getValue().equals(oldvalue)){
							tmpUser.getProfile().get(i).setKey(newkey);
							tmpUser.getProfile().get(i).setValue(newvalue);
							return true;
						}
					}
				}
			}
		return false;
	}
}
