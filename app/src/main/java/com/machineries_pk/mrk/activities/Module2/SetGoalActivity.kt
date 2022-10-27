package com.machineries_pk.mrk.activities.Module2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.databinding.ActivitySetGoalBinding

class SetGoalActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivitySetGoalBinding
    var a: Boolean = false
    var b: Boolean = false
    var c: Boolean = false
    var d: Boolean = false
    var e: Boolean = false

    var courses = arrayOf<String?>(
        "Easy", "Medium",
        "Hard"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            bg1.setOnClickListener {
                if (!a) {
                    a = true
                    bg1.setBackgroundResource(R.drawable.selected_bg)
                    check1.visibility = View.VISIBLE
                } else {
                    a = false
                    bg1.setBackgroundResource(R.drawable.unselected_bg)
                    check1.visibility = View.GONE
                }
            }

            bg2.setOnClickListener {
                if (!b) {
                    b = true
                    bg2.setBackgroundResource(R.drawable.selected_bg)
                    check2.visibility = View.VISIBLE
                } else {
                    b = false
                    bg2.setBackgroundResource(R.drawable.unselected_bg)
                    check2.visibility = View.GONE
                }
            }
            bg3.setOnClickListener {
                if (!c) {
                    c = true
                    bg3.setBackgroundResource(R.drawable.selected_bg)
                    check3.visibility = View.VISIBLE
                } else {
                    c = false
                    bg3.setBackgroundResource(R.drawable.unselected_bg)
                    check3.visibility = View.GONE
                }
            }
            bg4.setOnClickListener {
                if (!d) {
                    d = true
                    bg4.setBackgroundResource(R.drawable.selected_bg)
                    check4.visibility = View.VISIBLE
                } else {
                    d = false
                    bg4.setBackgroundResource(R.drawable.unselected_bg)
                    check4.visibility = View.GONE
                }
            }
            bg5.setOnClickListener {
                if (!e) {
                    e = true
                    bg5.setBackgroundResource(R.drawable.selected_bg)
                    check5.visibility = View.VISIBLE
                } else {
                    e = false
                    bg5.setBackgroundResource(R.drawable.unselected_bg)
                    check5.visibility = View.GONE
                }
            }



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





        binding.nextbtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }

    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

    }


    override fun onNothingSelected(parent: AdapterView<*>?) {

    }
}