package com.example.glossaryapp.models

data class OrderData(
    val __v: Int,
    val _id: String,
    val date: String,
    val orderStatus: String,
    val orderSummary: OrderSummary,
    val payment: Payment,
    val products: List<Product>,
    val shippingAddress: Address,
    val user: User,
    val userId: String
)

data class OrderSummary(
    val _id: String,
    val deliveryCharges: Int,
    val discount: Int,
    val orderAmount: Int,
    val ourPrice: Int,
    val totalAmount: Int
)

data class Payment(
    val _id: String,
    val paymentMode: String,
    val paymentStatus: String
)

