package com.hirepedal.customer.aboutus



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirepedal.customer.R
import com.hirepedal.customer.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_about_us.*

class AboutUsFragment : BaseFragment() {

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_about_us, null)
        bindViews(v)
        attachListeners()

        return v
    }

    override fun onResume() {
        super.onResume()
        setActionbarTitle(false,true,R.string.about_us)
        aboutus.loadUrl("https://www.google.com/")
    }
}