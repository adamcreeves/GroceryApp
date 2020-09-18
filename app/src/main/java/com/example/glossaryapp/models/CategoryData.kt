package com.example.glossaryapp.models

data class CategoryResult(
    var error: Boolean? = null,
    var count: Int?,
    var data: List<CategoryDataItem>
)



data class CategoryDataItem(
    val __v: Int,
    val _id: String,
    val catDescription: String,
    val catId: Int,
    val catImage: String,
    val catName: String,
    val position: Int,
    val slug: String,
    val status: Boolean
)