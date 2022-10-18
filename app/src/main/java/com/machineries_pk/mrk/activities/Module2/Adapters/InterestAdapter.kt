package com.machineries_pk.mrk.activities.Module2.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.machineries_pk.mrk.R

class InterestAdapter : RecyclerView.Adapter<MyViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listitem = layoutInflater.inflate(R.layout.interestlayout,parent,false)
        return MyViewHolder(listitem)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
return 5
    }

}

class MyViewHolder(view: View):RecyclerView.ViewHolder(view){

    val bg = view.findViewById(R.id.)

}