package com.example.glossaryapp.helpers

import android.content.Context
import com.example.glossaryapp.models.User

class SessionManager(var myContext: Context) {
    private val FILE_NAME = "REGISTERED_USERS_TOKENS"
    private val KEY_TOKEN = "token"

    var sharedPreferences = myContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun login(token: String) {

    }


}