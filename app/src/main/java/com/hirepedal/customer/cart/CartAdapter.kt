package com.hirepedal.customer.cart

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hirepedal.customer.R
import com.hirepedal.customer.activities.RootActivity
import com.hirepedal.customer.base.FragmentCommunicator
import com.hirepedal.customer.utils.sharedpreference.SharedPreferenceManager
import java.util.*


class CartAdapter(internal var context: Context, private var cartList:List<CartItem>) : RecyclerView.Adapter<CartAdapter.CartViewHolder>(){

    private val fragmentCommunicator = context as FragmentCommunicator

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
      //  getCartItems()
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart,parent,false)
        return CartViewHolder(view)
    }

    private fun getCartItems() {
        var cartItemList = ArrayList<String>()

        try {
            cartItemList = Gson().fromJson<ArrayList<String>>(SharedPreferenceManager.getCartDeta(RootActivity.rootActivity), object : TypeToken<ArrayList<String>>() {}.type)
        }catch (e:Exception){
            cartItemList = ArrayList<String>()
        }

    }


    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartList[position]
        holder.imageView.setImageDrawable(cartItem.pImage)
        holder.title.text = cartItem.cycleName
        holder.rootLayout.setOnClickListener {view ->

            var cartItemDetailFragment : CartItemDetailFragment = CartItemDetailFragment()
            fragmentCommunicator.showFragment(cartItemDetailFragment)

        }


    }

    override fun getItemCount(): Int {
        return cartList.size
    }

    class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.iv_cart_item)
        var title = itemView.findViewById<TextView>(R.id.tv_cart_name)
        var rootLayout = itemView.findViewById<LinearLayout>(R.id.all_cart_layout)
    }




}