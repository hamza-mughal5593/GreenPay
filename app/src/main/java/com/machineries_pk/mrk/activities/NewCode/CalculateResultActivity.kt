package com.machineries_pk.mrk.activities.NewCode

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.machineries_pk.mrk.databinding.ActivityCalculateResultBinding

class CalculateResultActivity : AppCompatActivity() {
    lateinit var binding: ActivityCalculateResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}