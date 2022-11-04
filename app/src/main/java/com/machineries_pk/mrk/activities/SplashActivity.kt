package com.machineries_pk.mrk.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.machineries_pk.mrk.activities.Module2.HomeActivity
import com.machineries_pk.mrk.activities.NewCode.LoginMainActivity
import com.machineries_pk.mrk.databinding.ActivitySplashBinding
import io.paperdb.Paper


class SplashActivity : AppCompatActivity() {
    lateinit var binding: ActivitySplashBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.start.setOnClickListener {

            val email =  Paper.book().read("email","")
            if (email.isNotEmpty()){
                val intent = Intent(this, HomeActivity::class.java)
                startActivity(intent)
            }else{
                val intent = Intent(this@SplashActivity, LoginMainActivity::class.java)
                startActivity(intent)
                finish()
            }

        }


//        object : CountDownTimer(3000, 1000) {
//            override fun onTick(millisUntilFinished: Long) {
//                //here you can have your logic to set text to edittext
//            }
//
//            override fun onFinish() {
//                if (Paper.book().read("first",true)){
//                    Paper.book().write("first",false)
//                    val intent = Intent(this@SplashActivity, OnBoard::class.java)
//                    startActivity(intent)
//                    finish()
//                }else{
//                    val intent = Intent(this@SplashActivity, ListActivity::class.java)
//                    startActivity(intent)
//                    finish()
//                }
//
//            }
//        }.start()

    }

    fun getTotalEfficiency(skill: Array<Int>): Long {
        var evensum = 0
        var oddsum = 0

        skill.forEach {
            if (it/2 == 0){
                evensum *= it
            }else{
                oddsum *= it
            }
        }


        return  evensum.plus(oddsum).toLong()

    }
}