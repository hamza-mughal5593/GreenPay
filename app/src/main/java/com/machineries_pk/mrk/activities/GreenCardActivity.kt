package com.machineries_pk.mrk.activities

import android.R
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.machineries_pk.mrk.databinding.ActivityGreenCardBinding
import io.paperdb.Paper

import android.graphics.Bitmap

import android.os.Environment

import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.os.Handler
import android.text.format.DateFormat
import android.view.PixelCopy
import android.view.View

import androidx.annotation.RequiresApi
import java.io.File
import java.io.FileOutputStream
import java.util.*
import android.content.Intent
import android.graphics.Canvas
import android.net.Uri
import android.provider.ContactsContract
import android.widget.Toast

import androidx.core.content.FileProvider
import com.machineries_pk.mrk.activities.Utils.Utils
import java.io.FileNotFoundException
import java.lang.Exception
import java.text.SimpleDateFormat
import android.graphics.drawable.Drawable

import android.os.Looper
import android.provider.MediaStore
import android.view.PixelCopy.OnPixelCopyFinishedListener
import java.io.OutputStream


class GreenCardActivity : AppCompatActivity(), (Bitmap) -> Unit {
    private lateinit var binding: ActivityGreenCardBinding



    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGreenCardBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.backBtn.setOnClickListener { finish() }

        binding.name.text =  Paper.book().read("name", "")
        binding.email.text=Paper.book().read("email","")
        binding.phone.text= Paper.book().read("phone", "")
        binding.data.text= Paper.book().read("final_data", "")




        binding.saveBtn.setOnClickListener {


//            getBitmapFromView(binding.card,this,this)


            getBitmapFromView(binding.card,this,callback = this)
        }



    }


//    @SuppressLint("ResourceAsColor")
//    private fun getBitmapFromView(view: View): Bitmap? {
//        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
//            val locations = IntArray(2)
//            view.getLocationInWindow(locations)
//            val rect = Rect(
//                locations[0],
//                locations[1], locations[0] + view.width, locations[1] + view.height
//            )
//            PixelCopy.request(
//                window, rect, bitmap,
//                { copyResult ->
//                    if (copyResult == PixelCopy.SUCCESS) {
//                        //                        callback.onResult(bitmap);
//                        bitmap_screenshot = bitmap
//                    }
//                }, Handler(Looper.getMainLooper())
//            )
//            bitmap_screenshot
//        } else {
//            val returnedBitmap =
//                Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
//            val canvas = Canvas(returnedBitmap)
//            val bgDrawable = view.background
//            if (bgDrawable != null) {
//                bgDrawable.draw(canvas)
//            } else {
//                canvas.drawColor(R.color.white)
//            }
//            view.draw(canvas)
//            returnedBitmap
//        }
//    }





    @RequiresApi(Build.VERSION_CODES.O)
    fun getBitmapFromView(view: View, activity: Activity, callback: (Bitmap) -> Unit) {
        activity.window?.let { window ->
            val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
            val locationOfViewInWindow = IntArray(2)
            view.getLocationInWindow(locationOfViewInWindow)
            try {
                PixelCopy.request(window, Rect(locationOfViewInWindow[0], locationOfViewInWindow[1], locationOfViewInWindow[0] + view.width, locationOfViewInWindow[1] + view.height), bitmap, { copyResult ->
                    if (copyResult == PixelCopy.SUCCESS) {
                        callback(bitmap)
                    }
                    // possible to handle other result codes ...
                }, Handler())
            } catch (e: IllegalArgumentException) {
                // PixelCopy may throw IllegalArgumentException, make sure to handle it
                e.printStackTrace()
            }
        }
    }

    override fun invoke(p1: Bitmap) {


//            @SuppressLint("SimpleDateFormat") val simpleDateFormat =
//                SimpleDateFormat("dd-MM-yyyy_hh:mm:ss")
//            val timestamp = simpleDateFormat.format(Date())
//
//
//            val root = Environment.getExternalStorageDirectory()
//                .toString() + "/DCIM/Greenpay Carbon Calculator"
//            val myDir = File(root)
//            if (!myDir.exists()) {
//                myDir.mkdirs()
//            }
//
//            val fname = "green_card$timestamp.png"
//
//            val file = File(myDir, fname)
////            if (file.exists()) file.delete()
//            try {
//                p1.compress(
//                    Bitmap.CompressFormat.PNG,
//                    100,
//                    FileOutputStream(file)
//                )
////                Utils.addImageToGallery(file.absolutePath, this@GreenCardActivity)
//                Toast.makeText(this,"Card saved successfully",Toast.LENGTH_LONG).show()
//            } catch (e: FileNotFoundException) {
//                e.printStackTrace()
//            }
        saveImage(p1,this,"/Greenpay Carbon Calculator")
    }
    private fun saveImage(bitmap: Bitmap, context: Context, folderName: String) {
        if (android.os.Build.VERSION.SDK_INT >= 29) {
            val values = contentValues()
            values.put(MediaStore.Images.Media.RELATIVE_PATH, "Pictures/" + folderName)
            values.put(MediaStore.Images.Media.IS_PENDING, true)
            // RELATIVE_PATH and IS_PENDING are introduced in API 29.

            val uri: Uri? = context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            if (uri != null) {
                saveImageToStream(bitmap, context.contentResolver.openOutputStream(uri))
                values.put(MediaStore.Images.Media.IS_PENDING, false)
                context.contentResolver.update(uri, values, null, null)
            }

        } else {
            val directory = File(Environment.getExternalStorageDirectory().toString() + "/" + folderName)
            // getExternalStorageDirectory is deprecated in API 29

            if (!directory.exists()) {
                directory.mkdirs()
            }
            val fileName = System.currentTimeMillis().toString() + ".png"
            val file = File(directory, fileName)
            saveImageToStream(bitmap, FileOutputStream(file))
            if (file.absolutePath != null) {
                val values = contentValues()
                values.put(MediaStore.Images.Media.DATA, file.absolutePath)
                // .DATA is deprecated in API 29
                context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
            }
        }
        Toast.makeText(this,"Card saved successfully",Toast.LENGTH_SHORT).show()
        val i = Intent(this@GreenCardActivity, ListActivity::class.java)
        i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(i)
    }

    private fun contentValues() : ContentValues {
        val values = ContentValues()
        values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
        values.put(MediaStore.Images.Media.DATE_ADDED, System.currentTimeMillis() / 1000);
        values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis());
        return values
    }

    private fun saveImageToStream(bitmap: Bitmap, outputStream: OutputStream?) {
        if (outputStream != null) {
            try {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
                outputStream.close()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

}