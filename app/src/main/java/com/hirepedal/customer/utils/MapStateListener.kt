package com.hirepedal.customer.utils


import android.app.Activity
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import java.util.*

abstract class MapStateListener(private val mMap: GoogleMap, touchableMapFragment: TouchableMapFragment, private val mActivity: Activity) {

    private var mMapTouched = false
    private var mMapSettled = false
    private var mTimer: Timer? = null
    private var mLastPosition: CameraPosition? = null

    val centerPosition: LatLng
        get() = this@MapStateListener.mMap.cameraPosition.target

    init {

        mMap.setOnCameraChangeListener {
            unsettleMap()
            if (!mMapTouched) {
                runSettleTimer()
            }
        }

        touchableMapFragment.setTouchListener(object : TouchableWrapper.OnTouchListener {
            override fun onTouch() {
                touchMap()
                unsettleMap()
            }

            override fun onRelease() {
                releaseMap()
                runSettleTimer()
            }
        })
    }

    private fun updateLastPosition() {
        mActivity.runOnUiThread { mLastPosition = this@MapStateListener.mMap.cameraPosition }
    }

    private fun runSettleTimer() {
        updateLastPosition()

        if (mTimer != null) {
            mTimer!!.cancel()
            mTimer!!.purge()
        }
        mTimer = Timer()
        mTimer!!.schedule(object : TimerTask() {
            override fun run() {
                mActivity.runOnUiThread {
                    val currentPosition = this@MapStateListener.mMap.cameraPosition
                    if (currentPosition == mLastPosition) {
                        settleMap()
                    }
                }
            }
        }, SETTLE_TIME.toLong())
    }

    @Synchronized private fun releaseMap() {
        if (mMapTouched) {
            mMapTouched = false
            onMapReleased()
        }
    }

    private fun touchMap() {
        if (!mMapTouched) {
            if (mTimer != null) {
                mTimer!!.cancel()
                mTimer!!.purge()
            }
            mMapTouched = true
            onMapTouched()
        }
    }

    fun unsettleMap() {
        if (mMapSettled) {
            if (mTimer != null) {
                mTimer!!.cancel()
                mTimer!!.purge()
            }
            mMapSettled = false
            mLastPosition = null
            onMapUnsettled()
        }
    }

    fun settleMap() {
        if (!mMapSettled) {
            mMapSettled = true
            onMapSettled()
        }
    }

    abstract fun onMapTouched()

    abstract fun onMapReleased()

    abstract fun onMapUnsettled()

    abstract fun onMapSettled()

    companion object {
        private val SETTLE_TIME = 1000
    }
}
