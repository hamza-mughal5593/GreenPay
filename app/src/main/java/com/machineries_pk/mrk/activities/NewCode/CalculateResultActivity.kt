package com.machineries_pk.mrk.activities.NewCode

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.location.Address
import android.location.Geocoder
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.*
import android.widget.AdapterView.OnItemClickListener
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.*
import com.android.volley.toolbox.StringRequest
import com.bumptech.glide.Glide
import com.bumptech.glide.request.FutureTarget
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.MainActivity
import com.machineries_pk.mrk.activities.Utils.MySingleton
import com.machineries_pk.mrk.activities.Utils.Utils.Companion.BASE_URL
import com.machineries_pk.mrk.databinding.ActivityCalculateResultBinding
import de.hdodenhof.circleimageview.CircleImageView
import io.paperdb.Paper
import org.json.JSONException
import org.json.JSONObject
import java.io.IOException
import java.util.*
import java.util.concurrent.CompletableFuture


class CalculateResultActivity : AppCompatActivity(), OnMapReadyCallback,
    GoogleMap.OnMarkerClickListener {
    private var mMap: GoogleMap? = null
    lateinit var binding: ActivityCalculateResultBinding

    var alluser: ArrayList<Alluser> = ArrayList()
    var allname: ArrayList<String> = ArrayList()


    lateinit var dialog: Dialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCalculateResultBinding.inflate(layoutInflater)
        setContentView(binding.root)


        dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.loading_dialog)


        dialog.show()


        val mapFragment: SupportMapFragment? =
            supportFragmentManager.findFragmentById(com.machineries_pk.mrk.R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)

        val lifestle = Paper.book().read("lifestyle", "")
        val name = Paper.book().read("name", "")
        val account_type = Paper.book().read("account_type", "")

        Paper.book().read("profile_image", "")

        binding.nameUser.text = name

        setdata(
            lifestle,
            name,
            account_type,
            Paper.book().read("profile_image", ""),
            Paper.book().read("latitude", 0.0),
            Paper.book().read("longitude", 0.0)
        )


        binding.popupmenu.setOnClickListener {
            val popup = PopupMenu(this@CalculateResultActivity, binding.popupmenu)
            //Inflating the Popup using xml file
            popup.menuInflater
                .inflate(R.menu.popup_menu, popup.menu)

            //registering popup with OnMenuItemClickListener
            popup.setOnMenuItemClickListener { item ->

                if (item.itemId == R.id.one) {

                    calclut()


                } else if (item.itemId == R.id.logout) {

                    backbtn(true)


                }




                true
            }
            //registering popup with OnMenuItemClickListener

            popup.show() //showing popup menu
        }


    }


    override fun onBackPressed() {
        backbtn(false)
    }


    fun calclut() {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.exit_dialog)
        dialog.show()


        var cancel = dialog.findViewById<TextView>(R.id.cancel)
        var exit = dialog.findViewById<TextView>(R.id.exit)
        var data = dialog.findViewById<TextView>(R.id.data)


        exit.text = "Yes"
        data.text = "Are you sure you want to re-calculate your carbon footprints?"




        cancel.setOnClickListener {
            dialog.dismiss()
        }

        exit.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()

            dialog.dismiss()


        }

    }


    fun backbtn(logout: Boolean) {
        var dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.exit_dialog)
        dialog.show()


        var cancel = dialog.findViewById<TextView>(R.id.cancel)
        var exit = dialog.findViewById<TextView>(R.id.exit)
        var data = dialog.findViewById<TextView>(R.id.data)

        if (logout) {
            exit.text = "Logout"
            data.text = "Are you sure you want to Logout?"

        }


        cancel.setOnClickListener {
            dialog.dismiss()
        }

        exit.setOnClickListener {


            dialog.dismiss()

            if (logout) {
                Paper.book().write("email", "")
                finish()
            } else {
                finishAffinity()
            }


        }

    }

    var pro_name = ""
    var pro_ranking = ""

    private fun setdata(
        lifestle: String,
        name: String,
        account_type: String,
        profileimg: String,
        lat: Double,
        lng: Double
    ) {

        if (lifestle.isNotEmpty()) {
            var data = lifestle.split(",")
            binding.totalTree.text = data[0]
            binding.totalCarbon.text = data[1]
            binding.totalDriving.text = data[2]
            var total_carbon = data[1].toFloat()

            when (total_carbon) {
                in 2.6..6.0 -> {
                    pro_name = "Evergreen"
                    pro_ranking = "1"
                }
                in 6.1..9.6 -> {
                    pro_name = "NatureLove"
                    pro_ranking = "2"
                }
                in 9.6..13.0 -> {
                    pro_name = "ForestKeeper"
                    pro_ranking = "3"
                }
                in 13.1..16.5 -> {
                    pro_name = "GreenStar"
                    pro_ranking = "4"
                }
                in 16.6..20.0 -> {
                    pro_name = "GreenHeart"
                    pro_ranking = "5"
                }
                in 20.1..23.5 -> {
                    pro_name = "Gardener"
                    pro_ranking = "6"
                }
                in 23.6..27.0 -> {
                    pro_name = "Grower"
                    pro_ranking = "7"
                }
            }


        }



        binding.progName.text = pro_name


        binding.username.text = name
        binding.accountype.text = account_type


        binding.countrycode.text = getAddress(lat, lng)





        Glide.with(this).load(BASE_URL + profileimg).placeholder(R.drawable.profile_picture)
            .centerCrop()
            .into(binding.profilePic)
    }

    fun getAddress(lat: Double, lng: Double): String {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]

            return obj.countryCode


        } catch (e: IOException) {
            e.printStackTrace()
        }
        return ""
    }

