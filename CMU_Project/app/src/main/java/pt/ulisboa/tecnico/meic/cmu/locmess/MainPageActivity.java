package pt.ulisboa.tecnico.meic.cmu.locmess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import org.json.simple.JSONObject;

import java.util.ArrayList;

import pt.ulisboa.tecnico.meic.cmu.locmess.connection.Action;
import pt.ulisboa.tecnico.meic.cmu.locmess.connection.Connection;
import pt.ulisboa.tecnico.meic.cmu.locmess.connection.MessageType;
import pt.ulisboa.tecnico.meic.cmu.locmess.service.BackgroundLocation;

/**
 * Created by Akilino on 09/03/2017.
 */

public class MainPageActivity extends AppCompatActivity implements NotesAdapter.ItemClickCallback{

    private static final String MESSAGE = "MESSAGE";
    private static final String SENDER = "SENDER";
    private static final String LOCATION = "LOCATION";
    private static final String MODE = "MODE";
    private static final String BUNDLE_EXTRAS = "BUNDLE_EXTRAS";

    private CurrentLocation currentLocation;

    private ArrayList<Post> posts;
    private Toolbar toolbar;
    private NotesAdapter notesAdapter;
    private FloatingActionButton floatingActionButton,floatingActionButtonCompass,floatingActionButtonPost;
    private String username;
    private BackgroundLocation locationService;

    RecyclerView recyclerView;

    Animation FabOpen, FabClose, FabRClockwise, FabRAntiClockwise;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_page);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.main_page);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        setupRecyclerView();

        floatingActionButton = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        floatingActionButtonCompass = (FloatingActionButton) findViewById(R.id.floatingActionButtonCompass);
        floatingActionButtonPost = (FloatingActionButton) findViewById(R.id.floatingActionButtonPost);

        handleFloatingActionButton();
        currentLocation = new CurrentLocation(this);

        //new BackgroundLocation(this);
    }

    private void setupRecyclerView(){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        //create Post
        posts = Post.createPostList(20);
        notesAdapter = new NotesAdapter(this, posts);
        recyclerView.setAdapter(notesAdapter);
        notesAdapter.setItemClickCallback(this);
    }

    private void handleFloatingActionButton(){
        FabClose = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_close);
        FabOpen = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.fab_open);
        FabRAntiClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_anticlockwise);
        FabRClockwise = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.rotate_clockwise);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isOpen){
                    closeActionButtons();

                    isOpen = false;
                }else{
                    openActionButtons();

                    isOpen = true;
                }
            }
        });

        floatingActionButtonCompass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActionButtons();
                isOpen = false;
                addLocation();
            }
        });

        floatingActionButtonPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closeActionButtons();
                isOpen = false;
                createPost();
            }
        });
    }

    public void closeActionButtons(){
        floatingActionButtonPost.startAnimation(FabClose);
        floatingActionButtonCompass.startAnimation(FabClose);
        floatingActionButton.startAnimation(FabRAntiClockwise);

        floatingActionButtonPost.setClickable(false);
        floatingActionButtonCompass.setClickable(false);
    }

    public void openActionButtons(){
        floatingActionButtonPost.startAnimation(FabOpen);
        floatingActionButtonCompass.startAnimation(FabOpen);
        floatingActionButton.startAnimation(FabRClockwise);

        floatingActionButtonPost.setClickable(true);
        floatingActionButtonCompass.setClickable(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_logout){

            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setMessage(R.string.logout);
            builder.setCancelable(true);

            builder.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            //locationService.stopUsingGPS();
                            logout();
                        }
                    });

            builder.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert = builder.create();
            alert.show();
        }else if(id == R.id.action_profile){
            Intent intent = new Intent(MainPageActivity.this, UserProfile.class);
            intent.putExtra("username",username);
            MainPageActivity.this.startActivity(intent);
        }else if(id == R.id.action_locations){
            Intent intent = new Intent(MainPageActivity.this, LocationActivity.class);
            MainPageActivity.this.startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void logout(){
        SharedPreferences sharedPref = getSharedPreferences("file", Context.MODE_PRIVATE);
        String username=sharedPref.getString("username", null);
        String sessionid=sharedPref.getString("sessionid", null);
        JSONObject json=new JSONObject();
        json.put("username", username);
        json.put("sessionid", sessionid);
        Action action =new Action(MessageType.logout, json);
        json=new Connection().execute(action);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("logged", false);
        editor.putString("username", null);
        editor.putString("sessionid", null);
        editor.commit();
        Intent intent = new Intent(this, LoginActivity.class);
        Toast.makeText(this, "You are now logged out!", Toast.LENGTH_SHORT).show();
        startActivity(intent);
        finish();
    }


    public void createPost(){
        Intent intent = new Intent(MainPageActivity.this, NewPost.class);
        MainPageActivity.this.startActivity(intent);
    }

    public void addLocation(){
        Intent intent = new Intent(MainPageActivity.this, NewGPSLocation.class);
        MainPageActivity.this.startActivity(intent);
    }

    public void openDialog(final int position) {
        LayoutInflater li = LayoutInflater.from(MainPageActivity.this);
        final View promptsView = li.inflate(R.layout.dialog_warning_unpost_post, null);

        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                MainPageActivity.this);

        alertDialogBuilder.setView(promptsView);

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                unpostPost(position);
                            }
                        })
                .setNegativeButton("No",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    public void unpostPost(int position){
        posts.remove(position);
        notesAdapter.notifyItemRemoved(position);
    }

    @Override
    public void onItemClick(int p) {

        openDialog(p);
        /*
        Intent intent = new Intent(this, DetailedPost.class);

        Bundle extras = new Bundle();
        extras.putString(MESSAGE,posts.get(p).getMessage());
        extras.putString(SENDER,posts.get(p).getUser());
        extras.putString(LOCATION,posts.get(p).getLocation());
        extras.putString(MODE,posts.get(p).getMode());

        intent.putExtra(BUNDLE_EXTRAS,extras);
        startActivity(intent);
        */
    }
}
