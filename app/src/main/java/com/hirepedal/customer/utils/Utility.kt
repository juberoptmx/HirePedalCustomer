package com.hirepedal.customer.utils

import android.text.TextUtils
import com.hirepedal.customer.myaccount.FormItem


object Utility {

    @JvmStatic fun validateEmail(email:String?) : Boolean {
        return if (email == null) {
            false
        } else {
            !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
        }
    }

    @JvmStatic fun validatePhoneNumber(phone:String?) : Boolean {
        return if (phone == null) {
            false
        } else {
            val regex = "[^\\d]"
            val phoneDigits = phone.replace(regex.toRegex(), "")
            return (phoneDigits.length == 10)
        }

    }

    @JvmStatic fun mustHaveValidation(item:FormItem) : Boolean {
        if (item.value == null) {
            return false
        } else {
            return (item!!.value!!.length > 0)
        }
    }

    @JvmStatic fun aadharValidation(item:FormItem) : Boolean {
        if (item.value == null) {
            return false
        } else {
            return Verhoeff.ValidateVerhoeff(item.value!!)
        }
    }

    @JvmStatic fun panCardValidation(item:FormItem) : Boolean {
        if (item.value == null) {
            return false
        } else {
            return item.value!!.matches("[A-Z]{5}[0-9]{4}[A-Z]{1}".toRegex())
        }
    }
}