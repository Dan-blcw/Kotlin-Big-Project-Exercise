package com.dan.um_app.dbase

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dan.um_app.model.entitis.NClass
import com.dan.um_app.model.entitis.Teacher

@Database(entities = arrayOf(Teacher::class), version = 1, exportSchema = false)
abstract class AllDB: RoomDatabase() {
    abstract fun getNoteDao(): AllDao
    companion object{
        @Volatile
        private var INSTANCE: AllDB? = null

        fun getDB(context: Context): AllDB{
            return  INSTANCE?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AllDB::class.java,
                    "dbdemo"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}