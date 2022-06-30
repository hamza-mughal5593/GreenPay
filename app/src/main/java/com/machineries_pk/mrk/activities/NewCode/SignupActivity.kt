package com.machineries_pk.mrk.activities.NewCode

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.machineries_pk.mrk.databinding.ActivitySignupBinding
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import pl.aprilapps.easyphotopicker.EasyImage.ImageSource
import java.io.File
import java.io.FileNotFoundException

class SignupActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    lateinit var binding: ActivitySignupBinding

    var courses = arrayOf<String?>(
        "Select Account Type", "Account Type 1",
        "Account Type 2", "Account Type 3",
        "Account Type 4", "Account Type 5"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupBtn.setOnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }



        binding.selectprofile.setOnClickListener {
            EasyImage.openChooserWithGallery(
                this,
                "Select an image or make new one",
                0
            )

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

    }

    override fun onItemSelected(
        parent: AdapterView<*>?,
        view: View, position: Int,
        id: Long
    ) {
        // make toastof name of course
        // which is selected in spinner
        Toast.makeText(
            applicationContext,
            courses[position],
            Toast.LENGTH_LONG
        )
            .show()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        EasyImage.handleActivityResult(
            requestCode,
            resultCode,
            data,
            this,
            object : DefaultCallback() {
                override fun onImagePickerError(e: Exception, source: ImageSource, type: Int) {
                    super.onImagePickerError(e, source, type)
                }

                override fun onImagesPicked(
                    imageFiles: List<File>,
                    source: ImageSource,
                    type: Int
                ) {
                    try {
                        val filePath = imageFiles[0].path
//                        mPaths.add(filePath)
                        binding.profileImage.setImageBitmap(BitmapFactory.decodeStream(contentResolver.openInputStream(Uri.fromFile(imageFiles[0]))
                            )
                        )
                    } catch (e: FileNotFoundException) {
                        e.printStackTrace()
                    }
                }
            })
    }
}