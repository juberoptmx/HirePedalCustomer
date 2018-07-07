package com.hirepedal.customer.cart



import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirepedal.customer.R
import com.hirepedal.customer.activities.RootActivity
import com.hirepedal.customer.base.BaseFragment
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

        // Static
        // Hercules
        cartItemList.add(CartItem("Sangam Cycle Shop", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.9510036, 75.7577134))
        cartItemList.add(CartItem("Bumson Saddle Shop", 2, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Roadeo", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.903857, 75.821208))
        cartItemList.add(CartItem("Jayant Cycles", 3, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Ryders", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.9359042, 75.7114598))
        cartItemList.add(CartItem("Altaf Cycles", 4, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Turbodrive MTB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.996827, 75.902208))
        cartItemList.add(CartItem("Five Start Cycles", 5, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "CMX", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hercules_c1),
                26.829615, 75.644113))

        // BSA
        cartItemList.add(CartItem("Sai Cycles", 6, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Champ", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.943993, 75.809052))
        cartItemList.add(CartItem("VST Cycles", 7, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Toddlers", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.996200, 75.828222))
        cartItemList.add(CartItem("Aabheer Cycles", 7, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Ladybird", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.938494, 75.632905))
        cartItemList.add(CartItem("Chitranjan Cycles", 8, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Workouts", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.929836, 75.840632))
        cartItemList.add(CartItem("Gajendra Cycles", 9, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Jr. Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                27.030329, 75.623733))
        cartItemList.add(CartItem("Kanhaiya Lal Cycles", 10, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_bsa_c1),
                26.898083, 75.895666))



        // Hero Cycles
        cartItemList.add(CartItem("Madhukar Cycles", 11, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Maxim Fun Series", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.898083, 75.752146))

        cartItemList.add(CartItem("Radheyshyam Cycles", 12, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Special Kidz Bikes", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.913960, 75.839553))
        cartItemList.add(CartItem("Subhash Cycles", 13, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Super Start Series", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                27.066849, 75.602690))
        cartItemList.add(CartItem("Vikrant Cycles", 14, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "SLR", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.903291, 75.812023))
        cartItemList.add(CartItem("Vivek Cycles", 15, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Ranger MTB/ATB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.9068372, 75.7992979))
        cartItemList.add(CartItem("Purshottam Cycles", 16, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "City Bikes", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.872220, 75.812781))
        cartItemList.add(CartItem("Jawahar Cycles", 17, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Roadsters", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.929565, 75.757142))
        cartItemList.add(CartItem("Hanumant Cycles", 18, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "Hero Sprint", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.961072, 75.846072))
        cartItemList.add(CartItem("Hariram Cycles", 19, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
                "ATB", 1, "Jayanagar", resources.getDrawable(R.drawable.ic_hero_c1),
                26.928752, 26.928752))
        cartItemList.add(CartItem("Amanpreet Cycles", 19, "Jayanagar", resources.getDrawable(R.drawable.ic_cycle),
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