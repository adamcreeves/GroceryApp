package com.example.glossaryapp.models

data class SubCategoriesResult(
    val count: Int,
    val data: ArrayList<SubCategory>,
    val error: Boolean
)

data class SubCategory(
    val _id: String,
    val catId: Int,
    val position: Int,
    val status: Boolean,
    val subDescription: String,
    val subId: Int,
    val subImage: String,
    val subName: String
){
    companion object{

    }
}