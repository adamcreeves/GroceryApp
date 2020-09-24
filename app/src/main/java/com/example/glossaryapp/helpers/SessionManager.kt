package com.example.glossaryapp.helpers

import android.content.Context
import com.example.glossaryapp.models.LoginResponse
import com.example.glossaryapp.models.User

class SessionManager(var myContext: Context) {
    private val FILE_NAME = "REGISTERED_USERS"
    private val KEY_TOKEN = "token"
    private val KEY_FIRST_NAME = "firstName"
    private val KEY_ID = "_id"
  //  private val KEY_FIRST_NAME = "firstName"


    var sharedPreferences = myContext.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE)
    var editor = sharedPreferences.edit()

    fun saveUserInfo(user: User) {
        editor.putString(KEY_FIRST_NAME, user.firstName)
        editor.putString(KEY_ID, user._id)
        editor.commit()
    }
    fun saveUserLogin(token: String) {
        editor.putString(KEY_TOKEN, token)
        editor.commit()

    }
    fun getUserInfo() : User {
        var name = sharedPreferences.getString(KEY_FIRST_NAME, null)
        var userId = sharedPreferences.getString(KEY_FIRST_NAME, null)
        return User(userId, null, name, null, null)
    }

    fun getQuickLogin() : Boolean {
        var token = sharedPreferences.getString(KEY_TOKEN, null)
        return token !=null
    }


}