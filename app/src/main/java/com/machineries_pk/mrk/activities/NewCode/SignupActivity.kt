package com.machineries_pk.mrk.activities.NewCode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.machineries_pk.mrk.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {
    lateinit var binding: ActivitySignupBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {  }

    }
}