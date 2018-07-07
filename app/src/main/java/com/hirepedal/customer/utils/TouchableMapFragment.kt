package com.hirepedal.customer.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.gms.maps.SupportMapFragment

class TouchableMapFragment : SupportMapFragment() {

    private var mOriginalContentView: View? = null
    private var mTouchView: TouchableWrapper? = null

    fun setTouchListener(onTouchListener: TouchableWrapper.OnTouchListener) {
        mTouchView!!.setTouchListener(onTouchListener)
    }

    override fun onCreateView(inflater: LayoutInflater, parent: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        try {
            mOriginalContentView = super.onCreateView(inflater, parent,
                    savedInstanceState)

            mTouchView = TouchableWrapper(activity!!)
            mTouchView!!.addView(mOriginalContentView)

            return mTouchView
        } catch (e: Exception) {
            e.printStackTrace()
            mOriginalContentView = super.onCreateView(inflater, parent,
                    savedInstanceState)

            mTouchView = TouchableWrapper(activity!!)
            mTouchView!!.addView(mOriginalContentView)

            return mTouchView
        }

    }

    override fun getView(): View? {
        return mOriginalContentView
    }

    companion object {
        fun newInstance(): TouchableMapFragment {
            val args = Bundle()
            val fragment = TouchableMapFragment()
            fragment.arguments = args
            return fragment
        }
    }

}