package com.machineries_pk.mrk.activities.Module2.Adapters

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.Module2.Model.ChooseModel

class ChooseActionAdapter(
    private val activity: Activity,
    private val list: ArrayList<ChooseModel>

) : RecyclerView.Adapter<ChooseActionAdapter.Holder>() {

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var c_img: ImageView = itemView.findViewById(R.id.c_img)
        var check: ImageView = itemView.findViewById(R.id.check)
        var c_txt: TextView = itemView.findViewById(R.id.c_txt)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(parent.context).inflate(R.layout.choose_item, parent, false)
        )
    }
    override fun onBindViewHolder(holder: ChooseActionAdapter.Holder, position: Int) {


        try {
            if (position<list.size){


                holder.c_img.setImageResource(list[position].img)
                if (list[position].check){
                    holder.check.setImageResource(R.drawable.choose_check)
                }else
                    holder.check.setImageResource(R.drawable.choose_uncheck)


                holder.check.setOnClickListener {
                    if (list[position].check){
                        list[position].check = false
                        holder.check.setImageResource(R.drawable.choose_uncheck)
                    }else{
                        list[position].check = true
                        holder.check.setImageResource(R.drawable.choose_check)
                    }
                }


            }

        }catch (e: OutOfMemoryError)
        {
            Toast.makeText(activity, "Not Enough Memory", Toast.LENGTH_SHORT).show()
        }
    }

    override fun getItemCount() = list.size


}