package com.example.workshopapp.dB

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.workshopapp.models.WorkshopListItem

@Dao
interface WorkshopDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertItemIfNotExists(item: WorkshopListItem)

    @Query("SELECT COUNT(*) FROM workshops WHERE name = :workshopName AND userId = :userId")
    suspend fun searchItem(workshopName: String, userId: String): Int

    @Query(value = "SELECT * FROM workshops WHERE userId  = :userId")
    suspend fun getWorkshops(userId: String): List<WorkshopListItem>

    @Query("SELECT COUNT(*) FROM workshops WHERE userId  = :userId")
    suspend fun getWorkshopCount(userId: String): Int

}