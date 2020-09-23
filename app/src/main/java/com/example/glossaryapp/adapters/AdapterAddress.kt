package com.example.glossaryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R

class AdapterAddress(
    private var myContext: Context,
//    private var myList: ArrayList<NewAddress>
) :
    RecyclerView.Adapter<AdapterShoppingCart.myViewHolder>() {


    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterShoppingCart.myViewHolder {
        TODO("Not yet implemented")
//        var view = LayoutInflater.from(myContext).inflate(R.layout.)
    }

    override fun onBindViewHolder(holder: AdapterShoppingCart.myViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind() {

        }
    }

}