package com.example.glossaryapp.models

data class OrdersResults(
    val count: Int,
    val `data`: List<Data>,
    val error: Boolean
)



data class OrderSummary2(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class Payment2(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
)

data class Product2(
    val _id: String,
    val image: String,
    val mrp: Double,
    val price: Double,
    val quantity: Int
)

data class ShippingAddress2(
    val city: String,
    val houseNo: String,
    val pincode: Int,
    val streetName: String
)

data class User2(
    val _id: String,
    val email: String,
    val mobile: String,
    val name: String
)