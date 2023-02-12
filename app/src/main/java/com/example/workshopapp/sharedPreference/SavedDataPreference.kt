package com.example.workshopapp.sharedPreference

import android.content.Context
import com.example.workshopapp.models.WorkshopListItem
import com.example.workshopapp.utils.Constants.SHARED_PREFERENCE_KEY
import com.example.workshopapp.utils.Constants.SHARED_PREFERENCE_KEY_2
import com.example.workshopapp.utils.Constants.USERNAME_KEY
import com.example.workshopapp.utils.Constants.WORKSHOP_KEY
import com.google.gson.Gson

class SavedDataPreference(val context: Context) {

    val prefs = context.getSharedPreferences(SHARED_PREFERENCE_KEY, Context.MODE_PRIVATE)
    val prefs2 = context.getSharedPreferences(SHARED_PREFERENCE_KEY_2, Context.MODE_PRIVATE)


    fun saveUserName(userName: String){
        val editor = prefs.edit()
        editor.putString(USERNAME_KEY, userName)
        editor.apply()
    }
    fun getUserName(): String? {
        return prefs.getString(USERNAME_KEY, " ")
    }

    fun saveWorkshopData(workshopListItem: WorkshopListItem){
        val editor = prefs2.edit()
        val parsedBeerListItem = Gson().toJson(workshopListItem)
        editor.putString(WORKSHOP_KEY, parsedBeerListItem)
        editor.apply()
    }
    fun getWorkshopData(): String? {
        return prefs2.getString(WORKSHOP_KEY, null)
    }
}