package com.example.glossaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.ProductDetailActivity
import com.example.glossaryapp.models.Product
import com.example.glossaryapp.models.ProductData
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_subcategory_adapter.view.*


class AdapterSubCategory(var myContext: Context, var myList: ArrayList<ProductData>) :
    RecyclerView.Adapter<AdapterSubCategory.myViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var view = LayoutInflater.from(myContext).inflate(R.layout.row_subcategory_adapter, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var productData = myList[position]
        holder.bind(productData)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list: ArrayList<ProductData>) {
        myList = list
        notifyDataSetChanged()
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(productData: ProductData) {

            itemView.text_view_product_name.text = productData.productName
            itemView.text_view_price.text = productData.price.toString()
            var imageUrl = "http://rjtmobile.com/grocery/images/"
            Picasso.get()
                .load(imageUrl + productData.image)
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_didnt_load)
                .into(itemView.image_view_subcategory_product)
            itemView.setOnClickListener {
                var intent = Intent(myContext, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT_NAME", productData.productName)
                intent.putExtra("PRICE", productData.price)
                intent.putExtra("IMAGE", productData.image)
                myContext.startActivity(intent)
            }
        }

    }
}
