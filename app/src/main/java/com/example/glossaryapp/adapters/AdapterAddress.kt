package com.example.glossaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.PaymentActivity
import com.example.glossaryapp.helpers.SessionManager
import com.example.glossaryapp.models.Address
import com.example.glossaryapp.models.AddressResult
import kotlinx.android.synthetic.main.row_adapter_addresses.view.*
import org.json.JSONObject

class AdapterAddress(
    private var myContext: Context,
    private var myList: ArrayList<Address>
) :
    RecyclerView.Adapter<AdapterAddress.myViewHolder>() {
    private lateinit var sessionManager: SessionManager

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterAddress.myViewHolder {
        var view =
            LayoutInflater.from(myContext).inflate(R.layout.row_adapter_addresses, parent, false)
        sessionManager = SessionManager(myContext)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: AdapterAddress.myViewHolder, position: Int) {
        var addressData = myList[position]
        holder.bind(addressData, position)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list: ArrayList<Address>) {
        notifyDataSetChanged()
        myList = list
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(addressData: Address, position: Int) {
            itemView.text_view_address_house_no.text = addressData.houseNo
            itemView.text_view_address_street_name.text = addressData.streetName
            itemView.text_view_address_city.text = addressData.city
            itemView.text_view_address_pincode.text = addressData.pincode.toString()
            itemView.text_view_address_type.text = addressData.type
            itemView.button_address_delete.setOnClickListener{
                var _id = sessionManager.getAddressId()
            }
        }
    }

}