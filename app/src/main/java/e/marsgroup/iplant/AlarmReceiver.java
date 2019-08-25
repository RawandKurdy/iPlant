package e.marsgroup.iplant;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import java.util.Random;

import br.com.goncalves.pugnotification.notification.PugNotification;

/**
 * Created by rawan on 3/19/2018.
 */
//NOTIFICATIONS ARE NOT SUPPORTED ON OREO //Library need to be updated //At last compile time was not available
    //App Tested on Android 7.0
public class AlarmReceiver extends BroadcastReceiver {
    //This class is called each time
    // the BroadcastReceiver receives the broadcast for this class (or this specific broadcast)

    Random r = new Random(); //we use this ,to get a random notification id
    @Override
    public void onReceive(Context context, Intent intent) {
        //Default Notification Sound of the System
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        //Here ,we are using PugNotification Library to make the notification to
        //provide best functionality and best compatibility
        PugNotification.with(context)
                .load().identifier(r.nextInt(10000))
                .title("Reminder Click for progress details")
                .message("Don't forget to water your plants")
                .bigTextStyle("Don't forget to water your plants daily or weekly depending on the info we provided")
                .click(TrackPlantedActivity.class).autoCancel(true).lights(Color.YELLOW,3000,3000)
                .smallIcon(android.R.drawable.ic_dialog_info).vibrate(new long[]{1000, 1000, 1000, 1000, 1000})
                .largeIcon(R.drawable.progressplant).sound(alarmSound)
                .flags(Notification.DEFAULT_ALL)
                .simple()
                .build();

        //If the user click on the notification he or she will be redirected to the Track Planted Activity class
        //Properties of the notification it does have sound ,vibration and led lights with it

    }
//Hoping this notification will be useful!
    //will it can be useful if the user know how to use it
    //and isn't lazy to dismiss it
    //every time
    //which is expected by the way
    //21318348A
    //mR.R
}