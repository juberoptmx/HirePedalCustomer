package com.hirepedal.customer.myaccount

import android.graphics.drawable.Drawable
import android.text.InputType
import android.widget.EditText
import com.hirepedal.customer.utils.Utility

class FormItem {

    var itemId:Int = 0
    var hint: String? = null
    var icon: Drawable? = null
    var value: String? = null
    var prefKey: String? = null
    var validationType: FormItemValidation = FormItemValidation.NONE
    var keypadType: FormItemKeypadType = FormItemKeypadType.TEXT
    var viewType:FormItemViewType = FormItemViewType.PLAIN_TEXT
    var maxCharLimit:Int? = null
    var options:ArrayList<String>? = null


    data class ValidationData(val valid: Boolean, val msg: String?)


    constructor(viewType:FormItemViewType,hint: String, icon: Drawable, value:String, prefKey:String, validation: FormItemValidation, keypadType: FormItemKeypadType, maxCharLimit:Int, options:ArrayList<String>) {
        this.viewType = viewType
        this.hint = hint
        this.icon = icon
        this.value = value
        this.prefKey = prefKey
        this.validationType = validation
        this.keypadType = keypadType
        this.maxCharLimit = maxCharLimit
        this.options = options
    }

    constructor(itemId:Int,viewType:FormItemViewType,hint: String, icon: Drawable, value:String, prefKey:String, validation: FormItemValidation, keypadType: FormItemKeypadType, maxCharLimit:Int, options:ArrayList<String>) {
        this.itemId = itemId
        this.viewType = viewType
        this.hint = hint
        this.icon = icon
        this.value = value
        this.prefKey = prefKey
        this.validationType = validation
        this.keypadType = keypadType
        this.maxCharLimit = maxCharLimit
        this.options = options
    }

    fun setKeyboardType(editText:EditText) {
        if (this.keypadType != null) {
            when(this.keypadType){
                FormItemKeypadType.EMAIL -> {
                    editText.inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
                }
                FormItemKeypadType.TEXT -> {
                    editText.inputType = InputType.TYPE_CLASS_TEXT
                }
                FormItemKeypadType.NUMBERS -> {
                    editText.inputType = InputType.TYPE_CLASS_NUMBER
                }
                FormItemKeypadType.PHONE -> {
                    editText.inputType = InputType.TYPE_CLASS_PHONE
                }
                FormItemKeypadType.MULTILINE_TEXT -> {
//                    editText.inputType = InputType.TYPE_TEXT_FLAG_MULTI_LINE
                }
            }
        } else {
            editText.inputType = InputType.TYPE_CLASS_TEXT
        }
    }

    fun validateField() : ValidationData {
        return when(this.validationType){
            FormItemValidation.NONE -> {
                ValidationData(true,"")
            }
            FormItemValidation.MUST_HAVE -> {
                if (Utility.mustHaveValidation(this)) {
                    ValidationData(true,"")
                } else {
                    ValidationData(false,"This value should not be empty")
                }
            }
            FormItemValidation.EMAIL -> {
                if (Utility.validateEmail(this.value)) {
                    ValidationData(true,"")
                } else {
                    ValidationData(false,"Please enter a valid email address")
                }
            }
            FormItemValidation.PHONE -> {
                if (Utility.validatePhoneNumber(this.value)) {
                    ValidationData(true,"")
                } else {
                    ValidationData(false,"Please enter a valid phone number")
                }
            }
            FormItemValidation.AADHAR -> {
                if (Utility.aadharValidation(this)) {
                    ValidationData(true,"")
                } else {
                    ValidationData(false,"Please enter a valid aadhar card number")
                }
            }
            FormItemValidation.PAN -> {
                if (Utility.panCardValidation(this)) {
                    ValidationData(true,"")
                } else {
                    ValidationData(false,"Please enter a valid PAN card number")
                }
            }
            else -> {
                return ValidationData(true,"")
            }
        }
    }
}

enum class FormItemValidation(val validation: Int) {
    NONE(1), MUST_HAVE(2),  EMAIL(3), PHONE(4), AADHAR(5), PAN(6), GAS_NUMBER(7),
    EB_NUMBER(8), DRIVING_LICENSE(9)
}

enum class FormItemKeypadType(val keyboard: Int) {
    EMAIL(1), TEXT(2), NUMBERS(3), PHONE(4), MULTILINE_TEXT(5)

}

enum class FormItemViewType(val itemType: Int) {
    PLAIN_TEXT(1), DATE_PICKER(2), DROP_DOWN(3), MULTILINE_TEXT(4)
}