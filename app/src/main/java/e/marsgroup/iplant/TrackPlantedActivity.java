package e.marsgroup.iplant;

import android.annotation.SuppressLint;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import e.marsgroup.iplant.Database.*;

import e.marsgroup.iplant.RecyclersandAdapters.PlantinNotification;
import e.marsgroup.iplant.RecyclersandAdapters.PlantinNotificationAdapter;

//I USED THE OFFICIAL SAMPLE OF FULL SCREEN ACTIVITY FROM GOOGLE TEAM
//BUT I MODIFIED IT A BIT (NAH A LOT) TO SUIT MY USE
//Thanks

//NOTE : I COMPLETELY REMOVED THE CONTROLS CUZ THIS WAS MEANT FOR A VIDEO PLAYER
//OR SOMETHING LIKE THAT
//SO I REMOVED THE CONTROLS AND NOW I GOT THIS
//WHICH WORKS FOR ME TOOOOOOOO
//hehe :)
//Good jOb right!
//if life was all coding then I would have hacked my own life!
//21318353A

/**
 * An  full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class TrackPlantedActivity extends AppCompatActivity {
    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
            mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                    | View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };
    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
        }
    };
    private boolean mVisible;
    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };

    databaseHelper db;
    TextView notfound;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_track_planted);

        db=new databaseHelper(getApplicationContext());


        //Lets Set Orientation Lock cuz our activity is meant to be portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mVisible = true;
        mContentView = findViewById(R.id.fullscreen_content);


        // Set up the user interaction to manually show or hide the system UI.
        mContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                toggle();
            }
        });

        //Recycler $#1T
        ArrayList<PlantinNotification> list = returnGeneratedArray(db.getAllPlanteds()); //Returns All Planted then Method [Process] it

        RecyclerView recycler_view = (RecyclerView) findViewById(R.id.recycler_view_FullScreen_Notification);

        //RecyclerView  look like a vertical list view
        recycler_view.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        //RecyclerView look like a grid view
        //recycler_view.setLayoutManager(new GridLayoutManager(this, 2)); //NOT USED BUT ALSO COMPATIBLE WITH THIS TOO


        PlantinNotificationAdapter adapter = new PlantinNotificationAdapter(list, this);
        recycler_view.setAdapter(adapter);

        //One condition if there was no plants in the planting table
        //that matches our range then we would simply show the notfound textview and enlarge it
        //and put a big bottle of Red color on it to be
        //extra visibly
        //on that beautiful white background

        notfound=findViewById(R.id.notfound);
        if(list.size()>0)
        {
            notfound.setVisibility(View.GONE); //YH GONE meaning we kill it (nah hhh we just removed it from the entire thing)
            //so it takes no space ,won't mess our layout
        }
        else
        {
            //and if we didn't have any plants then we would do the thing we mentioned before and do it yh simply :)
            notfound.setText("NO ITEMS");
            notfound.setTextColor(Color.RED);
            notfound.setTextSize(32f);
            notfound.setGravity(Gravity.CENTER);
        }

        //Finally Lets Close DB ,So it wont leak $^_^$
        db.closeDB();
    }

    //Generate the array for our Recycler
    public ArrayList<PlantinNotification> returnGeneratedArray(List <planted> a){
        ArrayList<PlantinNotification> returnArray=new ArrayList<>();
        long currentEpochTimeSeconds=System.currentTimeMillis()/1000; //We need seconds

        long start,end;

        for(planted p: a){
            //First WE compare the times with current time to see if its already finished to not
            //Include it in the Notification
            PlantinNotification tmp=new PlantinNotification();

            start=p.getStartEpochTime();
            end=p.getEstimatedEndEpochTime();
           //First lets check if the plant is in range ,so We wont add old plants in notification
            if(start<currentEpochTimeSeconds & end> currentEpochTimeSeconds) //Start | Current | End
            {
                plant tmp2=db.getPlant(p.getIdofPlant()); //GET Plant From DB
                tmp.setImageInPlantNotification(tmp2.getImgurl()); //GETS IMG
                tmp.setTitleofPlantinNotification("planting "+tmp2.getName());
                tmp.setStartDate("Start: "+epochtoDate.doJob(start));
                tmp.setEndDate("End: "+epochtoDate.doJob(end));
                tmp.setWaterinfoInNotification(tmp2.getWaterinfo());
                returnArray.add(tmp);
            }
        }
//Mr.R 20318917
    return returnArray;}

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in delay milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }
}
