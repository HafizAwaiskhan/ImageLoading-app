package com.example.joinappstudioassignment.data.localdata

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.joinappstudioassignment.data.localdata.dao.ImageDao
import com.example.joinappstudioassignment.data.responsemodel.ImageResponse

@Database(entities = [ImageResponse::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun getImageDao(): ImageDao
}