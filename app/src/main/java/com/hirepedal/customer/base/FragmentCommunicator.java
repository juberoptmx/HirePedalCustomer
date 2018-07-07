package com.hirepedal.customer.base;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.view.View;

public interface FragmentCommunicator {

    void showFragment(BaseFragment fragment);

    void showBackStack();

    void startProgress(String message);

    void startProgress(String message, boolean withProgressBar);

    void updateProgessValue(int value);

    void completeProgress();

    void showAlert(@Nullable String title, String message
            , @Nullable String positiveButtonName, @Nullable DialogInterface.OnClickListener positiveButtonClickListener
            , @Nullable String negativeButtonName, @Nullable DialogInterface.OnClickListener negativeButtonClickListener
            , @Nullable String neutralButtonName, @Nullable DialogInterface.OnClickListener neutralButtonClickListener);

    void showException(Exception e);

    void showKeyBoard(View view, int flag);

    void hideKeyBoard();

    void noTitle(Boolean flag);

    void backTitleButtonEnable(Boolean flag);

    void backTitleButtonEnable(Boolean flag,Boolean homeButton);

    void setActionTitleBar(boolean noActionBar,boolean backButtonVisible, int title);

    void setActionTitleBar(boolean noActionBar, boolean backButtonVisible, CharSequence title);

    void clearBackStackTraceAndAddNew(BaseFragment fragment);

    void clearBackStackTrace();

    void showHamburger(boolean showHamburger);

    void showHamburger(boolean showHamburger,String fragmentTag);

    void showHamburger(boolean showHamburger,boolean homeButton);

    void simpleAlert(String alertMessage);

    void customSimpleAlert(String alertMessage,String negativeButtonName,String positiveButtonName);

    void decreaseNotificationCount(int decrement);

    void clearNotificationCount();

    int getNotificationCount();

    boolean isConnectionAvailable();

    void backstackFragment();


    void updateHamburger();

    boolean isGpsEnabled();

    void showDatePickerDialog(DatePickerDialog.OnDateSetListener listener);

    void showTimePickerDialog(TimePickerDialog.OnTimeSetListener listener);

    void showEditText(boolean isShow);

    String getTodoTitle();

    void clearTodoTitle();

    void setTodoTitle(String title);

    String getPasswordTitle();

    void clearPasswordTitle();

    void setPasswordTitle(String title);

}
