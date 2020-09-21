package com.example.glossaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.ProductDetailActivity
import com.example.glossaryapp.app.Configure
import com.example.glossaryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_subcategory_adapter.view.*

class AdapterProducts(var myContext: Context) :
    RecyclerView.Adapter<AdapterProducts.myViewHolder>() {

    var myList: ArrayList<Product> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var view =
            LayoutInflater.from(myContext).inflate(R.layout.row_subcategory_adapter, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var product = myList[position]
        holder.bind(product)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list: ArrayList<Product>) {
        myList = list
        notifyDataSetChanged()
    }


    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product) {
            itemView.text_view_product_name.text = product.productName
            itemView.text_view_price.text = product.price.toString()
            Picasso.get()
                .load(Configure.IMAGE_URL + product.image)
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_didnt_load)
                .into(itemView.image_view_subcategory_product)
            itemView.setOnClickListener {
                var intent = Intent(myContext, ProductDetailActivity::class.java)
                intent.putExtra(Product.KEY_PRODUCT, product)
                myContext.startActivity(intent)
            }
        }
    }
}