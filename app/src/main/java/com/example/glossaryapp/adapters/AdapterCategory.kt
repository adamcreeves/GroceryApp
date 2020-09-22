package com.example.glossaryapp.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.SubCategoryActivity
import com.example.glossaryapp.app.Configure
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.models.Category
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.row_category_adapter.view.*
import java.util.*
import kotlin.collections.ArrayList

class AdapterCategory(var myContext: Context) : RecyclerView.Adapter<AdapterCategory.myViewHolder>() {

    var myList: ArrayList<Category> = ArrayList()

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

    fun setData(list: ArrayList<Category>){
        myList = list
        notifyDataSetChanged()
    }

    inner class myViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(category: Category) {
            Picasso.get()
                .load(Configure.IMAGE_URL + category.catImage)
                .resize(200,200)
                .centerCrop()
                .placeholder(R.drawable.image_loading)
                .error(R.drawable.image_didnt_load)
                .into(itemView.image_view_home_categories)
            itemView.text_view_category_name.text = category.catName

            itemView.setOnClickListener{
                var intent = Intent(myContext, SubCategoryActivity::class.java)
                intent.putExtra(Category.KEY_CATEGORY, category)
                myContext.startActivity(intent)
            }
        }
    }


}