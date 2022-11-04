package com.machineries_pk.mrk.activities.Module2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.machineries_pk.mrk.activities.NewCode.CalculateResultActivity
import com.machineries_pk.mrk.databinding.ActivityGoGreenBinding

class GoGreenActivity : AppCompatActivity() {
    lateinit var binding: ActivityGoGreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGoGreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.joinNow.setOnClickListener {
            val intent = Intent(this, JoinFormActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}