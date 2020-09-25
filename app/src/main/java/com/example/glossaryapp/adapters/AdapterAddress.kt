package com.example.glossaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.PaymentActivity
import com.example.glossaryapp.models.Address
import kotlinx.android.synthetic.main.row_adapter_addresses.view.*

class AdapterAddress(
    private var myContext: Context,
    private var myList: ArrayList<Address>
) :
    RecyclerView.Adapter<AdapterAddress.myViewHolder>() {

    private var mySelectedItem = -1

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterAddress.myViewHolder {
        var view =
            LayoutInflater.from(myContext).inflate(R.layout.row_adapter_addresses, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterAddress.myViewHolder, position: Int) {
        var addressData = myList[position]
        holder.bind(addressData, position, mySelectedItem)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list: ArrayList<Address>) {
        notifyDataSetChanged()
        myList = list
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(addressData: Address, position: Int, mySelectedItem: Int) {
            var selected = true
            itemView.text_view_address_house_no.text = addressData.houseNo
            itemView.text_view_address_street_name.text = addressData.streetName
            itemView.text_view_address_city.text = addressData.city
            itemView.text_view_address_pincode.text = addressData.pincode.toString()
//            if(mySelectedItem == -1 && position == 0)
            itemView.text_view_address_type.text = addressData.type
            itemView.radio_button_select_address.setOnClickListener {
                if (selected) {
                    itemView.text_view_radio_button_checked.visibility = View.VISIBLE
                    var intent = Intent(myContext, PaymentActivity::class.java)
                    intent.putExtra(Address.KEY_ADDRESS, addressData)
                    selected = false
                } else {
                    itemView.text_view_radio_button_checked.visibility = View.INVISIBLE
                    var intent = Intent(myContext, PaymentActivity::class.java)
                    selected = true
                }
            }
        }
    }

}