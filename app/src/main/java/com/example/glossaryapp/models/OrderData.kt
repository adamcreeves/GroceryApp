package com.example.glossaryapp.models

import java.io.Serializable

data class OrderSummary(
    var deliveryCharges: Int,
    var discount: Int,
    var orderAmount: Int,
    var ourPrice: Int,
    var totalAmount: Int
): Serializable

data class Payment(
    var paymentMode: String,
    var paymentStatus: String
): Serializable

data class PaymentProduct(
    var image: String,
    var mrp: Double,
    var price: Double,
    var quantity: Int
): Serializable

data class ShippingAddress(
    var city: String,
    var houseNo: String,
    var pincode: Int,
    var streetName: String
): Serializable

data class PaymentUser(
    var email: String,
    var mobile: String,
    var name: String
): Serializable

data class Data(
    val __v: Int? = null,
    val _id: String? = null,
    val date: String? = null,
    val orderStatus: String,
    val orderSummary: OrderSummary,
    val payment: Payment,
    val products: List<PaymentProduct>,
    val shippingAddress: ShippingAddress,
    val user: PaymentUser,
    val userId: String
): Serializable {
    companion object{
        const val KEY_PAYMENT = "payment_object"
    }
}
