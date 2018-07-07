package com.hirepedal.customer.myaccount

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Context
import android.support.design.widget.TextInputLayout
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Spinner
import com.hirepedal.customer.R
import com.hirepedal.customer.activities.RootActivity
import java.util.*
import android.widget.ArrayAdapter
import android.text.InputFilter
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.text.InputType
import android.view.inputmethod.InputMethodManager
import kotlin.collections.ArrayList


class MyAccountAdapter(internal var context: Context, private var infoList: ArrayList<FormItem>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when(viewType) {
            1 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plain_edit_text,parent,false)
                return MyAccountAdapter.PlainEditTextHolder(view)
            }
            2 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_date_picker_field,parent,false)
                return MyAccountAdapter.DatePickerEditTextHolder(view)
            }
            3 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_drop_down_field,parent,false)
                return MyAccountAdapter.DropDownEditTextHolder(view)
            }
            4 -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_multi_line_edit_text,parent,false)
                return MyAccountAdapter.MultiLineEditTextHolder(view)
            }
            else -> {
                val view = LayoutInflater.from(parent.context).inflate(R.layout.item_plain_edit_text,parent,false)
                return MyAccountAdapter.PlainEditTextHolder(view)
            }
        }

    }

    override fun getItemViewType(position: Int): Int {

        return infoList[position].viewType.itemType
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(infoList[position].viewType){
            FormItemViewType.PLAIN_TEXT -> {
                bindPlainTextViewHolder(holder,position)
            }
            FormItemViewType.DATE_PICKER -> {
                bindDatePickerViewHolder(holder,position)
            }
            FormItemViewType.DROP_DOWN -> {
                bindDropDownViewHolder(holder,position)
            }
            FormItemViewType.MULTILINE_TEXT -> {
                bindMultiLineTextViewHolder(holder,position)
            }
        }

    }

    private fun bindPlainTextViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var hol = holder as PlainEditTextHolder
        val myAccountItem = infoList[position]
        holder.imageView.visibility = View.INVISIBLE
        hol.textField.editText!!.setText(myAccountItem.value)
        hol.textField.hint = myAccountItem.hint
        hol.textField.editText!!.tag = position
        hol.textField.editText!!.addTextChangedListener(PlainEditTextWatcher(hol.textField.editText!!))
        if (myAccountItem.maxCharLimit != null) {
            hol.textField.editText!!.limitLength(myAccountItem.maxCharLimit!!.toInt())
        }
        hol.textField.editText!!.inputType = InputType.TYPE_CLASS_PHONE
        myAccountItem.setKeyboardType(hol.textField.editText!!)
    }

    private fun bindDatePickerViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var hol = holder as DatePickerEditTextHolder
        val myAccountItem = infoList[position]
        hol.imageView.visibility = View.VISIBLE
        hol.imageView.setImageDrawable(myAccountItem.icon)
        hol.textField.editText!!.setText(myAccountItem.value)
        hol.textField.hint = myAccountItem.hint
        hol.textField.editText!!.tag = position
        hol.textField.editText!!.addTextChangedListener(PlainEditTextWatcher(hol.textField.editText!!))
        hol.textField.editText!!.setInputType(InputType.TYPE_NULL)

        hol.textField.editText!!.setOnClickListener{
            showDatePicker(hol.textField.editText!!)
        }
        hol.textField.editText!!.setOnFocusChangeListener{ v, hasFocus ->
            if (hasFocus) {
                showDatePicker(hol.textField.editText!!)
            }
        }
    }

    private fun bindDropDownViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var hol = holder as DropDownEditTextHolder
        val myAccountItem = infoList[position]
        hol.imageView.visibility = View.INVISIBLE
        hol.textField.editText!!.setText(" ")
        hol.textField.hint = myAccountItem.hint
        hol.textField.editText!!.tag = position
        hol.textField.editText!!.addTextChangedListener(PlainEditTextWatcher(hol.textField.editText!!))
        hol.bgButton.visibility = View.GONE
        val spinnerArrayAdapter = ArrayAdapter<String>(RootActivity.rootActivity, android.R.layout.simple_spinner_dropdown_item, myAccountItem.options)
        hol.spinner.setAdapter(spinnerArrayAdapter)
        if (myAccountItem.value != null) {
            var position = myAccountItem.options!!.indexOf(myAccountItem.value.toString())
            hol.spinner.setSelection(position,true)
        }
        hol.spinner.onItemSelectedListener = object : OnItemSelectedListener {
            override fun onItemSelected(parentView: AdapterView<*>, selectedItemView: View, position: Int, id: Long) {
                myAccountItem.value = myAccountItem.options!![position]
            }
            override fun onNothingSelected(parentView: AdapterView<*>) {
                myAccountItem.value = null
            }
        };

    }

    private fun bindMultiLineTextViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        var hol = holder as MultiLineEditTextHolder
        val myAccountItem = infoList[position]
        holder.imageView.visibility = View.INVISIBLE
        hol.textField.editText!!.setText(myAccountItem.value)
        hol.textField.hint = myAccountItem.hint
        hol.textField.editText!!.tag = position
        hol.textField.editText!!.addTextChangedListener(PlainEditTextWatcher(hol.textField.editText!!))
        if (myAccountItem.maxCharLimit != null) {
            hol.textField.editText!!.limitLength(myAccountItem.maxCharLimit!!.toInt())
        }
        myAccountItem.setKeyboardType(hol.textField.editText!!)
    }

    override fun getItemCount(): Int {
        return infoList.size
    }

    class PlainEditTextHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.plain_edit_text_icon)
        var textField = itemView.findViewById<TextInputLayout>(R.id.plain_edit_text_layout)
    }

    class DatePickerEditTextHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.img_date_picker_edit_text_icon)
        var textField = itemView.findViewById<TextInputLayout>(R.id.text_layout_date_picker_field)
    }

    class DropDownEditTextHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.img_drop_down_edit_text_icon)
        var textField = itemView.findViewById<TextInputLayout>(R.id.text_layout_drop_down_field)
        var bgButton = itemView.findViewById<Button>(R.id.btn_drop_down_select)
        var spinner = itemView.findViewById<Spinner>(R.id.spin_drop_down_select)
    }

    class MultiLineEditTextHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageView = itemView.findViewById<ImageView>(R.id.multi_line_edit_text_icon)
        var textField = itemView.findViewById<TextInputLayout>(R.id.multi_line_edit_text_layout)
    }

    inner class PlainEditTextWatcher : TextWatcher {

        private var textField: EditText? = null

        constructor(editText: EditText) {
            this.textField = editText
        }

        override fun afterTextChanged(p0: Editable?) {
            var item = infoList[Integer.parseInt(this.textField!!.tag.toString())]
            if (item != null) {
                item.value = p0.toString()
            }
            val (status,message) = item.validateField()
            if (!status) {
                this.textField!!.setError(message)
            }
        }

        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }

        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
        }
    }

    private fun showDatePicker(editText:EditText) {
        //  Hiding keyboard if active
        var imm =  RootActivity.rootActivity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.getWindowToken(), 0)

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val dpd = DatePickerDialog(RootActivity.rootActivity,5,DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            editText.setText("" + dayOfMonth + "/" + monthOfYear + "/" + year)
            var item = infoList[Integer.parseInt(editText.tag.toString())]
            if (item != null) {
                item.value = "" + dayOfMonth + "/" + monthOfYear + "/" + year
            }
        }, year, month, day)

        dpd.show()

    }

    fun EditText.limitLength(maxLength: Int) {
        filters = arrayOf(InputFilter.LengthFilter(maxLength))
    }

}