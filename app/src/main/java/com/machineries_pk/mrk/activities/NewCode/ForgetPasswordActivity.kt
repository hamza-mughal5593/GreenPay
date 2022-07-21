package com.machineries_pk.mrk.activities.NewCode

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Window
import android.widget.Toast
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.MainActivity
import com.machineries_pk.mrk.activities.Utils.MySingleton
import com.machineries_pk.mrk.databinding.ActivityForgetPasswordBinding
import io.paperdb.Paper
import org.json.JSONException
import org.json.JSONObject
import java.util.HashMap

class ForgetPasswordActivity : AppCompatActivity() {
    lateinit var binding : ActivityForgetPasswordBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgetPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener {
            finish()
        }

        binding.loginBtn.setOnClickListener {

            if (binding.etEmail.text.toString().isNotEmpty()
                && binding.etPassword.text.toString().isNotEmpty()){
                login()
            }

        }
    }

    private fun login() {

        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)



        dialog.show()

        val RegistrationRequest: StringRequest = object : StringRequest(
            Method.POST,
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
                            "Password reset successfully.",
                            Toast.LENGTH_SHORT
                        ).show()

finish()

                    } else {
                        Toast.makeText(
                            this,
                            "Email not found",
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
                Toast.makeText(this@ForgetPasswordActivity, message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val header: MutableMap<String, String> = HashMap()

                header["do"]= "forgotpassword"
                header["apikey"]= "dwamsoft12345"
                header["email"]= binding.etEmail.text.toString()
                header["password"]=binding.etPassword.text.toString()


                return header
            }

        }
        RegistrationRequest.retryPolicy = DefaultRetryPolicy(
            25000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        MySingleton.getInstance(this).addToRequestQueue(RegistrationRequest)
    }



}