package e.marsgroup.iplant.Database;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by rawan on 3/20/2018.
 */

public class epochtoDate {

    public static String doJob(long seconds){
        Date date = new Date(seconds*1000);
        SimpleDateFormat sdf = new SimpleDateFormat("EEEE,MMMM d,yyyy h:mm,a", Locale.ENGLISH);
        //sdf.setTimeZone(TimeZone.getTimeZone("UTC")); //not needed in my case
        String formattedDate = sdf.format(date);
      return formattedDate;
    }
}
