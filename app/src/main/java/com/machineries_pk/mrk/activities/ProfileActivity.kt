package com.machineries_pk.mrk.activities

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.machineries_pk.mrk.R
import io.paperdb.Paper
import android.content.Intent
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.machineries_pk.mrk.activities.List.EmployeeModel
import com.machineries_pk.mrk.databinding.ActivityProfileBinding


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding: ActivityProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backBtn.setOnClickListener { finish() }

        var itemlist: ArrayList<EmployeeModel>? = ArrayList()

        itemlist = Paper.book().read("itemlist", itemlist)
        if (itemlist == null) {
            itemlist = ArrayList()
        }


        binding.greenCard.setOnClickListener {

            if (binding.userName.text.toString().isNotEmpty() || binding.userPhone.text.toString()
                    .isNotEmpty() || binding.userEmail.text.toString().isNotEmpty()
            ) {


                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE
                    ) != PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.INTERNET
                    ) != PackageManager.PERMISSION_GRANTED
                ) {

                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.INTERNET
                        ),
                        1
                    )
                } else {
                    Paper.book().write("name", binding.userName.text.toString())
                    Paper.book().write("email", binding.userEmail.text.toString())
                    Paper.book().write("phone", binding.userPhone.text.toString())


                    itemlist.add(
                        EmployeeModel(
                            binding.userName.text.toString(),
                            binding.userPhone.text.toString(),
                            binding.userEmail.text.toString(),
                            Paper.book().read("final_data","")
                        )
                    )
                    Paper.book().write("itemlist", itemlist)

                    val intent = Intent(this, GreenCardActivity::class.java)
                    startActivity(intent)
                }


            } else {
                Toast.makeText(this, "Please input Data", Toast.LENGTH_LONG).show()
            }


        }


        binding.saveBtn.setOnClickListener {

            if (binding.userName.text.toString().isNotEmpty() || binding.userPhone.text.toString()
                    .isNotEmpty() || binding.userEmail.text.toString().isNotEmpty()
            ) {


                Paper.book().write("name", binding.userName.text.toString())
                Paper.book().write("email", binding.userEmail.text.toString())
                Paper.book().write("phone", binding.userPhone.text.toString())


                itemlist.add(
                    EmployeeModel(
                        binding.userName.text.toString(),
                        binding.userPhone.text.toString(),
                        binding.userEmail.text.toString(),
                        Paper.book().read("final_data","")
                    )
                )
                 Paper.book().write("itemlist", itemlist)

                Toast.makeText(this, "Data saved successfully", Toast.LENGTH_LONG).show()
                val i = Intent(this@ProfileActivity, ListActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)


            } else {
                Toast.makeText(this, "Please input Data", Toast.LENGTH_LONG).show()
            }


        }
    }
}