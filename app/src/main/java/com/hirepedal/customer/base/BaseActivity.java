package com.hirepedal.customer.base;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import com.hirepedal.customer.R;
import com.hirepedal.customer.utils.Logger;
import java.util.Calendar;
import java.util.Date;


public abstract class BaseActivity extends AppCompatActivity implements FragmentManager.OnBackStackChangedListener, com.hirepedal.customer.base.FragmentCommunicator {

    private static String TAG = BaseActivity.class.getSimpleName();

    public FragmentManager fragmentManager = null;

    private ProgressDialog progressDialog;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
    }

    /**
     * invoke FragmentManager and load the fragment in the activity.
     *
     * @param fragment which is about to show
     */
    @Override
    public void showFragment(com.hirepedal.customer.base.BaseFragment fragment) {
        try {
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction();
            /*fragmentTransaction.setCustomAnimations(android.R.anim.slide_in_left,android.R.anim.slide_out_right
                    ,android.R.anim.slide_in_left,android.R.anim.slide_out_right);*/
            fragmentManager.addOnBackStackChangedListener(this);
            fragmentTransaction.replace(R.id.mainContainer, fragment, fragment.getSimpleTag());
            fragmentTransaction.addToBackStack(fragment.getSimpleTag());
            fragmentTransaction.commit();
        } catch (IllegalStateException | NullPointerException e) {
            Logger.e(TAG, e.getMessage());
        }
        hideKeyBoard();

    }

    @Override
    public void onBackStackChanged() {

    }

    @Override
    public void showBackStack() {

    }

    @Override
    public void onBackPressed() {
        if (backPressed()) {
            super.onBackPressed();
            if (fragmentManager != null && fragmentManager.getBackStackEntryCount() == 0) {
                finish();
            }
        }
    }

    private boolean backPressed() {
        for (int entry = 0; entry < fragmentManager.getBackStackEntryCount(); entry++) {
            String name = fragmentManager.getBackStackEntryAt(entry).getName();
            BaseFragment fragment = (BaseFragment) getSupportFragmentManager().findFragmentByTag(name);
            if (fragment != null && fragment.isVisible()) {
                Logger.i(TAG, "Visible fragment: " + name);
                return fragment.backButtonPressed();
            }
        }
        return false;
    }

    @Override
    public void startProgress(String message) {
        if (progressDialog == null) {
            try {
                progressDialog = new ProgressDialog(this);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.setIndeterminate(false);
                if (message != null)
                    progressDialog.setMessage(message);
                progressDialog.setCancelable(false);
//                progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_handler));
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (message != null)
                    progressDialog.setMessage(message);
                progressDialog.setCancelable(false);
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void startProgress(String message, boolean withProgressBar) {
        if (progressDialog == null) {
            try {
                progressDialog = new ProgressDialog(this, ProgressDialog.THEME_HOLO_DARK);
                progressDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                progressDialog.setIndeterminate(false);
                progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                if (message != null)
                    progressDialog.setMessage(message);
                progressDialog.setCancelable(false);
//                progressDialog.setIndeterminateDrawable(getResources().getDrawable(R.drawable.progressbar_handler));
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                if (message != null)
                    progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                progressDialog.setMessage(message);
                progressDialog.setCancelable(false);
                progressDialog.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void updateProgessValue(int value) {
        if (progressDialog != null) {
            progressDialog.setProgress(value);
        }
    }

    @Override
    public void completeProgress() {
        try {
            if (progressDialog != null) {
                progressDialog.dismiss();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showAlert(@Nullable String title, String message
            , @Nullable String positiveButtonName, @Nullable DialogInterface.OnClickListener positiveButtonClickListener
            , @Nullable String negativeButtonName, @Nullable DialogInterface.OnClickListener negativeButtonClickListener
            , @Nullable String neutralButtonName, @Nullable DialogInterface.OnClickListener neutralButtonClickListener) {
        //Create builder for dialog.
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //default
        final String DEFAUT_POSITIVE_BUTTON_NAME = "OK";
        final String DEFAUT_NEGATIVE_BUTTON_NAME = "CANCEL";

        //Add title if exists
        if (title != null && !title.isEmpty()) {
            builder.setTitle(title);
        }

        //Add message if exists other wise don't proceed.
        if (message != null && !message.isEmpty()) {
            builder.setMessage(message);
        } else {
            Logger.e("Alert Dialog not created", "Message for alert dialog is null or empty.");
            return;
        }

        //Add positive button if not null string.
        if (positiveButtonName != null && !positiveButtonName.isEmpty()) {
            if (positiveButtonClickListener != null) {
                builder.setPositiveButton(positiveButtonName, positiveButtonClickListener);
            } else {
                builder.setPositiveButton(positiveButtonName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing
                    }
                });
            }
        }else {//adding default positive button.
            if (positiveButtonClickListener != null) {
                builder.setPositiveButton(DEFAUT_POSITIVE_BUTTON_NAME, positiveButtonClickListener);
            } else {
                builder.setPositiveButton(DEFAUT_POSITIVE_BUTTON_NAME, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing
                    }
                });
            }
        }

        //Add negative button if not null string.
        if (negativeButtonName != null && !negativeButtonName.isEmpty()){
            if (negativeButtonClickListener != null) {
                builder.setNegativeButton(negativeButtonName, negativeButtonClickListener);
            } else {
                builder.setNegativeButton(negativeButtonName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing
                    }
                });
            }
        }

        //Add negative button if not null string.
        if (neutralButtonName != null && !neutralButtonName.isEmpty()){
            if (neutralButtonClickListener != null) {
                builder.setNeutralButton(neutralButtonName, neutralButtonClickListener);
            } else {
                builder.setNeutralButton(neutralButtonName, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //Do nothing
                    }
                });
            }
        }

        // Create the AlertDialog object and return it
        AlertDialog dialog = builder.create();
        dialog.show();
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(getResources().getColor(R.color.color_primary_orange));
        dialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(getResources().getColor(R.color.color_primary_orange));
        dialog.getButton(AlertDialog.BUTTON_NEUTRAL).setTextColor(getResources().getColor(R.color.color_primary_orange));
    }

    @Override
    public void showException(Exception e) {

    }

    @Override
    public void showKeyBoard(View view, int flag) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getWindow() != null && getWindow().getCurrentFocus() != null) {
            imm.showSoftInput(view, flag);


        }
    }

    @Override
    public void hideKeyBoard() {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (getWindow() != null && getWindow().getCurrentFocus() != null) {
            imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
                    .getWindowToken(), 0);
        }
    }

    @Override
    public void noTitle(Boolean flag) {
        if (flag) {
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
        } else {
            if (getSupportActionBar() != null)
                getSupportActionBar().show();
        }
    }

    @Override
    public void backTitleButtonEnable(Boolean flag) {
        if (flag) {
            if (getSupportActionBar() != null) {
//                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_custom_back_button);
                getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
            }
        }
    }

    @Override
    public void clearBackStackTraceAndAddNew(com.hirepedal.customer.base.BaseFragment fragment) {
        clearBackStackTrace();
        showFragment(fragment);
    }

    @Override
    public void clearBackStackTrace() {
        try {
            if (fragmentManager != null) {
                for (int i = fragmentManager.getBackStackEntryCount(); i > 0; i--) {
                    fragmentManager.popBackStackImmediate();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void showHamburger(boolean showHamburger) {

    }

    @Override
    public void simpleAlert(String alertMessage) {

    }

    @Override
    public void customSimpleAlert(String alertMessage, String negativeButtonName, String positiveButtonName) {

    }

    @Override
    public void decreaseNotificationCount(int decrement) {

    }

    @Override
    public void clearNotificationCount() {

    }

    @Override
    public int getNotificationCount() {
        return 0;
    }

    @Override
    public boolean isConnectionAvailable() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    @Override
    public void backstackFragment() {
        //try-catch block to avoid NPE popFromBackStack()
        try {
            if (fragmentManager != null) {
                fragmentManager.popBackStack();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isGpsEnabled(){
        try{
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
            if(!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
                buildAlertMessageNoGps();
            }else {
                return true;
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return false;
    }

    private void buildAlertMessageNoGps() {
        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(@SuppressWarnings("unused") final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        startActivity(new Intent(android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(final DialogInterface dialog, @SuppressWarnings("unused") final int id) {
                        dialog.cancel();
                    }
                });
        final AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void backTitleButtonEnable(Boolean flag, Boolean homeButton) {
        if (flag) {
            if (getSupportActionBar() != null) {
//                getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_custom_back_button);
                getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
                if (homeButton) {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_hamburger);
                } else {
                    getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
                }
            }
        } else {
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(flag);
            }
        }
    }

    @Override
    public void showDatePickerDialog(DatePickerDialog.OnDateSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,R.style.DatePicker,listener,year,month,day);
        datePickerDialog.getDatePicker().setMinDate(new Date().getTime());
        datePickerDialog.show();
    }


    @Override
    public void showTimePickerDialog(TimePickerDialog.OnTimeSetListener listener) {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,R.style.DatePicker,listener,hour,minute,false);
        timePickerDialog.show();
    }
}
