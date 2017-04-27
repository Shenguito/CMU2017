package ist.meic.cmu.server.storage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.json.simple.JSONObject;

public class Storage {

	private ArrayList<GPSLocation> location;
	private ArrayList<User> user;
	private ArrayList<Post> post;
	public Storage(){
		location=new ArrayList<GPSLocation>();
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
				if(tmplocation.getName().equals(name)&&
						tmplocation.getLatitude()==Double.parseDouble(lat)&&
						tmplocation.getLongitude()==Double.parseDouble(lon)){
					return false;
				}
			}
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
	public JSONObject getLocation(String lat, String lon, String radius) {
		JSONObject json=new JSONObject();
		if(location.size()!=0)
			for (int i=0;i<location.size();i++) {
				JSONObject jsonLocation=new JSONObject();
				
				if(Algorithm.distFrom(location.get(i).getLatitude(), location.get(i).getLongitude(), Double.parseDouble(lat), Double.parseDouble(lon))<=
								location.get(i).getRadius()){
					jsonLocation.put("name", location.get(i).getName());
					jsonLocation.put("latitude", location.get(i).getLatitude());
					jsonLocation.put("longitude", location.get(i).getLongitude());
					jsonLocation.put("radius", location.get(i).getRadius());
					json.put("location"+i, jsonLocation);
				}
			}
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
				JSONObject jsonPost=new JSONObject();
				if(type.toJSONString().split(post.get(i).getType()).length>1)
				if(Algorithm.distFrom(post.get(i).getLatitude(), post.get(i).getLongitude(), Double.parseDouble(lat), Double.parseDouble(lon))<=
								post.get(i).getRadius()){
					jsonPost.put("username", post.get(i).getUsername());
					jsonPost.put("latitude", post.get(i).getLatitude());
					jsonPost.put("longitude", post.get(i).getLongitude());
					jsonPost.put("type", post.get(i).getRadius());
					json.put("post"+i, jsonPost);
				}
			}
		return json;
	}
}
