package com.machineries_pk.mrk.activities.Module2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.machineries_pk.mrk.databinding.ActivitySetGoalBinding

class SetGoalActivity : AppCompatActivity() {
    lateinit var binding: ActivitySetGoalBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySetGoalBinding.inflate(layoutInflater)
        setContentView(binding.root)



        binding.interstRv.layoutManager = LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)

    }
}