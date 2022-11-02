package com.machineries_pk.mrk.activities.Module2

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.graphics.Color
import android.os.Bundle
import android.text.Html
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.databinding.ActivityJoinFormBinding
import io.paperdb.Paper
import java.text.SimpleDateFormat
import java.util.*


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


    }

    private fun updateLabel() {
        val myFormat = "MM/dd/yy"
        val dateFormat = SimpleDateFormat(myFormat, Locale.US)
        binding.dptxt.text = dateFormat.format(myCalendar.getTime())
    }
}