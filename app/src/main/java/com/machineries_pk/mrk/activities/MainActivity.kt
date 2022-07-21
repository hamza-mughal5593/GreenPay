package com.machineries_pk.mrk.activities

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Window
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.NewCode.CalculateResultActivity
import com.machineries_pk.mrk.activities.NewCode.LoginActivity
import com.machineries_pk.mrk.activities.Utils.MySingleton
import com.machineries_pk.mrk.databinding.ActivityMainBinding
import io.paperdb.Paper
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity() {


    var total_tree: Int = 5

    var total_co = 2.6


    var final_tree: Int = 0
    var final_co = 0.0

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.simpleSeekBar.setOnSeekBarChangeListener(SeekBar.OnSeekBarChangeListener)

        binding.backBtn.setOnClickListener { finish() }

        var total_diet_tree = 0
        var total_home_tree = 0
        var total_drive_tree = 0
        var total_fly_tree = 0
        var total_stop_tree = 0


        var total_diet_co = 0.0
        var total_home_co = 0.0
        var total_drive_co = 0.0
        var total_fly_co = 0.0
        var total_stop_co = 0.0



        binding.canclBtn.setOnClickListener { onBackPressed() }
        binding.backBtn.setOnClickListener { onBackPressed() }

        binding.diet.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                total_diet_co = (progress / 10.0)
                total_diet_tree = if (progress < 9)
                    1
                else
                    2
                if (progress == 0)
                    total_diet_tree = 0

                final_tree =
                    total_tree + total_diet_tree + total_home_tree + total_drive_tree + total_fly_tree + total_stop_tree
//                binding.totalTree.text = "total tree: $total_tree"

                if (progress > 6)
                    total_diet_co -= 0.8



                final_co =
                    total_co + total_diet_co + total_home_co + total_drive_co + total_fly_co + total_stop_co
                final_co = round(final_co, 1)
                binding.finalText.text =
                    "My annual Co2 emission is ($final_co T), $final_tree mangroves trees can sequester Co2 in 20 years"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })



        binding.home.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                total_home_co = (progress / 10.0)
                total_home_tree = when {
                    progress < 6 -> 1
                    progress < 12 -> 2
                    progress < 18 -> 3
                    progress < 24 -> 4
                    progress < 30 -> 5
                    else -> 6
                }
                if (progress == 0)
                    total_home_tree = 0



                final_tree =
                    total_tree + total_diet_tree + total_home_tree + total_drive_tree + total_fly_tree + total_stop_tree


                if (progress > 10)
                    total_home_co -= 0.7

                final_co =
                    total_co + total_diet_co + total_home_co + total_drive_co + total_fly_co + total_stop_co
                final_co = round(final_co, 1)
                binding.finalText.text =
                    "My annual Co2 emission is ($final_co T), $final_tree mangroves trees can sequester Co2 in 20 years"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        binding.drive.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                total_drive_co = (progress / 10.0)
                total_drive_tree = when {
                    progress < 5 -> 1
                    progress < 10 -> 2
                    progress < 15 -> 3
                    progress < 20 -> 4
                    progress < 25 -> 5
                    progress < 30 -> 6
                    progress < 35 -> 7
                    progress < 40 -> 8
                    progress < 45 -> 9
                    progress < 50 -> 10
                    progress < 55 -> 11
                    progress < 60 -> 12
                    progress < 65 -> 13
                    progress < 70 -> 14
                    progress < 75 -> 15
                    else -> 16
                }
                if (progress == 0)
                    total_drive_tree = 0
//                diet_co = new


                final_tree =
                    total_tree + total_diet_tree + total_home_tree + total_drive_tree + total_fly_tree + total_stop_tree

                final_co =
                    total_co + total_diet_co + total_home_co + total_drive_co + total_fly_co + total_stop_co
                final_co = round(final_co, 1)
                binding.finalText.text =
                    "My annual Co2 emission is ($final_co T), $final_tree mangroves trees can sequester Co2 in 20 years"
//                binding.totalCo.text = "total tree: $total_co"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        binding.fly.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                total_fly_co = (progress / 10.0)

                total_fly_tree = when {
                    progress < 5 -> 1
                    progress < 10 -> 2
                    progress < 15 -> 3
                    progress < 20 -> 4
                    progress < 25 -> 5
                    progress < 30 -> 6
                    progress < 35 -> 7
                    progress < 40 -> 8
                    progress < 45 -> 9
                    progress < 50 -> 10
                    progress < 55 -> 11
                    progress < 60 -> 12
                    progress < 65 -> 13
                    progress < 70 -> 14
                    progress < 75 -> 15
                    progress < 80 -> 16
                    progress < 85 -> 17
                    progress < 90 -> 18
                    else -> 19
                }
                if (progress == 0) {
                    total_fly_tree = 0
                }


                final_tree =
                    total_tree + total_diet_tree + total_home_tree + total_drive_tree + total_fly_tree + total_stop_tree

                final_co =
                    total_co + total_diet_co + total_home_co + total_drive_co + total_fly_co + total_stop_co
                final_co = round(final_co, 1)

                binding.finalText.text =
                    "My annual Co2 emission is ($final_co T), $final_tree mangroves trees can sequester Co2 in 20 years"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })



        binding.shop.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {

                total_stop_tree = 0
                total_stop_co = (progress / 10.0)


                if (progress < 6) {
                    total_stop_tree = 1
                } else if (progress < 12) {
                    total_stop_tree = 2
                } else if (progress < 18) {
                    total_stop_tree = 3
                } else if (progress < 24) {
                    total_stop_tree = 4
                } else if (progress < 30) {
                    total_stop_tree = 5
                } else {
                    total_stop_tree = 6
                }
                if (progress == 0) {
                    total_stop_tree = 0
                }


                final_tree =
                    total_tree + total_diet_tree + total_home_tree + total_drive_tree + total_fly_tree + total_stop_tree
                if (progress > 10)
                    total_stop_co -= 1.1

                final_co =
                    total_co + total_diet_co + total_home_co + total_drive_co + total_fly_co + total_stop_co
                final_co = round(final_co, 1)

                binding.finalText.text =
                    "My annual Co2 emission is ($final_co T), $final_tree mangroves trees can sequester Co2 in 20 years"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {

            }
        })


        binding.calculate.setOnClickListener {

//            Paper.book().write("final_tree", final_tree.toString())
//            Paper.book().write("final_co", final_co.toString())
//            Paper.book().write("final_data", binding.finalText.text.toString())
val lifestyle = "$final_tree,$final_co"
            Paper.book().write("lifestyle",lifestyle)




            update_lifestyle(lifestyle)

        }


    }


    override fun onBackPressed() {
        val intent = Intent(this, CalculateResultActivity::class.java)
        startActivity(intent)
    }

    private fun update_lifestyle(lifestyle: String) {

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
                            "Calculated Successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                        val intent = Intent(this, CalculateResultActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(
                            this,
                            "Error..!",
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
                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val header: MutableMap<String, String> = HashMap()

                header["do"]= "life_style"
                header["apikey"]= "dwamsoft12345"
                header["email"]=  Paper.book().read("email","")
                header["lifestyle"]=lifestyle

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



    fun round(value: Double, places: Int): Double {
        var value = value
        require(places >= 0)
        val factor = Math.pow(10.0, places.toDouble()).toLong()
        value = value * factor
        val tmp = Math.round(value)
        return tmp.toDouble() / factor
    }
}