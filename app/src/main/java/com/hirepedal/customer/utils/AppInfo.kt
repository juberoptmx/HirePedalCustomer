package com.hirepedal.customer.utils

import android.graphics.drawable.Drawable
import android.util.Log


internal class AppInfo {

    var appname = ""
    var pname = ""
    var versionName = ""
    var versionCode = 0
    var icon: Drawable? = null
    fun prettyPrint() {
        Log.d(appname,  "\t" + pname + "\t" + versionName + "\t" + versionCode + "\t" + icon)
    }
}