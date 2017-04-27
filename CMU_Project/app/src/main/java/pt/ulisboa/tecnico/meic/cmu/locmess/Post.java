package pt.ulisboa.tecnico.meic.cmu.locmess;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

/**
 * Created by Akilino on 16/03/2017.
 */

public class Post {

    public String title;
    public String location;
    public String user;
    public String message;
    public String mode;

    public Post(String message, String location, String user, String mode) throws UnsupportedEncodingException {
        this.message = message;
        this.location = location;
        this.user = user;
        this.mode = mode;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public static ArrayList<Post> createPostList(int numPosts){
        ArrayList<Post> posts = new ArrayList<>();

        for(int i = 1; i <= numPosts;i++){
            try {
                posts.add(new Post("Message " + i, "NewGPSLocation " + i, "User " + i, "14:30 - 14/02/2017" + i));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        return posts;
    }

}
