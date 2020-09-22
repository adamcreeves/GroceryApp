package com.example.glossaryapp.models

import java.io.Serializable

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
) : Serializable {
    companion object{
        const val KEY_TO_SUB_ID = "subId"
        const val KEY_SUBCATEGORY = "subCategory"
    }
}