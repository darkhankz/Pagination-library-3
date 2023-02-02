package com.movies.pagination_library_3.model.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.movies.pagination_library_3.data.MoviesDetailsData

@Database(entities = [MoviesDetailsData::class], version = 2)
abstract class MoviesRoomDatabase: RoomDatabase() {

    abstract fun getMoviesDao(): MoviesDao

    companion object{
        private var database: MoviesRoomDatabase ?= null

        fun getInstance(context: Context): MoviesRoomDatabase{
            return if (database == null){
                database = Room
                    .databaseBuilder(context, MoviesRoomDatabase::class.java, "db")
                    .build()
                database as MoviesRoomDatabase
            } else{
                database as MoviesRoomDatabase

            }
        }
    }
}