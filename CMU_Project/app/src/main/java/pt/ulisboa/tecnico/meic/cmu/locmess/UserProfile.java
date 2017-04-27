package pt.ulisboa.tecnico.meic.cmu.locmess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.simple.JSONObject;

import java.util.ArrayList;

import pt.ulisboa.tecnico.meic.cmu.locmess.connection.Action;
import pt.ulisboa.tecnico.meic.cmu.locmess.connection.Connection;
import pt.ulisboa.tecnico.meic.cmu.locmess.connection.MessageType;

/**
 * Created by Akilino on 02/04/2017.
 */

public class UserProfile extends AppCompatActivity implements PropertiesAdapterUser.ItemClickCallback {

    private Toolbar toolbar;
    private String key,value;
    private TextView nameTextView;
    ArrayList<Property> properties;
    private PropertiesAdapterUser adapter;
    private Property property;
    String username;
    RecyclerView recyclerView;
    String[] keys;
    ArrayList<String> keysList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        Intent intent = getIntent();
        username = intent.getStringExtra("username");

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle(R.string.user_profile);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);

        nameTextView = (TextView) findViewById(R.id.textView_username);
        nameTextView.setText(username);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView_properties);
        setupRecyclerView();

        keysList = new ArrayList<>();
        keys = getResources().getStringArray(R.array.keys);

        for(int i = 0; i < keys.length; i++)
            keysList.add(keys[i]);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(createHelperCallback());
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    public void setupRecyclerView(){
        properties = new ArrayList<>();
        adapter = new PropertiesAdapterUser(this,properties);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setItemClickCallback(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        menu.removeItem(R.id.action_profile);
        menu.removeItem(R.id.action_locations);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_logout){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
            builder1.setMessage(R.string.logout);
            builder1.setCancelable(true);

            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                            logout();
                        }
                    });

            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });

            AlertDialog alert11 = builder1.create();
            alert11.show();
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


    public void openDialogAddProperty(View view){
        LayoutInflater li = LayoutInflater.from(UserProfile.this);
        final View promptsView = li.inflate(R.layout.dialog_edit_property, null);

        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                UserProfile.this);

        alertDialogBuilder.setView(promptsView);

        final AutoCompleteTextView keyValue = (AutoCompleteTextView) promptsView
                .findViewById(R.id.autoCompleteTextView1);

        final ArrayAdapter<String> completeTextViewAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,keysList);
        keyValue.setAdapter(completeTextViewAdapter);

        final EditText valueValue = (EditText) promptsView
                .findViewById(R.id.valueEditText);

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Add Property")
                .setPositiveButton("Add",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                String key = keyValue.getText().toString();
                                String value = valueValue.getText().toString();

                                property = new Property(key, value);

                                if(!keysList.contains(key)){
                                    keysList.add(key);
                                    completeTextViewAdapter.notifyDataSetChanged();
                                }

                                Toast.makeText(UserProfile.this, "value: " + key + ":" + value, Toast.LENGTH_SHORT).show();
                                properties.add(0,property);
                                adapter.notifyItemInserted(0);
                                //adapter.notifyItemInserted(properties.size());
                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    public void openDialogEditProperty(final int position){
        LayoutInflater li = LayoutInflater.from(UserProfile.this);
        final View promptsView = li.inflate(R.layout.dialog_edit_property, null);

        android.support.v7.app.AlertDialog.Builder alertDialogBuilder = new android.support.v7.app.AlertDialog.Builder(
                UserProfile.this);

        alertDialogBuilder.setView(promptsView);

        final AutoCompleteTextView keyValue = (AutoCompleteTextView) promptsView
                .findViewById(R.id.autoCompleteTextView1);

        ArrayAdapter<String> completeTextViewAdapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,keysList);
        keyValue.setAdapter(completeTextViewAdapter);

        final EditText valueValue = (EditText) promptsView
                .findViewById(R.id.valueEditText);

        keyValue.setText(properties.get(position).getKey());
        valueValue.setText(properties.get(position).getValue());

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Edit Property")
                .setPositiveButton("Save",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                // get user input and set it to result
                                // edit text
                                key = keyValue.getText().toString();
                                value = valueValue.getText().toString();

                                property = new Property(key, value);

                                if(!keysList.contains(key)){
                                    keysList.add(key);
                                    adapter.notifyDataSetChanged();
                                }

                                Toast.makeText(UserProfile.this, "value: " + key + ":" + value, Toast.LENGTH_SHORT).show();

                                properties.add(position,property);
                                properties.remove(position+1);
                                adapter.notifyDataSetChanged();

                            }
                        })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                dialog.cancel();
                            }
                        });

        android.support.v7.app.AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    public void openDialogViewProperty(final int position){
        LayoutInflater li = LayoutInflater.from(UserProfile.this);
        final View promptsView = li.inflate(R.layout.dialog_view_property, null);

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                UserProfile.this);

        alertDialogBuilder.setView(promptsView);

        TextView keyValue = (TextView) promptsView
                .findViewById(R.id.textViewDialogViewKey);


        keyValue.setText(properties.get(position).getText());

        alertDialogBuilder
                .setCancelable(false)
                .setTitle("Property")
                .setPositiveButton("Edit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                openDialogEditProperty(position);
                            }
                        })
                .setNegativeButton("Delete",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog,int id) {
                                properties.remove(position);
                                adapter.notifyItemRemoved(position);
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    @Override
    public void onItemClick(int p) {
        openDialogViewProperty(p);

        Toast.makeText(this, "Position " + p, Toast.LENGTH_SHORT).show();
    }

    private ItemTouchHelper.Callback createHelperCallback(){
        ItemTouchHelper.SimpleCallback simpleItemTouchCallback =
                new ItemTouchHelper.SimpleCallback(ItemTouchHelper.UP | ItemTouchHelper.DOWN,
                        ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT){

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {

                        moveItem(viewHolder.getAdapterPosition(), target.getAdapterPosition());

                        return true;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {

                        deleteItem(viewHolder.getAdapterPosition());
                    }
                };
        return simpleItemTouchCallback;
    }

    private void moveItem(int oldPos, int newPos){
        Property property = properties.get(oldPos);
        properties.remove(oldPos);
        properties.add(newPos,property);
        adapter.notifyItemMoved(oldPos,newPos);
    }

    private void deleteItem(final int position){
        properties.remove(position);
        adapter.notifyItemRemoved(position);
    }
}
