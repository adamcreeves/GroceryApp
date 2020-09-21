package com.example.glossaryapp.models

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
){
    companion object{
        const val KEY_CAT_ID = "catId"
    }
}




