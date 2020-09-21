package com.example.glossaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.ProductDetailActivity
import com.example.glossaryapp.models.CategoryDataItem
import com.example.glossaryapp.models.ProductDataItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_subcategory_adapter.view.*

class AdapterProducts(private var myContext: Context, private var myList: ArrayList<ProductDataItem>) : RecyclerView.Adapter<AdapterProducts.myViewHolder>() {

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(products: ProductDataItem) {
            itemView.text_view_product_name.text = products.productName
            itemView.text_view_price.text = products.price.toString()
            var imageUrl = "https://rjtmobile.com/grocery/images/"
            Picasso.get()
                .load(imageUrl + products.image)
                .resize(100,100)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_didnt_load)
                .into(itemView.image_view_subcategory_product)
            itemView.setOnClickListener{
                var intent = Intent(myContext, ProductDetailActivity::class.java)
                intent.putExtra("PRODUCT_NAME", products.productName)
                intent.putExtra("PRODUCT_PRICE", products.price.toString())
                intent.putExtra("IMAGE_URL", imageUrl)
                myContext.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var view = LayoutInflater.from(myContext).inflate(R.layout.row_subcategory_adapter, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var products: ProductDataItem = myList[position]
        holder.bind(products)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list: ArrayList<ProductDataItem>){
        myList = list
        notifyDataSetChanged()
    }
}