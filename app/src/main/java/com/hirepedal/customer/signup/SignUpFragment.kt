package com.hirepedal.customer.signup

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.hirepedal.customer.R
import com.hirepedal.customer.activities.RootActivity
import com.hirepedal.customer.base.BaseFragment
import com.hirepedal.customer.dashboard.DashboardFragment
import com.hirepedal.customer.models.User
import com.hirepedal.customer.utils.sharedpreference.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_signup.*


class SignUpFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_signup,null)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btn_submit.setOnClickListener(this)
    }

    override fun onResume() {
        super.onResume()
        setActionbarTitle(true,false,"")
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_submit -> {
                if(validateFields()){


                    val user = User()
                    user.firstName = et_sign_up_name.text.toString()
                    user.fullName = et_sign_up_name.text.toString()
                    user.mobileNumber = et_mobile_number.text.toString()
                    user.company = et_company.text.toString()
                    user.designation = et_designation.text.toString()
                    user.workPhone = et_work_phone.text.toString()
                    user.workEmail = et_work_email.text.toString()
                    user.bloodGroup = et_blood_group.selectedItem.toString()
                    user.address = et_address_line1.text.toString() + ", " + et_address_line2.text.toString() + ", " + et_city.text.toString() + ", " + et_state.selectedItem.toString()
                    SharedPreferenceManager.saveUser(RootActivity.rootActivity, user)

                    val dashboardFragment = DashboardFragment()
                    showFragment(dashboardFragment)



                    RootActivity.rootActivity.updateHamburger()

                }
            }
        }
    }

    private fun validateFields(): Boolean{
        if(et_sign_up_name.text.toString().isEmpty()){
            et_sign_up_name.error = "Required"
            return false
        }else if(et_mobile_number.text.toString().isEmpty()){
            et_mobile_number.error = "Required"
            return false
        }else if(et_mobile_number.text.toString().length<10){
            et_mobile_number.error = "Mobile Number should be 10 digits"
        }else if(et_work_phone.text.toString().isNotEmpty() && et_work_phone.text.toString().length<10){
            et_work_phone.error = "Work Mobile Number should be 10 digits"
        }else if(et_work_email.text.toString().isNotEmpty() && validateEmail(et_work_email.text.toString())){
            et_work_email.error = "Invalid Email Address."
        }
        return true
    }

    private fun validateEmail(email:String):Boolean{
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}