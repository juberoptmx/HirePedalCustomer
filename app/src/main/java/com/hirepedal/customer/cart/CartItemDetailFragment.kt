package com.hirepedal.customer.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirepedal.customer.R
import com.hirepedal.customer.base.BaseFragment

class CartItemDetailFragment: BaseFragment() {

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_cart_item_detail, null)
        bindViews(v)
        attachListeners()
        return v
    }

    override fun onResume() {
        super.onResume()
        setActionbarTitle(false, true, R.string.item_detail)
    }

}