package com.machineries_pk.mrk.activities.Module2

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.text.Html
import android.view.Window
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.Utils.MySingleton
import com.machineries_pk.mrk.databinding.ActivityJoinFormBinding
import io.paperdb.Paper
import org.json.JSONException
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import com.android.volley.TimeoutError as TimeoutError1


class JoinFormActivity : AppCompatActivity() {

    val myCalendar: Calendar = Calendar.getInstance()

    lateinit var binding: ActivityJoinFormBinding

    var gender:Boolean=false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityJoinFormBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.name.text =  Paper.book().read("name", "User Name")
        binding.type.text =  Paper.book().read("pro_level", "")



        val date =
            OnDateSetListener { view, year, month, day ->
                myCalendar.set(Calendar.YEAR, year)
                myCalendar.set(Calendar.MONTH, month)
                myCalendar.set(Calendar.DAY_OF_MONTH, day)
                updateLabel()
            }


        binding.dptxt.setOnClickListener {
            DatePickerDialog(
                this@JoinFormActivity,
                date,
                myCalendar[Calendar.YEAR],
                myCalendar[Calendar.MONTH],
                myCalendar[Calendar.DAY_OF_MONTH]
            ).show()

        }


        binding.apply {
            tvFemale.setOnClickListener{
                if (gender){
                    tvFemale.setTextColor(Color.parseColor("#000000"))
                    tvFemale.text = Html.fromHtml("<u>Female</u>")
                    gender = false
                    tvMale.setTextColor(Color.parseColor("#D6D6D6"))
                    tvMale.text = Html.fromHtml("<u>Male</u>")

                }
            }

            tvMale.setOnClickListener{
                if (!gender){
                    tvMale.setTextColor(Color.parseColor("#000000"))
                    tvMale.text = Html.fromHtml("<u>Male</u>")
                    gender = true
                    tvFemale.setTextColor(Color.parseColor("#D6D6D6"))
                    tvFemale.text = Html.fromHtml("<u>female</u>")

                }
            }
        }


        binding.signupBtn.setOnClickListener {

            if (binding.etEmail.text.toString().isNotEmpty()
                && binding.name.text.toString().isNotEmpty()){
                subscribe()
            }

        }
        
        
    }
    private fun subscribe() {

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
                            "Subscribed successfully.",
                            Toast.LENGTH_SHORT
                        ).show()
                        Paper.book().write("isMember","true")
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
                    is TimeoutError1 -> {
                        message = "Connection TimeOut! Please check your internet connection."
                    }
                }
                Toast.makeText(this@JoinFormActivity, message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val header: MutableMap<String, String> = HashMap()

                header["do"]= "add_member"
                header["apikey"]= "dwamsoft12345"
                header["email"]= Paper.book().read("email","")
                header["isMember"]="true"


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
    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.dptxt.text = dateFormat.format(myCalendar.getTime())
    }
}