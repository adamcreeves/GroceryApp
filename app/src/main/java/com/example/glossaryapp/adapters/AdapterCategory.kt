package com.example.glossaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.SubCategoryActivity
import com.example.glossaryapp.models.CategoryDataItem
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterCategory(var myContext: Context, var myList: ArrayList<CategoryDataItem>) : RecyclerView.Adapter<AdapterCategory.myViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myViewHolder {
        var view = LayoutInflater.from(myContext).inflate(R.layout.row_category_adapter, parent, false)
        return myViewHolder(view)
    }

    override fun onBindViewHolder(holder: myViewHolder, position: Int) {
        var category = myList[position]
        holder.bind(category)
    }

    override fun getItemCount(): Int {
        return myList.size
    }

    fun setData(list: ArrayList<CategoryDataItem>){
        myList = list
        notifyDataSetChanged()
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: CategoryDataItem) {

            var imageUrl = "http://rjtmobile.com/grocery/images/"
            Picasso.get()
                .load(imageUrl + category.catImage)
                .resize(250,250)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_didnt_load)
                .into(itemView.image_view_home_categories)


            itemView.setOnClickListener{
                var intent = Intent(myContext, SubCategoryActivity::class.java)
//                intent.putExtra("CAT_ID", category.catId)
//                intent.putExtra("_ID", category._id)
//                intent.putExtra("CAT_NAME", category.catName)
//                intent.putExtra("SLUG", category.slug)
//                intent.putExtra("CAT_IMAGE", category.catImage)
                myContext.startActivity(intent)
            }
        }
    }


}