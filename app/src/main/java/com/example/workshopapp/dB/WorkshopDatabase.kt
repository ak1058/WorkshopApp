package com.example.workshopapp.dB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.workshopapp.models.WorkshopListItem

@Database(entities = [WorkshopListItem::class], version = 2)
abstract class WorkshopDatabase: RoomDatabase() {

    abstract fun workshopDao(): WorkshopDao

    companion object{
        @Volatile
        private var INSTANCE: WorkshopDatabase? =null

        fun getDataBase(context: Context): WorkshopDatabase {
            if (INSTANCE == null){
                synchronized(this){
                    if (INSTANCE == null){
                        INSTANCE = Room.databaseBuilder(context,
                            WorkshopDatabase::class.java,
                            "workshopDb")
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}