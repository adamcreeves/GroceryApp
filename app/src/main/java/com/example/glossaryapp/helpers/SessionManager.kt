package com.example.glossaryapp.helpers

import android.content.Context
import com.example.glossaryapp.models.Address
import com.example.glossaryapp.models.LoginResponse
import com.example.glossaryapp.models.User

class SessionManager(var myContext: Context) {
    private val FILE_NAME = "REGISTERED_USERS"

    private val KEY_TOKEN = "token"

    private val KEY_FIRST_NAME = "firstName"
    private val KEY_ID = "_id"
    private val KEY_EMAIL = "email"
    private val KEY_MOBILE = "mobile"

    private val KEY_PINCODE = "pincode"
    private val KEY_STREETNAME = "streetName"
    private val KEY_CITY = "city"
    private val KEY_HOUSE_NO = "houseNo"
    private val KEY_TYPE = "type"


    var sharedPreferences = myContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUserInfo(user: User) {
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_ID, user._id)
        editor.putString(KEY_EMAIL, user.email)
        editor.putString(KEY_MOBILE, user.mobile)
        editor.commit()
    }

    fun saveUserLogin(token: String) {
        editor.putString(KEY_TOKEN, token)
        editor.commit()

    }

    fun getUserId() : String {
        var userId = sharedPreferences.getString(KEY_ID, null)
        return userId.toString()
    }

    fun getFirstName() : String {
        var name = sharedPreferences.getString(KEY_FIRST_NAME, null)
        return name.toString()
    }

    fun saveAddress(address: Address) {
        editor.putString(KEY_PINCODE, address.pincode.toString())
        editor.putString(KEY_STREETNAME, address.streetName)
        editor.putString(KEY_CITY, address.city)
    }

    fun getEmail() : String {
        var email = sharedPreferences.getString(KEY_EMAIL, null)
        return email.toString()
    }

    fun getQuickLogin() : Boolean {
        var token = sharedPreferences.getString(KEY_TOKEN, null)
        return token !=null
    }

    fun logout() {
        editor.clear()
        editor.commit()
    }


}