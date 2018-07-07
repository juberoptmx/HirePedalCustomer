package com.hirepedal.customer.utils

import android.text.TextUtils
import android.widget.EditText


object ValidationManager {

    fun validateEmail(email: CharSequence): Boolean {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

    fun validatePhoneNumber(phone: String): Boolean {

        return !TextUtils.isEmpty(phone) && android.util.Patterns.PHONE.matcher(phone).matches() && phone.length == 10
    }

    fun validatePassword(password: String): Boolean {
        return password.length > 5
    }

    private fun isEmptyField(value: CharSequence): Boolean {
        return TextUtils.isEmpty(value)
    }

    fun getValue(fieldName: EditText): String? {

        return if (!isEmptyField(fieldName.text)) {
            fieldName.text.toString()
        } else {
            null
        }
    }

    fun getValidMobileNumber(mobileNumber: String): String {
        val regex = "[^\\d]"
        return mobileNumber.replace(regex.toRegex(), "")
    }

    fun isDataStringValid(checkString: String?): Boolean {

        return (checkString != null && !checkString.equals("null", ignoreCase = true) && checkString.trim { it <= ' ' }.isNotEmpty())

    }

    fun isDataStringValidPhone(checkString: String?): Boolean {

        return (checkString != null && !checkString.equals("null", ignoreCase = true) && checkString.trim { it <= ' ' }.isNotEmpty() && checkString.length == 10)

    }

    fun isLatLongValid(latitude: Double?, longitude: Double?): Boolean {

        if (latitude != null && longitude != null) {
            if (-90 <= latitude && latitude <= +90 && -180 <= longitude && longitude <= 180) {
                return true
            }
        }

        return false

    }


}