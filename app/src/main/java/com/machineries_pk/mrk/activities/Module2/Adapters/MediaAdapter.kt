package com.machineries_pk.mrk.activities.Module2.Adapters

import android.app.Activity
import android.content.Intent
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.machineries_pk.mrk.R
import com.machineries_pk.mrk.activities.Module2.Model.MediaModel
import java.io.File

class MediaAdapter (
    private val activity: Activity,
    private val list: ArrayList<MediaModel>,

) : RecyclerView.Adapter<MediaAdapter.Holder>() {

    class Holder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var heart: ImageView = itemView.findViewById(R.id.heart)
        var img: ImageView = itemView.findViewById(R.id.cho_img)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(LayoutInflater.from(parent.context).inflate(R.layout.my_choice_item,parent,false))
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {


        try {
            if (position<list.size){


                holder.img.setImageResource(list[position].img)
                if (list[position].fav){
                    holder.heart.setImageResource(R.drawable.heart_filled)
                }else
                    holder.heart.setImageResource(R.drawable.heart_unfilled)


                holder.heart.setOnClickListener {
                    if (list[position].fav){
                        list[position].fav = false
                        holder.heart.setImageResource(R.drawable.heart_unfilled)
                    }else{
                        list[position].fav = true
                        holder.heart.setImageResource(R.drawable.heart_filled)
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