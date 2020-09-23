package com.example.glossaryapp.models

class CartProductData(
    var id: String,
    var quantity: Int = 0,
    var mrp: Double,
    var productName: String,
    var price: Double,
    var image: String
)