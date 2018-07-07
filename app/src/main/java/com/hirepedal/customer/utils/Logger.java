package com.hirepedal.customer.utils;

import android.util.Log;

import com.hirepedal.customer.BuildConfig;



public final class Logger {

    public static boolean getIsEnabled(){
        return BuildConfig.DEBUG;
    }

    /**
     * Verbose Logger
     *
     * @param tag
     * @param message
     */
    public static final void v(String tag, String message) {
        if (getIsEnabled())
            Log.v(tag, "(" + getLineNumber() + ") " + message);
    }


    /**
     * Debug Logger
     *
     * @param tag
     * @param message
     */
    public static final void d(String tag, String message) {
        if (getIsEnabled())
            Log.d(tag, "(" + getLineNumber() + ") " + message);
    }

    /**
     * Information Logger
     *
     * @param tag
     * @param message
     */
    public static final void i(String tag, Object message) {
        if (getIsEnabled())
            Log.i(tag, "(" + getLineNumber() + ") " + String.valueOf(message));
    }


    /**
     * Warn Logger
     *
     * @param tag
     * @param message
     */
    public static final void w(String tag, String message) {
        if (getIsEnabled())
            Log.w(tag, "(" + getLineNumber() + ") " + message);
    }

    /**
     * Error Logger
     *
     * @param tag
     * @param message
     */
    public static final void e(String tag, String message) {
        if (getIsEnabled())
            Log.e(tag, "(" + getLineNumber() + ") " + message);
    }

    /**
     * Error Logger
     *
     * @param tag
     * @param message
     */
    public static final void e(String tag, String message,Exception e) {
        if (Logger.getIsEnabled())
            Log.e(tag, "(" + Logger.getLineNumber() + ") " + message,e);
    }

    /**
     * Get the logging line number.
     *
     * @return int - logging line number of calling.
     */
    public static String getLineNumber() {
        return Thread.currentThread().getStackTrace()[4].getLineNumber()+" - "+Thread.currentThread().getStackTrace()[4].getClassName();
    }

}
