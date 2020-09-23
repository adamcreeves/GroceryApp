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
import com.example.glossaryapp.models.CartProductData
import com.example.glossaryapp.models.Product
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_adapter_shopping_cart.view.*
import kotlinx.android.synthetic.main.row_category_adapter.view.*

class AdapterShoppingCart(
    private var myContext: Context,
    private var myList: ArrayList<CartProductData>
) :
    RecyclerView.Adapter<AdapterShoppingCart.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var view = LayoutInflater.from(myContext)
            .inflate(R.layout.row_adapter_shopping_cart, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var cartProductData = myList[position]
        holder.bind(cartProductData, position)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list: ArrayList<CartProductData>) {
        myList = list
        notifyDataSetChanged()
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(cartProductData: CartProductData, position: Int) {
            itemView.text_view_cart_product_name.text = cartProductData.productName
            itemView.text_view_cart_price.text = cartProductData.price.toString()
            itemView.text_view_cart_quantity.text = cartProductData.quantity.toString()
            Picasso.get()
                .load(cartProductData.image)
                .resize(100, 100)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_didnt_load)
                .into(itemView.image_view_cart_image)
            itemView.button_cart_delete.setOnClickListener {
                var dbHelper = DBHelper(myContext)
                dbHelper.deleteProduct(cartProductData.id)
                myList.removeAt(position)
                myContext.startActivity(Intent(myContext, ShoppingCartActivity::class.java))
            }
            itemView.button_cart_plus_one.setOnClickListener {
                var dbHelper = DBHelper(myContext)
                dbHelper.updatePlusProduct(cartProductData)
                itemView.text_view_cart_quantity.text = cartProductData.quantity.toString()
                myContext.startActivity(Intent(myContext, ShoppingCartActivity::class.java))
            }
            itemView.button_cart_minus_one.setOnClickListener {
                var dbHelper = DBHelper(myContext)
                if (cartProductData.quantity == 1) {
                    dbHelper.deleteProduct(cartProductData.id)
                    myContext.startActivity(Intent(myContext, ShoppingCartActivity::class.java))
                } else {
                    dbHelper.updateMinusProduct(cartProductData)
                    itemView.text_view_cart_quantity.text = cartProductData.quantity.toString()
                    myContext.startActivity(Intent(myContext, ShoppingCartActivity::class.java))
                }
            }
        }
    }


}