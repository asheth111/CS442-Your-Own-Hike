package team10.cs442.project.com.Friend_Lib;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.SaveCallback;

import java.util.List;

import team10.cs442.project.com.MainActs.MainAct;
import team10.cs442.project.com.R;
import team10.cs442.project.com.SharedLibs.MomentItemListActivity;

public class Add_Del_Friends extends Activity {

    public String flag = "Add";
    public String op_done = "false";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActionBar().setTitle("YourOwnHike");
        setContentView(R.layout.activity_add__del__friends);

        Button but_fri_done = (Button)findViewById(R.id.fri_done);

        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            Log.v("intent send", "returnedA");
            String checker = extras.getString("command");
            if(checker.equals("Add") ){
                //if returned from log in, then set user information
                getActionBar().setTitle("Add Friend");
                flag = "Add";
                but_fri_done.setText("+");
            }
            else{
                getActionBar().setTitle("Delete Friend");
                flag = "del";
                but_fri_done.setText("-");
            }
            Log.v("intent send", "passed");

        }

    }

    public void search_add_del(View view) {
        final EditText username_in = (EditText) findViewById(R.id.fri_input);

        if (flag.equals("Add")) {
            //search first
            ParseQuery<ParseUser> query = ParseUser.getQuery();
            query.whereEqualTo("username", username_in.getText().toString());
            query.findInBackground(new FindCallback<ParseUser>() {
                public void done(List<ParseUser> objects, ParseException e) {
                    if (e == null) {
                        // The query was successful.
                        Toast.makeText(getApplicationContext(), "User Added", Toast.LENGTH_LONG).show();
                        add_friend(username_in.getText().toString());
                        //op_done = "true";
                    } else {
                        // Something went wrong.
                        Toast.makeText(getApplicationContext(), "No such user", Toast.LENGTH_LONG).show();
                    }
                }
            });
        } else {//del condition
            ParseQuery<ParseObject> query = ParseQuery.getQuery("Friends");
            query.whereEqualTo("UserName", MainAct.user_saved);
            query.findInBackground(new FindCallback<ParseObject>() {
                public void done(List<ParseObject> friendList, com.parse.ParseException e) {
                    if (e == null) {
                        int cnt = 0;
                        Log.d("score", "Retrieved " + friendList.size() + " scores");
                        for (ParseObject friendObject : friendList) {
                            // use dealsObject.get('columnName') to access the properties of the Deals object
                            String tmp_loc = friendObject.get("Friends").toString();
                            if (tmp_loc.equals(username_in.getText().toString())) {
                                friendObject.deleteInBackground();
                                //op_done = "true";
                            }
                        }
                    } else {
                        Log.d("score", "Error: " + e.getMessage());
                    }
                }
            });
        }
        Intent intent = new Intent(getBaseContext(), MainAct.class);
        startActivity(intent);
    }

    public void add_friend(String name){
        ParseObject new_friend = new ParseObject("Friends");
        new_friend.put("UserName", MainAct.user_saved);
        new_friend.put("Friends", name);

        new_friend.saveInBackground(new SaveCallback() {
            public void done(ParseException e) {
                if (e == null) {
                    // Saved successfully.
                    Log.v("done","User saved!");
                } else {
                    // The save failed.
                    Log.v("error","User update error: " + e.toString());
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add__del__friends, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.fri_home) {
            Intent intent = new Intent(this, MainAct.class);
            startActivity(intent);
        }
        else if (id == R.id.fri_list) {
            Intent intent = new Intent(this, FriendItemListActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
