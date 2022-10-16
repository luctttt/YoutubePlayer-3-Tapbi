package vn.tapbi.youtubeplayer3.ui.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.ParcelFormatException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import timber.log.Timber;
import vn.tapbi.youtubeplayer3.R;

public class Convert {

    public static String convertTimeNew(String publishedAt, Context context) {
        String convertTime = "";
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault());        // "2021-06-21T06:30:02Z"
            @SuppressLint("SimpleDateFormat") SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");

            Date date = dateFormat.parse(publishedAt);
            Date now = new Date();


            assert date != null;
            long diff = now.getTime() - date.getTime();

            long diffYears = diff / (24 * 60 * 60 * 1000) / 365;
            if (diffYears > 0) {
                return diffYears + " years ago";
            } else {
                long diffDays = diff / (24 * 60 * 60 * 1000);
                if (diffDays == 0) {
                    long diffHours = diff / (60 * 60 * 1000) % 24;
                    if (diffHours == 0) {
                        long diffMinutes = diff / (60 * 1000) % 60;
                        if (diffMinutes == 0) {
                            long diffSeconds = diff / 1000 % 60;
                            if (diffSeconds == 0)
                                return context.getResources().getString(R.string.just_now);
                            else
                                return diffSeconds + " " + context.getResources().getString(R.string.seconds_ago);
                        } else
                            return diffMinutes + " " + context.getResources().getString(R.string.minutes_ago);
                    } else
                        return diffHours + " " + context.getResources().getString(R.string.hours_ago);
                } else
                    return (diffDays + " " + context.getResources().getString(R.string.days_ago));
            }

        } catch (ParcelFormatException | ParseException e) {
            Timber.d("Convert error : %s", e.toString());
        }

        return convertTime;
    }

    public static String convertDuration(String duration) {
        // PT2M20S - PT2H2M30S - PT5M - PT5H        PT2H9M20S   PT29M2S     PT1H5M : 1:05:00        : H - M - S -> (H)(M)(S) (HM)(HS)(MS) (HMS) : 'PT'HH'H'MM'M'SS'S'
        //Log.d("TAG", "convertDuration: "+duration);
        if (duration.contains("H")) {
            if (duration.contains("M")) {
                String hour = duration.substring(duration.indexOf("T") + 1, duration.indexOf("H"));
                String minute = duration.substring(duration.indexOf("H") + 1, duration.indexOf("M"));
                if (minute.length() == 1 ){
                    minute = "0"+minute;
                }

                if (duration.contains("S")){
                    String second = duration.substring(duration.indexOf("M") + 1, duration.indexOf("S"));
                    if (second.length()==1)     second = "0"+second;
                    return hour + ":" + minute + ":" + second;
                }

                return hour + ":" + minute + ":" + "00";
            } else {
                String hour = duration.substring(duration.indexOf("T") + 1, duration.indexOf("H"));
                return hour + ":00:00";
            }

        } else if (duration.contains("M")) {
            String hour = duration.substring(duration.indexOf("T") + 1, duration.indexOf("M"));
            if (duration.contains("S")) {
                String second = duration.substring(duration.indexOf("M") + 1, duration.indexOf("S"));
                if (second.length() == 2) {
                    return hour + ":" + second;
                } else return hour + ":0" + second;

            } else {
                return hour + ":00";
            }
        } else {
            if (duration.contains("D")){
                String hour = duration.substring(duration.indexOf("T")+1, duration.indexOf("D"));
                return hour + ":00:00";
            }
            return "00:" + duration.substring(duration.indexOf("T") + 1, duration.indexOf("S"));
        }

        //return "00:00";
    }

    public static String convertCount(int count) {
        if (count < 1000) {
            return count + "";
        } else if (count < 1000000) {
            double a = (count * 1.0) / 1000;
            return Math.round(a * 10.0) / 10.0 + "K";
        } else if (count < 1000000000) {
            double a = (count * 1.0) / 1000000;
            return Math.round(a * 10.0) / 10.0 + "M";
        } else {
            double a = (count * 1.0) / 1000000000;
            return Math.round(a * 10.0) / 10.0 + "T";
        }

    }

    public static String convertCountLong(long count) {
        if (count < 1000) {
            return count + "";
        } else if (count < 1000000) {
            double a = (count * 1.0) / 1000;
            return Math.round(a * 10.0) / 10.0 + "K";
        } else if (count < 1000000000) {
            double a = (count * 1.0) / 1000000;
            return Math.round(a * 10.0) / 10.0 + "M";
        } else {
            double a = (count * 1.0) / 1000000000;
            return Math.round(a * 10.0) / 10.0 + "T";
        }

    }

    public static int getTimeVideo(String timeConvert){
        // 4:16:20
        String []str = timeConvert.split(":");
        int sum = 0;
        int n = str.length;
        for(int i = 0;i<n;i++){
            int time = Integer.parseInt(str[i]);
            if (i==n-1) {
                sum +=time;
            }else{
                sum += time*60*(n-i-1);
            }
        }
        return sum;
    }


}
