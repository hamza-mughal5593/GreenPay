package com.machineries_pk.mrk.activities

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.ProgressDialog
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Rect
import android.net.Uri
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.List.EmployeeModel
import com.machineries_pk.mrk.activities.List.EmployeeRecyclerAdapter
import com.machineries_pk.mrk.databinding.ActivityListBinding
import io.paperdb.Paper
import android.text.Editable

import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.PixelCopy
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.GridLayoutManager
import com.google.android.material.navigation.NavigationView
import com.machineries_pk.mrk.activities.List.EmployeeRecyclerAdapter.OnShareClickedListener
import java.io.*
import java.lang.Exception


import android.os.Environment
import jxl.Workbook
import jxl.WorkbookSettings
import jxl.write.Label
import jxl.write.WritableSheet
import jxl.write.WritableWorkbook
import java.util.*
import kotlin.collections.ArrayList


class ListActivity : AppCompatActivity() {

    //    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager

    private lateinit var binding: ActivityListBinding


    var reviewAdapter: EmployeeRecyclerAdapter? = null
    var itemlist: ArrayList<EmployeeModel>? = ArrayList()
    var workbook: WritableWorkbook? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)




        val drawer = findViewById<DrawerLayout>(R.id.drawer_layout)
        val navigationView = findViewById<NavigationView>(R.id.nav_view)


        findViewById<View>(R.id.drawer_btn).setOnClickListener {
            if (drawer.isDrawerOpen(navigationView)) {
                drawer.closeDrawer(navigationView)
            } else {
                drawer.openDrawer(navigationView)
            }
        }
        navigationView.setNavigationItemSelectedListener(NavigationView.OnNavigationItemSelectedListener { item ->
            val id = item.itemId
            if (id == R.id.remove_nav) {


                createExcelSheet()

//                val task = ExportDatabaseCSVTask(this@ListActivity)
//                task.execute()
            } else if (id == R.id.nav_share) {
                try {
                    val shareIntent = Intent(Intent.ACTION_SEND)
                    shareIntent.type = "text/plain"
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "QR Code Scanner")
                    var shareMessage = "\nLet me recommend you this application\n\n"
                    shareMessage =
                        """
                        ${shareMessage}https://play.google.com/store/apps/details?id=com.greenpay.carbon.footprints.calculator
                        
                        
                        """.trimIndent()
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage)
                    startActivity(Intent.createChooser(shareIntent, "choose one"))
                } catch (e: Exception) {
                    //e.toString();
                }
            } else if (id == R.id.nav_rate) {
                val uri = Uri.parse("market://details?id=" + applicationContext.packageName)
                val rateAppIntent = Intent(Intent.ACTION_VIEW, uri)
                if (packageManager.queryIntentActivities(rateAppIntent, 0).size > 0) {
                    startActivity(rateAppIntent)
                } else {
                    /* handle your error case: the device has no way to handle market urls */
                }
            } else if (id == R.id.nav_priv) {
                val url2 =
                    "https://sites.google.com/view/greenpay-privacypolicy/home"
                val i2 = Intent(Intent.ACTION_VIEW)
                i2.data = Uri.parse(url2)
                startActivity(i2)
            }
            if (drawer.isDrawerOpen(navigationView)) {
                drawer.closeDrawer(navigationView)
            }
            true
        })





        binding.fabBtn.setOnClickListener {

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


                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }


        }









        binding.searchData.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {}
            override fun afterTextChanged(s: Editable) {
//                filter(s.toString());
                reviewAdapter?.filter(s.toString())
            }
        })

    }

    override fun onResume() {
        super.onResume()
        itemlist = ArrayList()
        itemlist = Paper.book().read("itemlist", itemlist)
        if (itemlist == null) {
            itemlist = ArrayList()
            binding.listEmpty.visibility = View.VISIBLE
        } else
            binding.listEmpty.visibility = View.GONE

        reviewAdapter = EmployeeRecyclerAdapter(this, itemlist!!, object :
            OnShareClickedListener {
            override fun ShareClicked(url: View?, parent: View?, position: Int) {
                showMenu(url!!, parent, position)
//Toast.makeText(this@ListActivity,"1",Toast.LENGTH_SHORT).show()
            }

            override fun OnShareClickedListener() {
                Toast.makeText(this@ListActivity, "2", Toast.LENGTH_SHORT).show()
            }


        })
        val layoutManager: RecyclerView.LayoutManager =
            GridLayoutManager(applicationContext, 1, RecyclerView.VERTICAL, false)
        binding.recyclerview.layoutManager = layoutManager
        binding.recyclerview.adapter = reviewAdapter


    }


