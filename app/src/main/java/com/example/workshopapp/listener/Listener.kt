package com.example.workshopapp.listener

import com.example.workshopapp.models.WorkshopListItem

interface Listener {

    fun onItemClickListener(position: Int, workshopListItem: WorkshopListItem)
}