package com.hirepedal.customer.cart


import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirepedal.customer.R
import com.hirepedal.customer.R.id.rv_cart
import com.hirepedal.customer.activities.RootActivity
import com.hirepedal.customer.base.BaseFragment
import com.hirepedal.customer.utils.AppInfo
import java.util.*
import kotlinx.android.synthetic.main.fragment_cart.*


class CartFragment: BaseFragment(), CartListener {

    private var cartItemList = ArrayList<CartItem>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getCartItems()
        setHasOptionsMenu(true)
    }

    private fun getCartItems() {

        // Hercules
        cartItemList.add(CartItem("Hercules", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.9510036, 75.7577134))
        cartItemList.add(CartItem("Hercules", 2, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules),
                "Roadeo", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.903857, 75.821208))
        cartItemList.add(CartItem("Hercules", 3, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules),
                "Ryders", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.9359042, 75.7114598))
        cartItemList.add(CartItem("Hercules", 4, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules),
                "Turbodrive MTB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.996827, 75.902208))
        cartItemList.add(CartItem("Hercules", 5, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules),
                "CMX", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.829615, 75.644113))

        // BSA
        cartItemList.add(CartItem("BSA", 6, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa),
                "Champ", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.943993, 75.809052))
        cartItemList.add(CartItem("BSA", 7, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa),
                "Toddlers", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.996200, 75.828222))
        cartItemList.add(CartItem("BSA", 7, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa),
                "Ladybird", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.938494, 75.632905))
        cartItemList.add(CartItem("BSA", 8, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa),
                "Workouts", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.929836, 75.840632))
        cartItemList.add(CartItem("BSA", 9, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa),
                "Jr. Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                27.030329, 75.623733))
        cartItemList.add(CartItem("BSA", 10, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.898083, 75.895666))



        // Hero Cycles
        cartItemList.add(CartItem("Hero Cycles", 11, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "Maxim Fun Series", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.898083, 75.752146))

        cartItemList.add(CartItem("Hero Cycles", 12, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "Special Kidz Bikes", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.913960, 75.839553))
        cartItemList.add(CartItem("Hero Cycles", 13, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "Super Start Series", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                27.066849, 75.602690))
        cartItemList.add(CartItem("Hero Cycles", 14, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "SLR", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.903291, 75.812023))
        cartItemList.add(CartItem("Hero Cycles", 15, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "Ranger MTB/ATB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.9068372, 75.7992979))
        cartItemList.add(CartItem("Hero Cycles", 16, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "City Bikes", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.872220, 75.812781))
        cartItemList.add(CartItem("Hero Cycles", 17, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.929565, 75.757142))
        cartItemList.add(CartItem("Hero Cycles", 18, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "Hero Sprint", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.961072, 75.846072))
        cartItemList.add(CartItem("Hero Cycles", 19, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "ATB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.928752, 26.928752))
        cartItemList.add(CartItem("Hero Cycles", 19, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_cycles),
                "Master Blasters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.880966, 75.752126))


    }

    override fun onClick(p0: View?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_cart, null)
        bindViews(v)
        attachListeners()
        return v
    }

    override fun onResume() {
        super.onResume()
        setActionbarTitle(false,true, R.string.cart_list)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mAdapter = CartAdapter(RootActivity.rootActivity,cartItemList)
        rv_cart.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rv_cart.adapter = mAdapter
    }

    override fun cartItemSelected(cartItem:CartItem) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}