package com.example.glossaryapp.models

import java.io.Serializable

data class CategoryResult(
    var error: Boolean? = null,
    var count: Int?,
    var data: ArrayList<Category>
)



data class Category(
    val __v: Int,
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean
) : Serializable {
    companion object{
        const val KEY_CATEGORY = "category"
    }
}




