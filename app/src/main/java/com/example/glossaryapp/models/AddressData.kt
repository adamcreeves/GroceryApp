package com.example.glossaryapp.models

import java.io.Serializable

data class AddressResult(
    val error: Boolean,
    val count: Int,
    val data: ArrayList<Address>
)

data class Address(
    val _id: String,
    val pincode: Int,
    val streetName: String,
    val city: String,
    val houseNo: String,
    val type: String,
    val userId: String,
) : Serializable {
    companion object {
        const val KEY_ADDRESS = "address"
    }
}
