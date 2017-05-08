package ist.meic.cmu.server.storage;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import ist.meic.cmu.server.tool.StringParser;

public class Storage {

	private ArrayList<GPSLocation> location;
	private ArrayList<User> user;
	private ArrayList<Post> post;
	public Storage(){
		location=new ArrayList<GPSLocation>();
		user=new ArrayList<User>();
		post=new ArrayList<Post>();
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
				
					jsonLocation.add(location.get(i).getName());
					jsonLocation.add(location.get(i).getLatitude());
					jsonLocation.add(location.get(i).getLongitude());
					jsonLocation.add(location.get(i).getRadius());
					json.put("location"+i, jsonLocation);
			}
			System.out.println("json output: "+json.toJSONString());
		return json;
	}
	public boolean userLocation(String username, String lat, String lon, String sessionid) {
		
		return true;
	}
	public boolean sendPost(String title, String message, String username, String startDate, String endDate, String location, String filter, String mode, String profile) {
		if(post.size()!=0)
			for(Post p:post){
				if(p.getTitle().equals(title)
						&& p.getMessage().equals(message)
						&& p.getUsername().equals(username)
						&& p.getLocation().equals(location)){
					return false;
				}
			}
		DateFormat format = new SimpleDateFormat("dd/MM/yy hh:mm");
		Date start=null;
		Date end=null;
		try {
			start = format.parse(startDate);
			end = format.parse(endDate);
		} catch (ParseException e) {
			System.out.println("Date format error!!!");
			return false;
		}
		
		ArrayList<Profile> prof=new ArrayList<>();
		String[] profileParser=StringParser.getProfile(profile);
		for(int i=0;i<profileParser.length;i+=2){
			prof.add(new Profile(profileParser[i], profileParser[i+1]));
		}
		post.add(new Post(title, message, username, start, end, location, filter, mode, prof));
		return true;
	}
	public JSONObject getPost(String username, String latitude, String longitude) {
		JSONObject json=new JSONObject();
		ArrayList<Profile> profile=new ArrayList<Profile>();
		if(user.size()!=0&&post.size()!=0&&location.size()!=0){
			for (int i=0;i<user.size();i++) {
				if(user.get(i).getUsername().equals(username)){
					profile=user.get(i).getProfile();
					break;
				}
			}
			for(int i=0,w=0;i<location.size();i++){
				if(Algorithm.distFrom(location.get(i).getLatitude(), location.get(i).getLongitude(), Double.parseDouble(latitude), Double.parseDouble(longitude))<=
								location.get(i).getRadius()){
					for(int j=0 ; j<post.size();j++){
						JSONArray arrayPost=new JSONArray();
						if(post.get(j).getLocation().equals(location.get(i).getName())||post.get(j).getUsername().equals(username)){
							if(post.get(j).getFilter().equals("Whitelist")){
								for(Profile p:post.get(j).getProfile()){
									if(profile.contains(p)){
										arrayPost.add(post.get(j).getTitle());
										arrayPost.add(post.get(j).getMessage());
										arrayPost.add(post.get(j).getUsername());
										arrayPost.add(post.get(j).getStartDate());
										arrayPost.add(post.get(j).getEndDate());
										arrayPost.add(post.get(j).getLocation());
										arrayPost.add(post.get(j).getFilter());
										arrayPost.add(post.get(j).getMode());
										arrayPost.add(post.get(j).getProfileString());
										
										json.put("post"+w, arrayPost);
										w++;
										//bug, may cause problem
										break;
									}
								}
							}else if(post.get(j).getFilter().equals("Blacklist")){
								for(Profile p:post.get(j).getProfile()){
									if(!profile.contains(p)){
										arrayPost.add(post.get(j).getTitle());
										arrayPost.add(post.get(j).getMessage());
										arrayPost.add(post.get(j).getUsername());
										arrayPost.add(post.get(j).getStartDate());
										arrayPost.add(post.get(j).getEndDate());
										arrayPost.add(post.get(j).getLocation());
										arrayPost.add(post.get(j).getFilter());
										arrayPost.add(post.get(j).getMode());
										arrayPost.add(post.get(j).getProfileString());
										
										json.put("post"+w, arrayPost);
										w++;
										//bug, may cause problem
										break;
									}
								}
							}
						}
					}
				}
			}
		}
		System.out.println("Post result: "+json.toJSONString());
		return json;
	}
	public boolean deletePost(String title, String message, String username, String location) {
		if(post.size()!=0)
			for(Post p:post){
				if(p.getTitle().equals(title)
						&& p.getMessage().equals(message)
						&& p.getUsername().equals(username)
						&& p.getLocation().equals(location)){
					post.remove(p);
					System.out.println("Remove successful");
					return true;
				}
			}
		return false;
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
