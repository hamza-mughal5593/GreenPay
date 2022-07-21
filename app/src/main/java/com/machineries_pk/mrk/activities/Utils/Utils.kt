package com.machineries_pk.mrk.activities.Utils

import android.content.ContentValues
import android.content.Context
import android.provider.MediaStore

class Utils {
    companion object{


        val BASE_URL="https://www.greensave.co/"

        fun addImageToGallery(filePath: String?, context: Context) {
            val values = ContentValues()
            values.put(MediaStore.Images.Media.DATE_TAKEN, System.currentTimeMillis())
            values.put(MediaStore.Images.Media.MIME_TYPE, "image/png")
            values.put(MediaStore.MediaColumns.DATA, filePath)
            context.contentResolver.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values)
        }
    }
}