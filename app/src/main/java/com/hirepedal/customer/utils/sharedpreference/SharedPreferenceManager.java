package com.hirepedal.customer.utils.sharedpreference;

import android.content.Context;
import android.content.SharedPreferences;
import com.google.gson.Gson;
import com.hirepedal.customer.R;
import com.hirepedal.customer.cart.CartItem;
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

    public static String getFeaturePreference(Context context){
        return getSharedPreference(context).getString(context.getString(R.string.pref_feature),null);
    }

    public static void saveCartData(Context context, CartItem cartItem) {
        getSharedPreferenceEditor(context).putString(context.getString(R.string.cart), new Gson().toJson(cartItem)).commit();
    }

    public static String getCartData(Context context) {
        return getSharedPreference(context).getString(context.getString(R.string.cart), null);
    }












}
