package com.machineries_pk.mrk.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.machineries_pk.mrk.R
import android.os.CountDownTimer
import com.machineries_pk.mrk.activities.Boarding.OnBoard
import io.paperdb.Paper


class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
//        val list = Array<Int>(4)


//        getTotalEfficiency()


//        val skillCount = readLine()!!.trim().toInt()
//
//        val skill = Array<Int>(skillCount, { 0 })
//        for (i in 0 until skillCount) {
//            val skillItem = readLine()!!.trim().toInt()
//            skill[i] = skillItem
//        }
//
//        val result = getTotalEfficiency(skill)
//
        object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                //here you can have your logic to set text to edittext
            }

            override fun onFinish() {
                if (Paper.book().read("first",true)){
                    Paper.book().write("first",false)
                    val intent = Intent(this@SplashActivity, OnBoard::class.java)
                    startActivity(intent)
                    finish()
                }else{
                    val intent = Intent(this@SplashActivity, ListActivity::class.java)
                    startActivity(intent)
                    finish()
                }

            }
        }.start()

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