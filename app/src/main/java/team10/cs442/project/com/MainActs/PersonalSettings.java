package team10.cs442.project.com.MainActs;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Switch;

import java.util.zip.CheckedInputStream;

import team10.cs442.project.com.R;

public class PersonalSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_settings);

        CheckBox mycheck = (CheckBox)findViewById(R.id.checkBox);
        mycheck.setChecked(MainAct.share_on);
        mycheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked)
            {
                if ( isChecked )
                {
                    // perform logic
                    MainAct.share_on = true;
                }
                else{
                    MainAct.share_on = false;
                }

            }
        });

        Switch myswitch = (Switch)findViewById(R.id.switch1);

        myswitch.setChecked(MainAct.day_night);

        myswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    MainAct.day_night = true;
                } else {
                    MainAct.day_night = false;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.gohome, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.home) {
            Intent intent = new Intent(this, MainAct.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
