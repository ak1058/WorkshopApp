package com.example.workshopapp.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("workshops")
data class WorkshopListItem(
    @PrimaryKey
    val id: Int,
    var userId: String,
    val instructor: String,
    val date: String,
    val details: String,
    val icon_link: String,
    val name: String,
    val time: String,
    var isApplied: Boolean = false
)