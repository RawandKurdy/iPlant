package e.marsgroup.iplant;

import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;
import com.ajts.androidmads.sqliteimpex.SQLiteImporterExporter;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import e.marsgroup.iplant.fragments.categoryframe;
import e.marsgroup.iplant.fragments.customframe;
import e.marsgroup.iplant.fragments.homeframe;
import e.marsgroup.iplant.fragments.progressframe;
import e.marsgroup.iplant.fragments.settingsframe;
import e.marsgroup.iplant.Database.databaseHelper;

public class HomeScreen extends AppCompatActivity {

    //OUR LOVELY HOME SCREEN OH GOD!
    //App Tested on Android 7.0

    private TextView mTextMessage;

    //db here too ,we need it for a bit of check here too
    databaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme); //SplashScreen Show if there was a need for it ,if not it won't
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        //Lets Set Orientation Lock cuz our activity is meant to be portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        db=new databaseHelper(getApplicationContext());

        //For initial start (first) start
        //when it first starts it check it then does the job if the plants table was empty
        if(db.getCountOfPlants()==0)
        {//Add DB load from Assets Folder

            SQLiteImporterExporter sqLiteImporterExporter = new SQLiteImporterExporter(getApplicationContext(), db.getDatabaseName());

            try {
                sqLiteImporterExporter.importDataBaseFromAssets();


                //Debugging
                Log.d("d","Loading DB from assets was successful");

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(this, "Initial Start Failed :( ,Contact the developer", Toast.LENGTH_LONG).show();

                //Debugging
                Log.d("d","Loading DB from assets failed");

            }
            finally {
                sqLiteImporterExporter.close();
                Toast.makeText(this, "Welcome Dear User :) \n Thanks for installing our Application \n Group T", Toast.LENGTH_LONG).show();
            }

        }//Assets

        //db close for avoiding leak
        db.closeDB();


        loadFrag(new homeframe()); //Loading the first Fragment to our screen so it won't be empty
         mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        callRepeatingNotification(); //setting up the alarms

    }

    //Listener Variable for our Navigation Bar ::::::)
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    setTitle(R.string.title_home);
                   //Maybe I might add an extra thing here
                    loadFrag(new homeframe());
                    return true;
                case R.id.navigation_category:
                 setTitle(R.string.title_category);
                    //Maybe I might add an extra thing here
                  loadFrag(new categoryframe());
                    return true;
                case R.id.navigation_progress:
                    setTitle(R.string.title_progress);
                    //Maybe I might add an extra thing here
                    loadFrag(new progressframe());
                    return true;
                case R.id.navigation_custom:
                    loadFrag(new customframe());
                    setTitle(R.string.title_custom);
                    //Maybe I might add an extra thing here
                    return true;
                case R.id.navigation_settings:
                    loadFrag(new settingsframe());
                    //Maybe I might add an extra thing here
                    setTitle(R.string.title_settings);
                    return true;
            }return false;
        }
    };

    //This method only loads a fragment to a container
    public void loadFrag(Fragment f){
        FragmentManager fm=getFragmentManager();
        FragmentTransaction fmt=fm.beginTransaction();
        fmt.replace(R.id.maincontainer,f);
        fmt.commit();
    }

    //Create the repeating Alarms to notify us
    //First kicks at 8AM in the morning
    //Second kicks at 8PM in the evening
    public void callRepeatingNotification(){
        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.HOUR_OF_DAY, 8);
        cal1.set(Calendar.MINUTE, 0);
        cal1.set(Calendar.SECOND, 0);

        Calendar cal2 = Calendar.getInstance();
        cal2.set(Calendar.HOUR_OF_DAY, 20);
        cal2.set(Calendar.MINUTE, 0);
        cal2.set(Calendar.SECOND, 0);

// Test if the times are in the past, if they are add one day
        Calendar now = Calendar.getInstance();
        if(now.after(cal1))
            cal1.add(Calendar.HOUR_OF_DAY, 24);
        if(now.after(cal2))
            cal2.add(Calendar.HOUR_OF_DAY, 24);

        //Debugging
        Log.d("d","now"+now);
        Log.d("d","c1"+now.after(cal1));
        Log.d("d","c2"+now.after(cal2));

//  two different PendingIntents, they MUST have different requestCodes
        Intent intent = new Intent(this, AlarmReceiver.class);
        PendingIntent morningAlarm = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent eveningAlarm = PendingIntent.getBroadcast(getApplicationContext(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

//  both alarms, set to repeat once every day
        AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal1.getTimeInMillis(), DateUtils.DAY_IN_MILLIS, morningAlarm);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, cal2.getTimeInMillis(), DateUtils.DAY_IN_MILLIS, eveningAlarm);

    }
}
