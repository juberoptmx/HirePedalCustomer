package com.hirepedal.customer.utils

import android.content.Context
import android.view.MotionEvent
import android.widget.FrameLayout


class TouchableWrapper(context: Context) : FrameLayout(context) {

    private var onTouchListener: OnTouchListener? = null

    fun setTouchListener(onTouchListener: OnTouchListener) {
        this.onTouchListener = onTouchListener
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        //  TODO: 21-01-2016 fix belo error (Turn off permission book a service and switch to map and touvh the screen)
        //        01-21 01:16:02.732 29892-29892/demo.sample.com.sample E/AndroidRuntime: FATAL EXCEPTION: main
        //        Process: demo.sample.com.sample, PID: 29892
        //        java.lang.NullPointerException: Attempt to invoke interface method 'void TouchableWrapper$OnTouchListener.onTouch()' on a null object reference
        //        at TouchableWrapper.dispatchTouchEvent(TouchableWrapper.java:24)
        try {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> onTouchListener!!.onTouch()
                MotionEvent.ACTION_UP -> onTouchListener!!.onRelease()
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }


        return super.dispatchTouchEvent(event)
    }

    interface OnTouchListener {
        fun onTouch()

        fun onRelease()
    }
}