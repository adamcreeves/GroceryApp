package com.example.glossaryapp.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R
import com.example.glossaryapp.models.Data
import kotlinx.android.synthetic.main.row_adapter_orders_placed.view.*

class AdapterOrdersPlaced(
    private var myContext: Context,
    private var myList: ArrayList<Data>
) :
    RecyclerView.Adapter<AdapterOrdersPlaced.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var view = LayoutInflater.from(myContext).inflate(R.layout.row_adapter_orders_placed, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var data = myList[position]
        holder.bind(data, position)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list: ArrayList<Data>){
        myList = list
        notifyDataSetChanged()
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(data: Data, position: Int) {
            itemView.text_view_orders_user.text = data.user.name
            itemView.text_view_orders_address.text = data.shippingAddress.toString()
            itemView.text_view_orders_products.text = data.products.toString()
            itemView.text_view_orders_payment.text = data.payment.toString()
        }
    }

}