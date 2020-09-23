package com.example.glossaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.ShoppingCartActivity
import com.example.glossaryapp.app.Configure
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_adapter_shopping_cart.view.*
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class AdapterShoppingCart (private var myContext: Context, private var myList: ArrayList<Product>) :
    RecyclerView.Adapter<AdapterShoppingCart.myViewHolder>() {

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(product: Product, position: Int) {
            itemView.text_view_cart_product_name.text = product.productName
            itemView.text_view_cart_price.text = product.price.toString()
            Picasso.get()
                .load(Configure.IMAGE_URL + product.image)
                .resize(100,100)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_didnt_load)
                .into(itemView.image_view_cart_image)
            itemView.button_cart_delete.setOnClickListener {
                var dbHelper = DBHelper(myContext)
                dbHelper.deleteProduct(product._id)
                myList.removeAt(position)
                myContext.startActivity(Intent(myContext, ShoppingCartActivity::class.java))
            }
        }
    }

    fun setData(list: ArrayList<Product>) {
        myList = list
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var view = LayoutInflater.from(myContext).inflate(R.layout.row_adapter_shopping_cart, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var product: Product = myList[position]
        holder.bind(product, position)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

}