//    fun getUserCountry(context: Context): String {
//        try {
//            val tm = context.getSystemService(TELEPHONY_SERVICE) as TelephonyManager
//            val simCountry = tm.simCountryIso
//            if (simCountry != null && simCountry.length == 2) { // SIM country code is available
//                return simCountry.toLowerCase(Locale.US)
//            } else if (tm.phoneType != TelephonyManager.PHONE_TYPE_CDMA) { // device is not 3G (would be unreliable)
//                val networkCountry = tm.networkCountryIso
//                if (networkCountry != null && networkCountry.length == 2) { // network country code is available
//                    return networkCountry.toLowerCase(Locale.US)
//                }
//            }
//        } catch (e: Exception) {
//        }
//        return ""
//
//
//
//
//
//    }

    private fun allusers() {


        val RegistrationRequest: StringRequest = @RequiresApi(Build.VERSION_CODES.N)
        object : StringRequest(
            Method.POST,
            "http://www.greensave.co/",
            Response.Listener
            { response ->
                try {

                    if (dialog.isShowing) {
                        dialog.dismiss()
                    }
                    val objects = JSONObject(response)
                    val response_code = objects.getInt("success")
                    if (response_code == 1) {

//                        Toast.makeText(
//                            this,
//                            "Calculated Successfully",
//                            Toast.LENGTH_SHORT
//                        ).show()
                        val data = objects.getJSONArray("data")

                        for (i in 0 until data.length()) {
                            val actor: JSONObject = data.getJSONObject(i)
//                            val name = actor.getString("name")
                            allname.add(actor.getString("name"))
                            alluser.add(
                                Alluser(
                                    actor.getString("image"),
                                    actor.getString("lat"),
                                    actor.getString("lng"),
                                    actor.getString("lifestyle"),
                                    actor.getString("name"),
                                    actor.getString("account_type")
                                )
                            )
                        }

                        ArrayAdapter<String>(
                            this,
                            android.R.layout.simple_list_item_1,
                            allname
                        ).also { adapter ->
                            binding.autoCompleteTextView1.setAdapter(adapter)
                        }

                        binding.autoCompleteTextView1.onItemClickListener =
                            OnItemClickListener { parent, view, position, id ->
                                Log.d("TAG", binding.autoCompleteTextView1.text.toString())


                                for (item in alluser) {
                                    if (binding.autoCompleteTextView1.text.toString()
                                            .equals(item.name)
                                    ) {

                                        hideKeyboard(this)

                                        setdata(
                                            item.lifestyle,
                                            item.name,
                                            item.account_type,
                                            item.img,
                                            item.lat.toDouble(),
                                            item.lng.toDouble()
                                        )

                                        val sydney =
                                            LatLng(item.lat.toDouble(), item.lng.toDouble())
                                        mMap?.moveCamera(
                                            CameraUpdateFactory.newLatLngZoom(
                                                sydney,
                                                8f
                                            )
                                        )


                                    }
                                }


//                                val i = Intent(applicationContext, Dashboard::class.java)
//                                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK
//                                applicationContext.startActivity(i)
                            }

                        val marker =
                            arrayOfNulls<Marker>(alluser.size) //change length of array according to you

                        mMap!!.setOnMarkerClickListener(this)
                        for (i in 0 until alluser.size) {

                            // below line is use to add marker to each location of our array list.
                            Log.e(
                                "121212",
                                "allusers: ${alluser.get(i).lat.toDouble()}      ${alluser.get(i).lng.toDouble()}"
                            )
                            marker[i] = mMap?.addMarker(
//                            mMap?.addMarker(
                                MarkerOptions()
                                    .position(
                                        LatLng(
                                            alluser.get(i).lat.toDouble(),
                                            alluser.get(i).lng.toDouble()
                                        )
                                    )
//                                    .position(LatLng(31.5204 + i, 74.3587 + i))
                                    .icon(
                                        getMarkerBitmapFromView(
                                            alluser.get(i).img,
                                            alluser.get(i).lifestyle
                                        )?.let {
                                            BitmapDescriptorFactory.fromBitmap(
                                                it
                                            )
                                        })
                            )


                        }

                    } else {
                        Toast.makeText(
                            this,
                            "Error..!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } catch (e: JSONException) {
                    e.printStackTrace()
                }
                dialog.dismiss()
            },
            Response.ErrorListener { volleyError ->
                dialog.dismiss()
                var message: String? = null
                when (volleyError) {
                    is NetworkError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ServerError -> {
                        message =
                            "The server could not be found. Please try again after some time!!"
                    }
                    is AuthFailureError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is ParseError -> {
                        message = "Parsing error! Please try again after some time!!"
                    }
                    is NoConnectionError -> {
                        message = "Cannot connect to Internet...Please check your connection!"
                    }
                    is TimeoutError -> {
                        message = "Connection TimeOut! Please check your internet connection."
                    }
                }
                Toast.makeText(this@CalculateResultActivity, message, Toast.LENGTH_LONG).show()
            }) {
            override fun getParams(): MutableMap<String, String> {
                val header: MutableMap<String, String> = HashMap()

                header["do"] = "all_users"
                header["apikey"] = "dwamsoft12345"

                return header
            }

        }
        RegistrationRequest.retryPolicy = DefaultRetryPolicy(
            25000,
            DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )
        MySingleton.getInstance(this).addToRequestQueue(RegistrationRequest)
    }

    fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager =
            activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }


    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        p0.uiSettings
        // Add a marker in Sydney and move the camera
        // Add a marker in Sydney and move the camera
        val sydney = LatLng(Paper.book().read("latitude", 0.0), Paper.book().read("longitude", 0.0))
        allusers()


        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 8f))



        mMap?.mapType = GoogleMap.MAP_TYPE_NORMAL

        p0.uiSettings.setAllGesturesEnabled(true)
    }

    var bitma: Bitmap? = null

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getMarkerBitmapFromView(img: String, lifestyle: String): Bitmap? {

        try {
            val customMarkerView: View =
                (getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater).inflate(
                    R.layout.view_custom_marker,
                    null
                )
            val markerImageView: CircleImageView =
                customMarkerView.findViewById(R.id.profile_image) as CircleImageView
            val level: TextView = customMarkerView.findViewById(R.id.level) as TextView

            Log.e("1211112", "getMarkerBitmapFromView: ${BASE_URL + img}")


            val completableFuture: CompletableFuture<Any> = CompletableFuture()
            object : Thread() {
                @RequiresApi(Build.VERSION_CODES.N)
                override fun run() {

                    try {
                        if (img.isNotEmpty()) {


                        var futureBitmap: FutureTarget<Bitmap> = Glide.with(applicationContext)
                            .asBitmap().load(BASE_URL + img).submit()

                        bitma = futureBitmap.get()

//                        runOnUiThread {
//                            val imageView = ImageView(this@CalculateResultActivity)
//                            imageView.layoutParams =
//                                LinearLayout.LayoutParams(160, 160) // value is in pixels

                        if (bitma != null)
                            markerImageView.setImageBitmap(bitma)
                        else {
                            val imgResId = R.drawable.profile_picture
                            markerImageView.setImageResource(imgResId)
                        }
                        }else{
                            val imgResId = R.drawable.profile_picture
                            markerImageView.setImageResource(imgResId)
                        }
                        // Add ImageView to LinearLayout
//                            markerImageView.addView(imageView)


                        if (lifestyle.isNotEmpty()) {
                            var data = lifestyle.split(",")
                            var total_carbon = data[1].toFloat()

                            when (total_carbon) {
                                in 2.6..6.0 -> {
                                    pro_ranking = "1"
                                }
                                in 6.1..9.6 -> {
                                    pro_ranking = "2"
                                }
                                in 9.6..13.0 -> {
                                    pro_ranking = "3"
                                }
                                in 13.1..16.5 -> {
                                    pro_ranking = "4"
                                }
                                in 16.6..20.0 -> {
                                    pro_ranking = "5"
                                }
                                in 20.1..23.5 -> {
                                    pro_ranking = "6"
                                }
                                in 23.6..27.0 -> {
                                    pro_ranking = "7"
                                }
                            }


                        }


                        level.text = pro_ranking



                        customMarkerView.measure(
                            View.MeasureSpec.UNSPECIFIED,
                            View.MeasureSpec.UNSPECIFIED
                        )
                        customMarkerView.layout(
                            0,
                            0,
                            customMarkerView.measuredWidth,
                            customMarkerView.measuredHeight
                        )
                        customMarkerView.buildDrawingCache()
                        val returnedBitmap = Bitmap.createBitmap(
                            customMarkerView.measuredWidth, customMarkerView.measuredHeight,
                            Bitmap.Config.ARGB_8888
                        )
                        val canvas = Canvas(returnedBitmap)
                        canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
                        val drawable: Drawable = customMarkerView.background
                        if (drawable != null) drawable.draw(canvas)
                        customMarkerView.draw(canvas)
//                        return returnedBitmap


                        completableFuture.complete(returnedBitmap)

//                        }
//                        sleep(5000)
                    } catch (e: InterruptedException) {
                        e.printStackTrace()
                    }

                }
            }.start()
            val resultFromThread = completableFuture.get()


            return resultFromThread as Bitmap
//            if (lifestyle.isNotEmpty()) {
//                var data = lifestyle.split(",")
//                var total_carbon = data[1].toFloat()
//
//                when (total_carbon) {
//                    in 2.6..6.0 -> {
//                        pro_ranking = "1"
//                    }
//                    in 6.1..9.6 -> {
//                        pro_ranking = "2"
//                    }
//                    in 9.6..13.0 -> {
//                        pro_ranking = "3"
//                    }
//                    in 13.1..16.5 -> {
//                        pro_ranking = "4"
//                    }
//                    in 16.6..20.0 -> {
//                        pro_ranking = "5"
//                    }
//                    in 20.1..23.5 -> {
//                        pro_ranking = "6"
//                    }
//                    in 23.6..27.0 -> {
//                        pro_ranking = "7"
//                    }
//                }
//
//
//            }
//
//
//            level.text = pro_ranking
//
//
//
//            customMarkerView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED)
//            customMarkerView.layout(
//                0,
//                0,
//                customMarkerView.measuredWidth,
//                customMarkerView.measuredHeight
//            )
//            customMarkerView.buildDrawingCache()
//            val returnedBitmap = Bitmap.createBitmap(
//                customMarkerView.measuredWidth, customMarkerView.measuredHeight,
//                Bitmap.Config.ARGB_8888
//            )
//            val canvas = Canvas(returnedBitmap)
//            canvas.drawColor(Color.WHITE, PorterDuff.Mode.SRC_IN)
//            val drawable: Drawable = customMarkerView.background
//            if (drawable != null) drawable.draw(canvas)
//            customMarkerView.draw(canvas)
//            return returnedBitmap
        } catch (e: Exception) {
            Log.e("bitmapp", "getMarkerBitmapFromView: ${e.message}")
            return null
        }


    }

    override fun onMarkerClick(marker: Marker): Boolean {
        Log.e("12121211", "onMarkerClick: ${marker.id}")

        val id = marker.id.drop(1)

        var item = alluser[id.toInt()]
        setdata(
            item.lifestyle,
            item.name,
            item.account_type,
            item.img,
            item.lat.toDouble(),
            item.lng.toDouble()
        )

        val sydney = LatLng(item.lat.toDouble(), item.lng.toDouble())
        mMap?.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 8f))

        return true
    }
}