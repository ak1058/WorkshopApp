package com.example.workshopapp.viewModels

import android.content.Context
import androidx.lifecycle.ViewModel
import com.example.workshopapp.models.WorkshopListItem
import com.google.gson.Gson

class WorkshopViewModel(val context: Context): ViewModel() {

    private lateinit var workshopList: ArrayList<WorkshopListItem>


    init {
        loadWorkshopFromAsset()
        workshopList = ArrayList<WorkshopListItem>()
        for (i in loadWorkshopFromAsset()!!){
            workshopList.add(i)
        }
    }

    private fun loadWorkshopFromAsset(): Array<WorkshopListItem>? {
        val inputStream = context.assets.open("data.json")
        val size: Int = inputStream.available()
        val buffer = ByteArray(size)
        inputStream.read(buffer)
        inputStream.close()

        val json = String(buffer, Charsets.UTF_8)
        val gson = Gson()
        return gson.fromJson(json, Array<WorkshopListItem>::class.java)
    }

    fun getList() = workshopList


}