package com.machineries_pk.mrk.activities.NewCode

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import com.machineries_pk.mrk.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivitySignupBinding

    var courses = arrayOf<String?>("Select Account Type", "Account Type 1",
        "Account Type 2", "Account Type 3",
        "Account Type 4", "Account Type 5")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



        binding.accountype.onItemSelectedListener = this

        // Create the instance of ArrayAdapter
        // having the list of courses
        val ad: ArrayAdapter<*> = ArrayAdapter<Any?>(
            this,
            android.R.layout.simple_spinner_item,
            courses)

        // set simple layout resource file
        // for each item of spinner
        ad.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item)

        // Set the ArrayAdapter (ad) data on the
        // Spinner which binds data to spinner
        binding.accountype.adapter = ad

    }
    override fun onItemSelected(parent: AdapterView<*>?,
                                view: View, position: Int,
                                id: Long) {
        // make toastof name of course
        // which is selected in spinner
        Toast.makeText(applicationContext,
            courses[position],
            Toast.LENGTH_LONG)
            .show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}