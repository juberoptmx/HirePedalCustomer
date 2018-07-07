package com.hirepedal.customer.myaccount

import android.Manifest
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.hirepedal.customer.R
import com.hirepedal.customer.activities.RootActivity
import com.hirepedal.customer.base.BaseFragment
import com.hirepedal.customer.models.User
import com.hirepedal.customer.splash.SplashFragment
import com.hirepedal.customer.utils.sharedpreference.SharedPreferenceManager
import kotlinx.android.synthetic.main.fragment_my_account.*
import java.io.ByteArrayOutputStream
import java.io.File


class MyAccountFragment : BaseFragment() {

    private var infoList = ArrayList<FormItem>()
    private var nameLabel: TextView? = null
    private var emailLabel: TextView? = null
    private var saveButton: Button? = null
    private var removeAccButton:Button? = null
    private var editPhotoButton:Button? = null
    private var profilePicView:ImageView? = null
    private val GALLERY_REQUEST = 101
    private val CAMERA_REQUEST = 1888
    private val MY_CAMERA_PERMISSION_CODE = 100
    private val PERMISSION_REQUEST_WRITE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        infoList = getInfoList()
        saveAboutMeInfo(false)
    }

    override fun onClick(p0: View) {
        when(p0.id){
            R.id.btn_my_account_save -> {
                saveAboutMeInfo(true)
            }
            R.id.btn_my_account_remove -> {
                removeAccount()
            }
            R.id.btn_my_account_edit_photo -> {
                changeProfilePic()
//                choosePicFromGallery()
            }
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_my_account, null)
        bindViews(v)
        attachListeners()
        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = MyAccountAdapter(RootActivity.rootActivity,infoList!!)
        rv_my_account.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL,false)
        rv_my_account.adapter = adapter
        saveButton!!.setOnClickListener(this)
        removeAccButton!!.setOnClickListener(this)
        editPhotoButton!!.setOnClickListener(this)
        restoreProfilePic()
        var user = Gson().fromJson<User>(SharedPreferenceManager.getUser(RootActivity.rootActivity), object : TypeToken<User>() {}.type)
        nameLabel!!.setText(user.fullName)
        emailLabel!!.setText(user.email)
    }

    override fun bindViews(view: View?) {
        super.bindViews(view)
        nameLabel= view!!.findViewById(R.id.label_my_account_name) as TextView
        emailLabel = view!!.findViewById(R.id.label_my_account_email) as TextView
        saveButton = view!!.findViewById(R.id.btn_my_account_save) as Button
        removeAccButton = view!!.findViewById(R.id.btn_my_account_remove) as Button
        editPhotoButton = view!!.findViewById(R.id.btn_my_account_edit_photo) as Button
        profilePicView = view!!.findViewById(R.id.img_my_account_profile) as ImageView
    }

    override fun onResume() {
        super.onResume()
        setActionbarTitle(false,true, R.string.my_account)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.menu_cart,menu)
    }


    private fun getInfoList() : ArrayList<FormItem> {
        var list = ArrayList<FormItem>()
        var user = Gson().fromJson<User>(SharedPreferenceManager.getUser(RootActivity.rootActivity), object : TypeToken<User>() {}.type)
        if (user.fullName == null) {
            list.add(FormItem(1,FormItemViewType.PLAIN_TEXT,"Name", resources.getDrawable(R.drawable.ic_add),"","hash_key_profile_name", FormItemValidation.MUST_HAVE, FormItemKeypadType.TEXT,250, arrayListOf("") ))
        } else {
            list.add(FormItem(1,FormItemViewType.PLAIN_TEXT,"Name", resources.getDrawable(R.drawable.ic_add),user.fullName!!,"hash_key_profile_name", FormItemValidation.MUST_HAVE, FormItemKeypadType.TEXT,250, arrayListOf("") ))
        }
        if (user.email == null) {
            list.add(FormItem(2,FormItemViewType.PLAIN_TEXT,"Email", resources.getDrawable(R.drawable.ic_add),"","hash_key_profile_email", FormItemValidation.EMAIL, FormItemKeypadType.EMAIL, 250, arrayListOf("")))
        } else {
            list.add(FormItem(2,FormItemViewType.PLAIN_TEXT,"Email", resources.getDrawable(R.drawable.ic_add),user.email!!,"hash_key_profile_email", FormItemValidation.EMAIL, FormItemKeypadType.EMAIL, 250, arrayListOf("")))
        }
        if (user.mobileNumber == null) {
            list.add(FormItem(3,FormItemViewType.PLAIN_TEXT,"Mobile Number", resources.getDrawable(R.drawable.ic_add),"","hash_key_profile_mobile", FormItemValidation.PHONE, FormItemKeypadType.PHONE, 10, arrayListOf("")))
        } else {
            list.add(FormItem(3,FormItemViewType.PLAIN_TEXT,"Mobile Number", resources.getDrawable(R.drawable.ic_add),user.mobileNumber!!,"hash_key_profile_mobile", FormItemValidation.PHONE, FormItemKeypadType.PHONE, 10, arrayListOf("")))
        }
        if (user.dob == null) {
            list.add(FormItem(4,FormItemViewType.DATE_PICKER,"DOB", resources.getDrawable(R.drawable.ic_calander_active),"","hash_key_profile_dob", FormItemValidation.NONE, FormItemKeypadType.NUMBERS, 10, arrayListOf("")))
        } else {
            list.add(FormItem(4,FormItemViewType.DATE_PICKER,"DOB", resources.getDrawable(R.drawable.ic_calander_active),user.dob!!,"hash_key_profile_dob", FormItemValidation.NONE, FormItemKeypadType.NUMBERS, 10, arrayListOf("")))
        }
        if (user.gender == null) {
            list.add(FormItem(5,FormItemViewType.DROP_DOWN,"Gender", resources.getDrawable(android.R.drawable.arrow_down_float),"","hash_key_profile_gender", FormItemValidation.MUST_HAVE, FormItemKeypadType.TEXT, 10, arrayListOf("Male","Female")))
        } else {
            list.add(FormItem(5,FormItemViewType.DROP_DOWN,"Gender", resources.getDrawable(android.R.drawable.arrow_down_float),user.gender!!,"hash_key_profile_gender", FormItemValidation.MUST_HAVE, FormItemKeypadType.TEXT, 10, arrayListOf("Male","Female")))
        }
        if (user.bloodGroup == null) {
            list.add(FormItem(6,FormItemViewType.DROP_DOWN,"Blood Group", resources.getDrawable(android.R.drawable.arrow_down_float),"","hash_key_profile_blood_group", FormItemValidation.MUST_HAVE, FormItemKeypadType.TEXT, 10, arrayListOf("O +ve","O -ve","A +ve","A -ve","B +ve","B -ve","AB +ve","AB -ve")))
        } else {
            list.add(FormItem(6,FormItemViewType.DROP_DOWN,"Blood Group", resources.getDrawable(android.R.drawable.arrow_down_float),user.bloodGroup!!,"hash_key_profile_blood_group", FormItemValidation.MUST_HAVE, FormItemKeypadType.TEXT, 10, arrayListOf("O +ve","O -ve","A +ve","A -ve","B +ve","B -ve","AB +ve","AB -ve")))
        }
        if (user.company == null) {
            list.add(FormItem(7,FormItemViewType.PLAIN_TEXT,"Company Name", resources.getDrawable(R.drawable.ic_add),"","hash_key_profile_company_name", FormItemValidation.NONE, FormItemKeypadType.TEXT, 100, arrayListOf("")))
        } else {
            list.add(FormItem(7,FormItemViewType.PLAIN_TEXT,"Company Name", resources.getDrawable(R.drawable.ic_add),user.company!!,"hash_key_profile_company_name", FormItemValidation.NONE, FormItemKeypadType.TEXT, 100, arrayListOf("")))
        }
        if (user.designation == null) {
            list.add(FormItem(8,FormItemViewType.PLAIN_TEXT,"Designation", resources.getDrawable(R.drawable.ic_add),"","hash_key_profile_designation", FormItemValidation.NONE, FormItemKeypadType.TEXT, 100, arrayListOf("")))
        } else {
            list.add(FormItem(8,FormItemViewType.PLAIN_TEXT,"Designation", resources.getDrawable(R.drawable.ic_add),user.designation!!,"hash_key_profile_designation", FormItemValidation.NONE, FormItemKeypadType.TEXT, 100, arrayListOf("")))
        }
        if (user.address == null) {
            list.add(FormItem(9,FormItemViewType.MULTILINE_TEXT,"Address", resources.getDrawable(R.drawable.ic_add),"","hash_key_profile_address", FormItemValidation.NONE, FormItemKeypadType.MULTILINE_TEXT, 255, arrayListOf("")))
        } else {
            list.add(FormItem(9,FormItemViewType.MULTILINE_TEXT,"Address", resources.getDrawable(R.drawable.ic_add),user.address!!,"hash_key_profile_address", FormItemValidation.NONE, FormItemKeypadType.MULTILINE_TEXT, 255, arrayListOf("")))
        }
        if (user.workPhone == null) {
            list.add(FormItem(10,FormItemViewType.PLAIN_TEXT,"Work Phone Number", resources.getDrawable(R.drawable.ic_add),"","hash_key_profile_work_phone_number", FormItemValidation.PHONE, FormItemKeypadType.PHONE, 10, arrayListOf("")))
        } else {
            list.add(FormItem(10,FormItemViewType.PLAIN_TEXT,"Work Phone Number", resources.getDrawable(R.drawable.ic_add),user.workPhone!!,"hash_key_profile_work_phone_number", FormItemValidation.PHONE, FormItemKeypadType.PHONE, 10, arrayListOf("")))
        }
        if (user.workEmail == null) {
            list.add(FormItem(11,FormItemViewType.PLAIN_TEXT,"Work Mail", resources.getDrawable(R.drawable.ic_add),"","hash_key_profile_work_mail", FormItemValidation.EMAIL, FormItemKeypadType.EMAIL, 100, arrayListOf("")))
        } else {
            list.add(FormItem(11,FormItemViewType.PLAIN_TEXT,"Work Mail", resources.getDrawable(R.drawable.ic_add),user.workEmail!!,"hash_key_profile_work_mail", FormItemValidation.EMAIL, FormItemKeypadType.EMAIL, 100, arrayListOf("")))
        }
        return list
    }

    private fun saveAboutMeInfo(validate:Boolean) {
        for (item in infoList) {
            if (!item.validateField().valid && validate) {
                val dialog = AlertDialog.Builder(context)
                        .setTitle("Warning!!!")
                        .setMessage("Please fill all the mandatory fields and proceed")
                        .setPositiveButton("OK", null).create()
                dialog.show()
                dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_POSITIVE).setTextColor(resources.getColor(R.color.color_primary_orange))
                dialog.getButton(android.support.v7.app.AlertDialog.BUTTON_NEGATIVE).setTextColor(resources.getColor(R.color.color_primary_orange))

                return
            }
        }
        var user = Gson().fromJson<User>(SharedPreferenceManager.getUser(RootActivity.rootActivity), object : TypeToken<User>() {}.type)
        for (item in infoList) {
            when(item.itemId) {
                1 -> {
                    user.fullName = item.value
                }
                2 -> {
                    user.email = item.value
                }
                3 -> {
                    user.mobileNumber = item.value
                }
                4 -> {
                    user.dob = item.value
                }
                5 -> {
                    user.gender = item.value
                }
                6 -> {
                    user.bloodGroup = item.value
                }
                7 -> {
                    user.company = item.value
                }
                8 -> {
                    user.designation = item.value
                }
                9 -> {
                    user.address = item.value
                }
                10 -> {
                    user.workPhone = item.value
                }
                11 -> {
                    user.workEmail = item.value
                }

            }
        }

        SharedPreferenceManager.saveUser(RootActivity.rootActivity,user)
        if(validate) {
            Toast.makeText(this.context, "Information saved successfully..",Toast.LENGTH_LONG).show()
            RootActivity.rootActivity.updateHamburger()
        }
    }

    private fun removeAccount() {
        var alertLayout = layoutInflater.inflate(R.layout.dialogu_remove_account_layout, null)
        var alert = AlertDialog.Builder(RootActivity.rootActivity)
        alert.setView(alertLayout)
        alert.setCancelable(true)
        var dialog = alert.create()
        var yesButton:Button = alertLayout.findViewById(R.id.btn_remove_acc_yes)
        var noButton:Button = alertLayout.findViewById(R.id.btn_remove_acc_no)
        yesButton.setOnClickListener {
            clearSharedPreference()
            dialog.dismiss()
        }
        noButton.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun clearSharedPreference() {
        var editor = SharedPreferenceManager.getSharedPreferenceEditor(RootActivity.rootActivity)
        editor.clear()
        editor.commit()
        val splashFragment = SplashFragment()
        clearBackStackTraceAndAddNew(splashFragment)
    }

    private fun changeProfilePic() {
        if (ContextCompat.checkSelfPermission(RootActivity.rootActivity,android.Manifest.permission.CAMERA) !== PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE ), MY_CAMERA_PERMISSION_CODE)
        } else {
            val cameraIntent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERA_REQUEST)
        }
    }

    private fun choosePicFromGallery() {
        if (ContextCompat.checkSelfPermission(RootActivity.rootActivity, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), PERMISSION_REQUEST_WRITE)
        } else {
            val galleryIntent =  Intent()
            galleryIntent.type = "image/*"
            galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE,true)
            galleryIntent.action = Intent.ACTION_GET_CONTENT
            startActivityForResult(galleryIntent,GALLERY_REQUEST)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var user = Gson().fromJson<User>(SharedPreferenceManager.getUser(RootActivity.rootActivity), object : TypeToken<User>() {}.type)
        if (requestCode == CAMERA_REQUEST && resultCode == Activity.RESULT_OK) {
            val photo = data!!.extras!!.get("data") as Bitmap
            profilePicView!!.setImageBitmap(photo)
            val tempUri = getImageUri(RootActivity.rootActivity, photo)
            var path = getRealPathFromURI(tempUri)
            user.profilePicUrl = path
            SharedPreferenceManager.saveUser(RootActivity.rootActivity,user)

        } else if (requestCode == GALLERY_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data!=null){
                if(data.data != null) {
                    val mImageUri = data.data
                    var path = getRealPathFromURI(mImageUri)
                    user.profilePicUrl = path
                    SharedPreferenceManager.saveUser(RootActivity.rootActivity,user)
                }
            }
        }

        restoreProfilePic()

    }

    fun getImageUri(inContext: Context, inImage: Bitmap): Uri {
        val bytes = ByteArrayOutputStream()
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes)
        val path = MediaStore.Images.Media.insertImage(inContext.contentResolver, inImage, "Title", null)
        return Uri.parse(path)
    }

    fun getRealPathFromURI(uri: Uri): String {
        val cursor = RootActivity.rootActivity.getContentResolver().query(uri, null, null, null, null)
        cursor.moveToFirst()
        val idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
        return cursor.getString(idx)
    }

    private fun restoreProfilePic() {
        var user = Gson().fromJson<User>(SharedPreferenceManager.getUser(RootActivity.rootActivity), object : TypeToken<User>() {}.type)
        if (user.profilePicUrl != null) {
            var pathString = user.profilePicUrl
            var uri = Uri.parse(pathString)
            var imgFile = File(pathString)
            if(imgFile.exists()){
                var myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath())
                profilePicView!!.setImageBitmap(myBitmap)
            } else {
                profilePicView!!.setImageDrawable(ContextCompat.getDrawable(RootActivity.rootActivity, R.drawable.bg_dashboard));
            }
        } else {
            profilePicView!!.setImageDrawable(ContextCompat.getDrawable(RootActivity.rootActivity, R.drawable.bg_dashboard));
        }
    }


}