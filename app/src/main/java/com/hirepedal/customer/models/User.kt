package com.hirepedal.customer.models


class User {

    var firstName: String? = null
    var lastName: String? = null
    var fullName: String? = null
    var gender: String? = null
    var bloodGroup: String? = null
    var company: String? = null
    var designation: String? = null
    var mobileNumber: String? = null
    var workPhone: String? = null
    var workEmail: String? = null

    var email: String? = null
    var dob:String? = null
    var address:String? = null
    var profilePicUrl:String? = null

    fun setFullName(firstName: String, lastName: String) {
        this.fullName = "$firstName $lastName"
    }
}
