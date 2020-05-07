package com.seint.takehome.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = arrayOf(Appointment::class),version = 1)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase(){
    abstract fun appointmentDao() : AppointmentDao

    companion object{
        //singleton to prevent multiple instances of database opening
        @Volatile
        private var INSTANCE : AppDatabase? = null

        fun getDataBase(context: Context) : AppDatabase {
            val tempInstance = INSTANCE
            if(tempInstance != null){
                return  tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java,"appDataBase").build()
                INSTANCE = instance
                return  instance
            }
        }
    }
}
