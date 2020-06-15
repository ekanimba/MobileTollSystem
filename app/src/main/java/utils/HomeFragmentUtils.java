package utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class HomeFragmentUtils {

    public String getDueDate() {
        Calendar c = Calendar.getInstance();
        c.add(Calendar.DATE, 14);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");

        return simpleDateFormat.format(c.getTime());
    }

    public String getGreetings(Calendar c) {

        String greetings = null;
        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);
        if (timeOfDay >= 0 && timeOfDay < 12) {
            greetings = "Good Morning";
        } else if (timeOfDay >= 12 && timeOfDay < 16) {
            greetings = "Good Afternoon";
        } else if (timeOfDay >= 16 && timeOfDay < 21) {
            greetings ="Good Evening";
        } else if (timeOfDay >= 21 && timeOfDay < 24) {
            greetings = "Good Night";
        }
        return greetings;
    }

    public String getDate() {
        Calendar c = Calendar.getInstance();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("d MMM yyyy");
        return simpleDateFormat.format(c.getTime());
    }
}