//    class ExportDatabaseCSVTask(mainActivity: Activity) : AsyncTask<String?, String?, String>() {
//        private val dialog = ProgressDialog(mainActivity)
//
//        @SuppressLint("StaticFieldLeak")
//        private val main: Activity = mainActivity
//
//        override fun onPreExecute() {
//            dialog.setMessage("Exporting database...")
//            dialog.show()
//        }
//
//
//        @SuppressLint("NewApi")
//        override fun onPostExecute(success: String) {
//            if (dialog.isShowing) {
//                dialog.dismiss()
//            }
//            if (success.isEmpty()) {
//                Toast.makeText(main, "Export successful!", Toast.LENGTH_SHORT).show()
//            } else {
//                Toast.makeText(main, "Export failed!", Toast.LENGTH_SHORT).show()
//            }
//        }
//
//        override fun doInBackground(vararg params: String?): String {
//            val exportDir = File(
//                Environment.getExternalStorageDirectory(),
//                "Documents/Greenpay Carbon Calculator"
//            )
//            if (!exportDir.exists()) {
//                exportDir.mkdirs()
//            }
//            val file = File(exportDir, "Demo.xls")
////            return try {
//
//
//            val hssfWorkbook = HSSFWorkbook()
//            val hssfSheet = hssfWorkbook.createSheet("Custom Sheet")
//            val hssfRow = hssfSheet.createRow(0)
//
//            val hssfCell = hssfRow.createCell(0)
//            val hssfCell1 = hssfRow.createCell(1)
//            val hssfCell2 = hssfRow.createCell(2)
//            val hssfCell3 = hssfRow.createCell(3)
//            val hssfCell4 = hssfRow.createCell(4)
//            hssfCell.setCellValue("Sr No.")
//            hssfCell1.setCellValue("Name")
//            hssfCell2.setCellValue("Email")
//            hssfCell3.setCellValue("Country")
//            hssfCell4.setCellValue("Carbon Emissions")
//            try {
//
//                val fileOutputStream = FileOutputStream(file)
//                hssfWorkbook.write(fileOutputStream)
//                if (fileOutputStream != null) {
//                    fileOutputStream.flush()
//                    fileOutputStream.close()
//                }
//            } catch (e: Exception) {
//                e.printStackTrace()
//            }
//
//return ""
////                file.createNewFile()
////                val csvWrite = CSVWriter(FileWriter(file))
////
////                //data
////                val listdata = ArrayList<String>()
////                listdata.add("Aniket")
////                listdata.add("Shinde")
////                listdata.add("pune")
////                listdata.add("anything@anything")
////                //Headers
////                val arrStr1 = arrayOf("First Name", "Last Name", "Address", "Email")
////                csvWrite.writeNext(*arrStr1)
////                val arrStr = arrayOf(
////                    listdata[0],
////                    listdata[1], listdata[2], listdata[3]
////                )
////                csvWrite.writeNext(*arrStr)
////                csvWrite.close()
////                ""
////            } catch (e: IOException) {
////                Log.e("MainActivity", e.message, e)
////                ""
////            }
//        }
//    }

    private fun showMenu(view: View, parent: View?, position: Int) {
        val popupMenu = PopupMenu(this, view) //View will be an anchor for PopupMenu
        popupMenu.inflate(R.menu.menu)
        val menu: Menu = popupMenu.getMenu()
        popupMenu.setOnMenuItemClickListener(object : MenuItem.OnMenuItemClickListener,
            PopupMenu.OnMenuItemClickListener, (Bitmap) -> Unit {
            @RequiresApi(Build.VERSION_CODES.O)
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                if (item?.itemId == R.id.delete) {
                    removeItem(position)
                } else if (item?.itemId == R.id.make_card) {
                    val intent = Intent(this@ListActivity, GreenCardActivity::class.java)
                    startActivity(intent)
                }
                return true
            }

            override fun invoke(p1: Bitmap) {
            }

        })
        popupMenu.show()
    }

    private fun removeItem(position: Int) {
        itemlist?.removeAt(position)
        reviewAdapter?.notifyItemRemoved(position)
        reviewAdapter?.notifyItemRangeChanged(position, itemlist?.size!!)
    }

    fun createExcelSheet() {
        //File futureStudioIconFile = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
//        val csvFile = "Excelsheet.xls"
//        val futureStudioIconFile = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).toString() + "/" + csvFile)

        val exportDir = File(
            Environment.getExternalStorageDirectory(),
            "Documents/Greenpay Carbon Calculator"
        )
        if (!exportDir.exists()) {
            exportDir.mkdirs()
        }
        val file = File(exportDir, "Greenpay calculations sheet.xls")

        val wbSettings = WorkbookSettings()
        wbSettings.locale = Locale("en", "EN")
        try {
            workbook = Workbook.createWorkbook(file, wbSettings)
            createFirstSheet()
            //            createSecondSheet();
            //closing cursor
            workbook?.write()
            workbook?.close()
            Toast.makeText(this,"Successfully Saved",Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun createFirstSheet() {
        try {
//            val listdata: MutableList<Bean> = ArrayList()
//            listdata.add(Bean("mr", "firstName1", "middleName1", "lastName1"))
//            listdata.add(Bean("mr", "firstName1", "middleName1", "lastName1"))
//            listdata.add(Bean("mr", "firstName1", "middleName1", "lastName1"))
            //Excel sheet name. 0 (number)represents first sheet
            val sheet: WritableSheet = workbook!!.createSheet("sheet1", 0)
            // column and row title
            sheet.addCell(Label(0, 0, "Sr No."))
            sheet.addCell(Label(1, 0, "Name"))
            sheet.addCell(Label(2, 0, "Email"))
            sheet.addCell(Label(3, 0, "Country"))
            sheet.addCell(Label(4, 0, "Carbon Emissions"))
            for (i in itemlist!!.indices) {

                val sr = i+1

                sheet.addCell(Label(0, i + 1, sr.toString()))
                sheet.addCell(Label(1, i + 1, itemlist?.get(i)?.name))
                sheet.addCell(Label(2, i + 1, itemlist?.get(i)?.email))
                sheet.addCell(Label(3, i + 1, itemlist?.get(i)?.phone))
                sheet.addCell(Label(4, i + 1, itemlist?.get(i)?.data))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
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