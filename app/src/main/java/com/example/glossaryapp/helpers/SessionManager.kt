package com.example.glossaryapp.helpers

import android.content.Context
import com.example.glossaryapp.models.LoginResponse
import com.example.glossaryapp.models.User

class SessionManager(var myContext: Context) {
    private val FILE_NAME = "REGISTERED_USERS"
    private val KEY_TOKEN = "token"
    private val KEY_FIRST_NAME = "firstName"
    private val KEY_USER_ID = "userId"
  //  private val KEY_FIRST_NAME = "firstName"


    var sharedPreferences = myContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUserInfo(user: User) {
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_TOKEN, user.token)
        editor.commit()

    }
    fun getUserInfo() : User {
        var name = sharedPreferences.getString(KEY_FIRST_NAME, null)
        var token = sharedPreferences.getString(KEY_TOKEN, "Signed in")
        return User(null, null, name, null, null, token)
    }


}