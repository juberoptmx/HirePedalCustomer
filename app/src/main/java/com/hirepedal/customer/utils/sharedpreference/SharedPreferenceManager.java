package com.hirepedal.customer.utils.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.hirepedal.customer.R;
import com.hirepedal.customer.models.User;
import java.util.ArrayList;
import java.util.HashMap;



public class SharedPreferenceManager {

    public static final String TAG = SharedPreferenceManager.class.getSimpleName();

    public static SharedPreferences getSharedPreference(Context context) {
        return context.getSharedPreferences(context.getString(R.string.shared_preference_name), Context.MODE_PRIVATE);
    }

    public static SharedPreferences.Editor getSharedPreferenceEditor(Context context) {
        SharedPreferences sharedPreferences = getSharedPreference(context);
        return sharedPreferences.edit();
    }

    public static void saveUser(Context context, User user) {
        getSharedPreferenceEditor(context).putString(context.getString(R.string.user), new Gson().toJson(user)).commit();
    }

    public static String  getUser(Context context) {
        return getSharedPreference(context).getString(context.getString(R.string.user), null);
    }

    public static void saveFeaturePreference(Context context,String feature){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_feature),feature).commit();
    }

    public static String getFeaturePreference(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_feature),null);
    }

    public static void saveMyApps(Context context, ArrayList<String> myApps){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_my_apps),new Gson().toJson(myApps)).commit();
    }

    public static String getMyApps(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_my_apps),null);
    }

    public static void saveMyFitnessApps(Context context, ArrayList<String> myFitness){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_my_fitness),new Gson().toJson(myFitness)).commit();
    }

    public static String getMyFitnessApps(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_my_fitness),null);
    }

    public static void saveAtHomeApps(Context context, ArrayList<String> atHome){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_at_home),new Gson().toJson(atHome)).commit();
    }

    public static String getAtHomeApps(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_at_home),null);
    }

    public static void saveAtOfficeApps(Context context, ArrayList<String> atOffice){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_at_office),new Gson().toJson(atOffice)).commit();
    }

    public static String getAtOfficeApps(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_at_office),null);
    }

    public static void saveMyPaymentApps(Context context, ArrayList<String> myPayment){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_my_payment),new Gson().toJson(myPayment)).commit();
    }

    public static String getMyPaymentApps(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_my_payment),null);
    }

    public static String getAboutMeData(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_about_me_data),null);
    }

    public static void saveAboutMeData(Context context, HashMap<String, String> aboutMeInfo){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_about_me_data),new Gson().toJson(aboutMeInfo)).commit();
    }

    // Save & retrieve methods for My account
    public static String getMyAccountData(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_my_account_data),null);
    }

    public static void saveMyAccountData(Context context, HashMap<String, String> myAccountInfo){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_my_account_data),new Gson().toJson(myAccountInfo)).commit();
    }

    public static void removeMyAccountInfo(Context context) {
        getSharedPreferenceEditor(context).remove(context.getString(R.string.pref_my_account_data)).commit();
    }

    public static String getMyLocations(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_my_location),null);
    }



    public static String getMyTickets(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_my_tickets),null);
    }

    public static void saveMyRecordings(Context context, ArrayList<String> files){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_my_recordings),new Gson().toJson(files)).commit();
    }

    public static String getMyRecordings(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_my_recordings),null);
    }



    public static String getToDoList(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_todo_list),null);
    }

    public static void savePasswordLocker(Context context, String password){
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_password_locker),password).commit();
    }

    public static String getPasswordLocker(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_password_locker),null);
    }


    // Save & retrieve methods for Thinking Of you
    // Save & retrieve methods for Thinking Of you, callSomeone and TextSomeone
    public static String getContactsDataFor(int moduleName, Context context) {
        return getSharedPreference(context).getString(context.getString(moduleName),null);
    }

    public static void saveContactsDataFor(int moduleName, Context context, HashMap<String, String> myAccountInfo) {
        getSharedPreferenceEditor(context).putString(context.getString(moduleName),new Gson().toJson(myAccountInfo)).commit();
    }



    public static String getPasswordList(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_password_list),null);
    }

    // Save & retrieve methods for Find Me
    public static String getFindMeData(Context context) {
        return getSharedPreference(context).getString(context.getString(R.string.find_me_data),null);
    }

    public static void saveFindMeData(Context context, HashMap<String, ArrayList<HashMap<String, String>>> myAccountInfo) {
        getSharedPreferenceEditor(context).putString(context.getString(R.string.find_me_data),new Gson().toJson(myAccountInfo)).commit();
    }

    public static String getHushHushSelectionPreference(Context context) {
        return getSharedPreference(context).getString(context.getString(R.string.pref_hush_hush),null);
    }

    public static void saveHushHushSelectionPreference(Context context, String hushHushPref) {
        getSharedPreferenceEditor(context).putString(context.getString(R.string.pref_hush_hush), hushHushPref).commit();
    }

    public static void removeHushHushSelectionPreference(Context context) {
        getSharedPreferenceEditor(context).remove(context.getString(R.string.pref_hush_hush)).commit();
    }
}
