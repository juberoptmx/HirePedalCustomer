package com.hirepedal.customer.base;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatDialogFragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.hirepedal.customer.R;
import com.hirepedal.customer.utils.Logger;


public abstract class BaseFragment extends AppCompatDialogFragment implements
        View.OnClickListener {

    private static String SimpleTAG = BaseFragment.class.getSimpleName();
    public String TAG = this.getClass().getSimpleName();

    //  todo BF private RequestManager mVolleyRequestManager = null;
    private static boolean isConnectionAvailable;
    private static int mContainerId = 0;
    private static String mPreviousPageName = null;
    private final Handler mHandler = new Handler(Looper.getMainLooper());
    //  todo BF private NetworkReceiver mNetworkutility = null;
    private FragmentManager fragmentManager = null;
    private Thread mUiThread = Looper.getMainLooper().getThread();
    private ImageView mBackToHome = null;
    private ImageView mHomeIcon = null;
    private String mBaseFragmentID = "basefragment";
    private String mPreviousFragmentName;
    private LoadingListner mLoadingListner;

    protected Activity fastTrackActivity;

    private com.hirepedal.customer.base.FragmentCommunicator fragmentCommunicator;

    private View view;


    interface LoadingListner {
        void showLoadingScreen(boolean b);
    }

    public synchronized static void setStatus(boolean connection) {
        if (connection)
            isConnectionAvailable = true;
        else {
            isConnectionAvailable = false;
        }
    }

    public void bindViews(View view){

    };

    /**
     * attaches your listeners to the views must get called after {@link #bindViews(View)}
     */
    public void attachListeners(){


    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        com.hirepedal.customer.utils.Logger.i(SimpleTAG, "OnCreate on "
                + this.getClass().getSimpleName());
        super.onCreate(savedInstanceState);
        SimpleTAG = this.getClass().getSimpleName();
        registerNetWorkReceiver();
    }

    private void registerNetWorkReceiver() {
        IntentFilter mfilter = new IntentFilter(
                "android.net.conn.CONNECTIVITY_CHANGE");
        //Todo BF below
        /*mNetworkutility = new NetworkReceiver(this);
        getActivity().registerReceiver(mNetworkutility, mfilter);*/
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        com.hirepedal.customer.utils.Logger.i(SimpleTAG, "OnCreateView on "
                + this.getClass().getSimpleName());
        //Todo BF below
        /*mVolleyRequestManager = new RequestManager();
        mVolleyRequestManager.init(getContext().getApplicationContext());*/
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onStart() {
        com.hirepedal.customer.utils.Logger.i(SimpleTAG, "OnStart on "
                + this.getClass().getSimpleName());
        super.onStart();
    }

    @Override
    public void onResume() {
        com.hirepedal.customer.utils.Logger.i(SimpleTAG, "OnResume on "
                + this.getClass().getSimpleName());
        setPreviousFragmentName();
        super.onResume();
        showBackStack();
    }

    protected void showBackStack() {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.showBackStack();
        }
    }

    public String getSimpleTag() {
        return TAG;
    }

    @Override
    public void onPause() {
        com.hirepedal.customer.utils.Logger.i(SimpleTAG, "OnPause on "
                + this.getClass().getSimpleName());
        super.onPause();
    }

    @Override
    public void onStop() {
        com.hirepedal.customer.utils.Logger.i(SimpleTAG, "OnStop on "
                + this.getClass().getSimpleName());
        super.onStop();
    }

    @Override
    public void onDestroy() {
        com.hirepedal.customer.utils.Logger.i(SimpleTAG, "onDestroy on "
                + this.getClass().getSimpleName());
//    todo BF    getActivity().unregisterReceiver(mNetworkutility);
        super.onDestroy();
    }

    @Override
    public void onDestroyView() {
        com.hirepedal.customer.utils.Logger.i(SimpleTAG, "OnDestroyView on "
                + this.getClass().getSimpleName());
        super.onDestroyView();
        hideKeyboard();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            if (context instanceof FragmentCommunicator) {
                fragmentCommunicator = (FragmentCommunicator) context;
            } else {
                throw new IllegalArgumentException(context.getClass().getSimpleName()
                        + "Parent activity must implement FragmentCommunicator interface.");
            }
            if (context instanceof Activity) {
                fastTrackActivity = (Activity) context;
            } else {
                throw new IllegalArgumentException(context.getClass().getSimpleName()
                        + "Parent activity must OGTActivity.");
            }
        }
    }

    public void showLoading(boolean bool) {
        if (mLoadingListner != null) {
            com.hirepedal.customer.utils.Logger.e("ContextName", mLoadingListner.getClass().getSimpleName());
            mLoadingListner.showLoadingScreen(bool);
        } else {
            com.hirepedal.customer.utils.Logger.i("BaseFragment", "Loading screen skipped");
        }
    }


    protected void setPreviousFragmentName() {
        //Todo BF complete function
        /*if (getActivity() instanceof BookMyTimeActivity) {
            mPreviousFragmentName = ((BookMyTimeActivity) getActivity()).getPreviousFragmentName();
//            Logger.e("Previous Fragment Name", mPreviousFragmentName);
        }*/
    }

    protected String getPreviousFragmentName() {
        return mPreviousFragmentName;
    }

    public static void showErrorInLog(String error) {
        com.hirepedal.customer.utils.Logger.e(SimpleTAG, "  *    *  ");
        com.hirepedal.customer.utils.Logger.e(SimpleTAG, " ***\t*** ");
        com.hirepedal.customer.utils.Logger.e(SimpleTAG, "**********");
        com.hirepedal.customer.utils.Logger.e(SimpleTAG, error);
        com.hirepedal.customer.utils.Logger.e(SimpleTAG, "**********");
        com.hirepedal.customer.utils.Logger.e(SimpleTAG, " ***\t*** ");
        com.hirepedal.customer.utils.Logger.e(SimpleTAG, "  *    *  ");
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Logger.i(SimpleTAG, SimpleTAG + " : onConfigurationChanged ");
        getAppName();
    }

    protected String getAppName() {
        String appName = getActivity().getString(R.string.app_name);
        try {
            ApplicationInfo appInfo = getActivity().getPackageManager().getApplicationInfo(getActivity().getPackageName(),
                    getActivity().getPackageManager().GET_META_DATA);
            appName = appInfo.loadLabel(getActivity().getPackageManager()).toString();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return appName;
    }

    protected void showFragment(BaseFragment fragment) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.showFragment(fragment);
        }
    }

    public String getTAG() {
        return TAG;
    }

    public int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        int px = Math.round(dp
                * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }

    public int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources()
                .getDisplayMetrics();
        int dp = Math.round(px
                / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public String getText(View v) {
        String data = "";
        try {
            if (v instanceof EditText) {
                data = ((EditText) v).getText().toString().trim();
            } else if (v instanceof TextView) {
                data = ((TextView) v).getText().toString().trim();
            } else {
                com.hirepedal.customer.utils.Logger.i("getText Error", "Cannot Extract text from the view " + String.valueOf(v));
            }
        } catch (Exception e) {
            com.hirepedal.customer.utils.Logger.i("getText Error", "Cannot Extract text from the view " + String.valueOf(v + " " + e));
        }
        return data;
    }

    protected void backstackFragment() {
        if(fragmentCommunicator!=null){
            fragmentCommunicator.backstackFragment();
        }
    }

    protected void hideKeyboard() {
        View view = getActivity().getCurrentFocus();
        if (view != null) {
            InputMethodManager inputManager = (InputMethodManager) getActivity()
                    .getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }


    //Todo BF complete function
    /*@Override
    public void onNetworkStateChanged(boolean connection) {
        setStatus(connection);
    }*/

    protected final void updateUI(Runnable runnable) {
        if (Thread.currentThread() != mUiThread) {
            mHandler.post(runnable);
        } else {
            runnable.run();
        }
    }

    protected boolean backButtonPressed() {
        return true;
    }

    protected void finishActivity() {
        if (getActivity() != null)
            getActivity().finish();
    }

    protected String getPreviousName() {
        return mPreviousPageName;
    }


    public void OpenActivity(Activity activity, String classname, boolean thisfinish) {
        Logger.i("Opening ", classname);
        try {
            Class classfile = Class.forName(classname);
            Intent intent = new Intent(getActivity(), classfile);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            if (thisfinish) {
                activity.finish();
            }
        } catch (ClassNotFoundException | NullPointerException e) {
            e.printStackTrace();
        }
    }

    public void showValidationError(String message) {
        showAlert("Alert", message, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing.
            }
        },null,null,null,null);
    }

    private void errorDialog(int lineNumber, String message) {
        //Todo when releasing, just enableDialog variable it false for not using this feature.
        boolean enableDialog = com.hirepedal.customer.utils.Logger.getIsEnabled();
        if (enableDialog) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("*****Error Occurred*****")
                    .setMessage(this.TAG + "(" + lineNumber + ")"
                            + ": " + message)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            //doNothing
                        }
                    })
                    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // do nothing
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }

    protected void startProgress(String message) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.startProgress(message);
        }
    }

    protected void startProgress(String message, boolean withProgressBar) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.startProgress(message, withProgressBar);
        }
    }

    protected void updateProgessValue(int value) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.updateProgessValue(value);
        }
    }

    protected void completeProgress() {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.completeProgress();
        }
    }

    protected void showAlert(@Nullable String title, String message
            , @Nullable String positiveButtonName, @Nullable DialogInterface.OnClickListener positiveButtonClickListener
            , @Nullable String negativeButtonName, @Nullable DialogInterface.OnClickListener negativeButtonClickListener
            , @Nullable String neutralButtonName, @Nullable DialogInterface.OnClickListener neutralButtonClickListener) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.showAlert(title,
                    message,
                    positiveButtonName,
                    positiveButtonClickListener,
                    negativeButtonName,
                    negativeButtonClickListener,
                    neutralButtonName,
                    neutralButtonClickListener);
        }
    }

    protected void showKeyBoard() {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.showKeyBoard(view, 0);
        }
    }

    protected void hideKeyBoard() {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.hideKeyBoard();
        }
    }

    protected void logResponse(String responseMessage) {
        if (responseMessage != null) {
            Logger.d(TAG, "Response: " + responseMessage);
        }
    }

    protected void noTitle(Boolean flag) {
        if (fragmentCommunicator != null)
            fragmentCommunicator.noTitle(flag);
    }

    protected void backTitleButtonEnable(Boolean flag) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.backTitleButtonEnable(flag);
        }
    }

    protected final void setActionbarTitle(boolean noActionBar, boolean backButtonVisible, int title) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.setActionTitleBar(noActionBar, backButtonVisible, title);
            if (title == R.string.hirePedal) {
                fragmentCommunicator.backTitleButtonEnable(true, true);
            } else {
                fragmentCommunicator.backTitleButtonEnable(true, false);
            }
        }
    }

    protected final void setActionbarTitle(boolean noActionBar, boolean backButtonVisible, CharSequence title){
        if (this.fragmentCommunicator != null) {
            this.fragmentCommunicator.setActionTitleBar(noActionBar, backButtonVisible, title);
        }
    }

    protected void clearBackStackTraceAndAddNew(BaseFragment fragment) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.clearBackStackTraceAndAddNew(fragment);
        }
    }

    protected void clearBackStackTrace() {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.clearBackStackTrace();
        }
    }

    protected void updateHamburger(){
        if(fragmentCommunicator!=null){
            fragmentCommunicator.updateHamburger();
        }
    }

    protected void showHamburger(boolean showHamburger) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.showHamburger(showHamburger);
        }
    }

    protected void fullScreen(boolean flag) {
        if (flag) {
            if (fragmentCommunicator != null) {
                fragmentCommunicator.noTitle(flag);
            }
        } else {
            if (fragmentCommunicator != null) {
                fragmentCommunicator.noTitle(flag);
            }
        }
    }

    protected com.hirepedal.customer.base.FragmentCommunicator getFragmentCommunicator() {
        return fragmentCommunicator;
    }

    protected void simpleAlert(String alertMessage) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.simpleAlert(alertMessage);
        }
    }

    protected void customSimpleAlert(String alertMessage, String negativeButtonName, String positiveButtonName) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.customSimpleAlert(alertMessage, negativeButtonName, positiveButtonName);
        }
    }

    protected void decreaseNotificationCount(int decrement) {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.decreaseNotificationCount(decrement);
        }
    }

    protected void clearNotificationCount() {
        if (fragmentCommunicator != null) {
            fragmentCommunicator.clearNotificationCount();
        }
    }

    protected int getNotificationCount() {
        if (fragmentCommunicator != null) {
            return fragmentCommunicator.getNotificationCount();
        }
        return 0;
    }

    protected boolean isConnectionAvailable() {
        if (fragmentCommunicator != null) {
            return fragmentCommunicator.isConnectionAvailable();
        }
        return false;
    }

    protected boolean isGpsEnabled(){
        if(fragmentCommunicator!=null){
            return fragmentCommunicator.isGpsEnabled();
        }
        return false;
    }

    protected void showDatePicker(DatePickerDialog.OnDateSetListener listener){
        if(fragmentCommunicator!=null){
            fragmentCommunicator.showDatePickerDialog(listener);
        }
    }

    protected void showTimePicker(TimePickerDialog.OnTimeSetListener listener){
        if(fragmentCommunicator!=null){
            fragmentCommunicator.showTimePickerDialog(listener);
        }
    }

    protected void showEditText(boolean isShown){
        if(fragmentCommunicator!=null){
            fragmentCommunicator.showEditText(isShown);
        }
    }

    protected String getTodoTitle(){
        if(fragmentCommunicator!=null){
            return fragmentCommunicator.getTodoTitle();
        }
        return "";
    }

    protected void clearTodoTitle(){
        if(fragmentCommunicator!=null){
            fragmentCommunicator.clearTodoTitle();
        }
    }

    protected void setTodoTitle(String title){
        if(fragmentCommunicator!=null){
            fragmentCommunicator.setTodoTitle(title);
        }
    }

    protected String getPasswordTitle(){
        if(fragmentCommunicator!=null){
            return fragmentCommunicator.getPasswordTitle();
        }
        return "";
    }

    protected void clearPasswordTitle(){
        if(fragmentCommunicator!=null){
            fragmentCommunicator.clearPasswordTitle();
        }
    }

    protected void setPasswordTitle(String title){
        if(fragmentCommunicator!=null){
            fragmentCommunicator.setPasswordTitle(title);
        }
    }
}
