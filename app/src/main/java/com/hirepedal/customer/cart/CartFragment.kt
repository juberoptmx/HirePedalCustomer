package com.hirepedal.customer.cart


import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hirepedal.customer.R
import com.hirepedal.customer.activities.RootActivity
import com.hirepedal.customer.base.BaseFragment
import com.hirepedal.customer.utils.AppInfo
import com.hirepedal.customer.utils.sharedpreference.SharedPreferenceManager
import com.titan.fasttrack.myapps.CartItem
import kotlinx.android.synthetic.main.fragment_cart.*
import java.util.*

class CartFragment: BaseFragment(), CartListener {


    private var allAppsList = ArrayList<CartItem>()
    private var cartItems = ArrayList<AppInfo>()
    private val res = ArrayList<AppInfo>()

    val pm: PackageManager
        get() = context!!.packageManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cartItems = getPackages()
        setHasOptionsMenu(true)
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
        val mAdapter = CartAdapter(RootActivity.rootActivity,allAppsList,this)
        rv_cart.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rv_cart.adapter = mAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("DDD","DDD")
    }

    private fun getPackages(): ArrayList<AppInfo> {
        val apps = getInstalledApps(false) /* false = no system packages */
        val max = apps.size
        for (i in 0 until max) {
            apps[i].prettyPrint()
            allAppsList.add(CartItem(apps[i].appname, apps[i].icon!!, apps[i].pname, false))
        }
        return apps
    }

    private fun getInstalledApps(getSysPackages: Boolean): ArrayList<AppInfo> {

        val packs = pm.getInstalledPackages(0)

        for (i in packs.indices) {
            val p = packs.get(i)
            val appInfo = pm.getApplicationInfo(p.packageName, 0)

            val isSystemApp = appInfo.flags and ApplicationInfo.FLAG_SYSTEM !== 0
            if(!isSystemApp){
                val newInfo = AppInfo()
                newInfo.appname = p.applicationInfo.loadLabel(pm).toString()
                newInfo.pname = p.packageName
                newInfo.versionName = p.versionName
                newInfo.versionCode = p.versionCode
                newInfo.icon = p.applicationInfo.loadIcon(pm)
                res.add(newInfo)
            }
        }
        return res
    }

    override fun onMethodCallback1(packageName:String, selection: Boolean) {

                var selectedAppList = Gson().fromJson<ArrayList<String>>(SharedPreferenceManager.getMyApps(RootActivity.rootActivity), object : TypeToken<ArrayList<String>>() {}.type)
                if (selectedAppList == null) {
                    selectedAppList = ArrayList<String>()
                }
                changeAppSelectionStatus(selectedAppList,packageName,selection)
                SharedPreferenceManager.saveMyApps(RootActivity.rootActivity,selectedAppList)


    }

    private fun changeAppSelectionStatus(selectedAppList: ArrayList<String>, packageName:String, selection:Boolean) {

        if (selection) {
            if (!selectedAppList.contains(packageName)) {
                selectedAppList.add(packageName)
            }
        } else {
            if (selectedAppList.contains(packageName)) {
                selectedAppList.remove(packageName)
            }
        }
    }



}