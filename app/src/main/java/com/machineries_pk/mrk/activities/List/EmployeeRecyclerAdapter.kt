package com.machineries_pk.mrk.activities.List

import android.content.Context
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.machineries_pk.mrk.R
import kotlin.collections.ArrayList

import android.view.*
import io.paperdb.Paper
import java.util.*

val list2: ArrayList<EmployeeModel> = ArrayList()
class EmployeeRecyclerAdapter (

    private val context: Context,
    private val list: ArrayList<EmployeeModel>, private   var mCallback:OnShareClickedListener

) : RecyclerView.Adapter<EmployeeRecyclerAdapter.ViewHolder>() {


    init {


        list2.addAll(list)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView = itemView.findViewById(R.id.name)
        var email: TextView = itemView.findViewById(R.id.email)
        var phone: TextView = itemView.findViewById(R.id.phone)
        var data: TextView = itemView.findViewById(R.id.data)
        var menu_btn: ImageView = itemView.findViewById(R.id.menu_btn)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent , false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = list[position].name
        holder.phone.text = list[position].phone
        holder.email.text = list[position].email
        holder.data.text = list[position].data

        holder.menu_btn.setOnClickListener {
//            val intent = Intent(context, Home::class.java)
//            val stream = ByteArrayOutputStream()
//            list[position].image.compress(Bitmap.CompressFormat.PNG, 100, stream)
//            val byteArray = stream.toByteArray()
////            intent.putExtra("image",byteArray)
////            intent.putExtra("name",list[position].name)
////            intent.putExtra("role",list[position].role)
//            context.startActivity(intent)

            mCallback.ShareClicked(it,holder.itemView,position)
//            showMenu(it)

             Paper.book().write("name", list[position].name)
            Paper.book().write("email",list[position].email)
             Paper.book().write("phone",list[position].phone)
             Paper.book().write("final_data", list[position].data)

        }

    }
    interface OnShareClickedListener {
        fun ShareClicked(url: View?, parent: View?, position: Int)
        fun OnShareClickedListener()
    }
    override fun getItemCount() = list.size


    public fun filter(charText: String) {
        var charText = charText
        charText = charText.toLowerCase(Locale.getDefault())
        list.clear()
        if (charText.length == 0) {
            list.addAll(list2)
        } else {
            for (wp in list2) {
                if (wp.name.toLowerCase(Locale.getDefault()).contains(charText)) {
                    list.add(wp)
                }
            }
        }
        notifyDataSetChanged()
    }



}