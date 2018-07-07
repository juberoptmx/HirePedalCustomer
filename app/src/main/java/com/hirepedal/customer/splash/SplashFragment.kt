package com.hirepedal.customer.splash

import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirepedal.customer.R
import com.hirepedal.customer.activities.RootActivity
import com.hirepedal.customer.base.BaseFragment
import com.hirepedal.customer.dashboard.DashboardFragment
import com.hirepedal.customer.signup.SignUpFragment
import com.hirepedal.customer.utils.sharedpreference.SharedPreferenceManager


class SplashFragment : BaseFragment() {

    private var intent: Intent? = null

    private lateinit var mp: MediaPlayer

    fun setIntent(intent: Intent) {
        this.intent = intent
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        playCycleBell()
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_splash, container, false)
        return view
    }

    override fun onResume() {
        noTitle(true)
        super.onResume()
        checkStatus()
        //setActionbarTitle(true,false,R.string.app_name);
    }

    private fun checkStatus() {
        object : CountDownTimer(2000, 1000) {

            override fun onTick(millisUntilFinished: Long) {            }
            override fun onFinish() {

                if(SharedPreferenceManager.getUser(RootActivity.rootActivity)!=null){
                    clearBackStackTraceAndAddNew(DashboardFragment())
                }else{
                    clearBackStackTraceAndAddNew(SignUpFragment())
                }
            }
        }.start()
    }

    override fun onClick(v: View) {}

    private fun playCycleBell(){

        mp = MediaPlayer.create(context, R.raw.cycle_bell)
        mp.start ()

    }



}
