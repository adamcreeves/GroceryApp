package com.example.glossaryapp.models

import java.io.Serializable

data class ProductResults(
    val count: Int,
    val data: ArrayList<Product>,
    val error: Boolean
)

data class Product(
    val _id: String,
    val catId: Int?,
    val description: String,
    val image: String,
    val mrp: Double?,
    val position: Int?,
    val price: Double,
    val productName: String,
    val quantity: Int?,
    val status: Boolean?,
    val subId: Int?,
    val unit: String?
) : Serializable {
    companion object{
        const val KEY_PRODUCT = "product"
    }
}