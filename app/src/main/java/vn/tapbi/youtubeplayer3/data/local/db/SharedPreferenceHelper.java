package vn.tapbi.youtubeplayer3.data.local.db;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;

import java.util.Locale;

import vn.tapbi.youtubeplayer3.common.Constant;

public class SharedPreferenceHelper {

    private static final String SELECTED_LANGUAGE = "Locale.Helper.Selected.Language";
    public static final String POSITION_HOME = "positionHome";
    public static final String POSITION_TRENDING = "positionTrending";
    //private static final String NOTIFICATION = "state_notification";

    /* notification */
    public static void saveStateNotification(Context context, boolean state) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.NOTIFICATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("state", state);
        editor.apply();
    }

    public static boolean getStateNotification(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.NOTIFICATION, Context.MODE_PRIVATE);
        return sharedPref.getBoolean("state", false);
    }

    /* play background */
    public static void saveStatePlayBackground(Context context, boolean state) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.PLAY_BACKGROUND, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putBoolean("play_background", state);
        editor.apply();
    }

    public static boolean getStateBackground(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.PLAY_BACKGROUND, Context.MODE_PRIVATE);
        return sharedPref.getBoolean("play_background", false);
    }

    /* language */
    public static void saveLanguage(Context context, String language) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.LANGUAGE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("state_language", language);
        editor.apply();
    }

    public static String getStateLanguage(Context context) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.LANGUAGE, Context.MODE_PRIVATE);
        return sharedPref.getString("state_language", "");
    }

    public static Context onAttach(Context context) {
        String lang = getPersistedData(context, Locale.getDefault().getLanguage());
        return setLocale(context, lang);
    }

    public static Context onAttach(Context context, String defaultLanguage) {
        String lang = getPersistedData(context, defaultLanguage);
        return setLocale(context, lang);
    }

    public static String getLanguage(Context context) {
        return getPersistedData(context, Locale.getDefault().getLanguage());
    }

    public static Context setLocale(Context context, String language) {
        persist(context, language);

//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            return updateResources(context, language);
//        } else
            return updateResourcesLegacy(context, language);
    }

    private static String getPersistedData(Context context, String defaultLanguage) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(SELECTED_LANGUAGE, defaultLanguage);
    }

    private static void persist(Context context, String language) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = preferences.edit();

        editor.putString(SELECTED_LANGUAGE, language);
        editor.apply();
    }

    @TargetApi(Build.VERSION_CODES.N)
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Configuration configuration = context.getResources().getConfiguration();
        configuration.setLocale(locale);
        configuration.setLayoutDirection(locale);

        return context.createConfigurationContext(configuration);
    }

    private static Context updateResourcesLegacy(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources resources = context.getResources();

        Configuration configuration = resources.getConfiguration();
        configuration.locale = locale;
        configuration.setLayoutDirection(locale);

        resources.updateConfiguration(configuration, resources.getDisplayMetrics());

        return context;
    }

    public static void savePosition(Context context , int position , String key){
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.NOTIFICATION, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt(key, position);
        editor.apply();
    }

    public static int getPosition(Context context , String key) {
        SharedPreferences sharedPref = context.getSharedPreferences(Constant.NOTIFICATION, Context.MODE_PRIVATE);
        return sharedPref.getInt(key, 0);
    }
}
