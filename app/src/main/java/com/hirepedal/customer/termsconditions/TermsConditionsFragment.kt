package com.hirepedal.customer.aboutme




import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirepedal.customer.R
import com.hirepedal.customer.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_terms_conditions.*

class TermsConditionsFragment : BaseFragment()
{

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_terms_conditions, null)
        bindViews(v)
        attachListeners()

        return v
    }


    override fun onResume() {
        super.onResume()
        setActionbarTitle(false,true,R.string.terms_conditions)
        webview.loadUrl("https://in.yahoo.com/?p=us")
    }
}
