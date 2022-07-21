package com.machineries_pk.mrk.activities.NewCode

import android.R.attr
import android.app.Dialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.Utils.MySingleton
import io.paperdb.Paper
import org.json.JSONException
import org.json.JSONObject
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource
import java.io.File
import java.io.FileNotFoundException

import android.graphics.Bitmap

import android.os.Environment
import android.R.attr.bitmap
import android.util.Base64
import java.io.ByteArrayOutputStream
import android.util.Patterns

import android.text.TextUtils
import android.R.attr.bitmap
import android.graphics.Matrix
import androidx.exifinterface.media.ExifInterface
import com.bumptech.glide.load.resource.bitmap.TransformationUtils

import com.bumptech.glide.load.resource.bitmap.TransformationUtils.rotateImage
import com.machineries_pk.mrk.databinding.ActivitySignupBinding


class SignupActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivitySignupBinding

    var courses = arrayOf<String?>(
        "Select Account Type", "Green Gift Card",
        "GreenKeeper Membership"
    )
var accounttype = 0

    var  profile_img:String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {


            if (binding.etname.text.toString().isNotEmpty() && binding.etEmail.text.toString().isNotEmpty() && binding.etPassword.text.toString()
                    .isNotEmpty() && accounttype!=0 && profile_img.isNotEmpty()){

                        if (isValidEmail(binding.etEmail.text.toString())){

            signup()
                        }
                else{
                            Toast.makeText(
                                this,
                                "Please enter valid Email Address",
                                Toast.LENGTH_SHORT
                            ).show()
                }


            }else{
                Toast.makeText(
                    this,
                    "Please fill all the fields",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }
        binding.backBtn.setOnClickListener {
            finish()
        }


        binding.selectprofile.setOnClickListener {
            EasyImage.openChooserWithGallery(
                this,
                "Select an image or make new one",
                0
            )

        }


        binding.accountype.onItemSelectedListener = this

        // Create the instance of ArrayAdapter
        // having the list of courses
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            courses
        )

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        binding.accountype.adapter = ad

    }

    fun isValidEmail(target: CharSequence?): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun signup() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)



        dialog.show()

        val RegistrationRequest: StringRequest = object : StringRequest(Method.POST,
            "http://www.greensave.co/",
            Response.Listener
            { response ->
                try {

                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                    val objects = JSONObject(response)
                    val response_code = objects.getInt("success")
                    if (response_code == 1) {

                        Toast.makeText(
                            this,
                            "Signup Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Email Address already exists...!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                dialog.dismiss()
            },
            Response.ErrorListener { volleyError ->
                dialog.dismiss()
                var message: String? = null
                when (volleyError) {
                    is NetworkError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ServerError -> {
                        message =
                            "The server could not be found. Please try again after some time!!"
                    }
                    is AuthFailureError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ParseError -> {
                        message = "Parsing error! Please try again after some time!!"
                    }
                    is NoConnectionError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is TimeoutError -> {
                        message = "Connection TimeOut! Please check your internet connection."
                    }
                }
                Toast.makeText(this@SignupActivity, message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val header: MutableMap<String, String> = HashMap()

                header["do"]= "add_user"
                header["apikey"]= "dwamsoft12345"
                header["name"]= binding.etname.text.toString()
                header["email"]= binding.etEmail.text.toString()
                header["password"]=binding.etPassword.text.toString()
                header["account_type"]=courses[accounttype].toString()
                header["image"]= "data:image/png;base64,$profile_img"
                header["lat"]= Paper.book().read("latitude", 0.0).toString()
                header["lng"]=Paper.book().read("longitude", 0.0).toString()

                return header
            }

//            override fun getParams(): MutableMap<String, String> {
//                val header: MutableMap<String, String> = HashMap()
//
//                header[""] = ""
//
//                header["do"]="add_user"
//                header["apikey"]="dwamsoft12345"
//                header["name"]=binding.etname.text.toString()
//                header["email"]=binding.etEmail.text.toString()
//                header["password"]=binding.etPassword.text.toString()
//                header["account_type"]=courses[accounttype].toString()
//                header["lat"]= Paper.book().read("latitude", 0.0).toString()
//                header["lng"]=Paper.book().read("longitude", 0.0).toString()
//                return header
//            }
        }
        RegistrationRequest.retryPolicy = DefaultRetryPolicy(
            25000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        MySingleton.getInstance(this).addToRequestQueue(RegistrationRequest)
    }





    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View, position: Int,
        id: Long
    ) {
        // make toastof name of course
        // which is selected in spinner

        accounttype  = position
//        Toast.makeText(
//            applicationContext,
//            courses[position],
//            Toast.LENGTH_LONG
//        )
//            .show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            object : DefaultCallback() {
                override fun onImagePickerError(e: Exception, source: ImageSource, type: Int) {
                    super.onImagePickerError(e, source, type)
                }

                override fun onImagesPicked(
                    imageFiles: List<File>,
                    source: ImageSource,
                    type: Int
                ) {
                    try {
                        val filePath = imageFiles[0].path


                        val bmOptions = BitmapFactory.Options()
                        var bitmap = BitmapFactory.decodeFile(filePath, bmOptions)
                        bitmap = Bitmap.createScaledBitmap(
                            bitmap!!,
                            bitmap.width/2,
                            bitmap.height/2,
                            true
                        )

                        val byteArrayOutputStream = ByteArrayOutputStream()
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
                        var  byteArray:ByteArray = byteArrayOutputStream.toByteArray()
                        profile_img = Base64.encodeToString(byteArray, Base64.DEFAULT)

//                        mPaths.add(filePath)
                        binding.profileImage.setImageBitmap(bitmap)


                        val ei = ExifInterface(filePath)
                        val orientation: Int = ei.getAttributeInt(
                            ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_UNDEFINED
                        )

                        var rotatedBitmap: Bitmap? = null
                        when (orientation) {
                            ExifInterface.ORIENTATION_ROTATE_90 -> rotatedBitmap =
                                rotateImage(bitmap, 90)
                            ExifInterface.ORIENTATION_ROTATE_180 -> rotatedBitmap =
                                rotateImage(bitmap, 180)
                            ExifInterface.ORIENTATION_ROTATE_270 -> rotatedBitmap =
                                rotateImage(bitmap, 270)
                            ExifInterface.ORIENTATION_NORMAL -> rotatedBitmap = bitmap
                            else -> rotatedBitmap = bitmap
                        }

                        binding.profileImage.setImageBitmap(rotatedBitmap)


                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            })
    }

    fun rotateImage(source: Bitmap, angle: Float): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(angle)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height,
            matrix, true
        )
    }
}