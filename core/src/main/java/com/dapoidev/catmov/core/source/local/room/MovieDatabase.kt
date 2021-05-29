package com.dapoidev.catmov.core.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dapoidev.catmov.core.source.local.entitiy.MovieEntity

@Database(entities = [MovieEntity::class], version = 1, exportSchema = false)
abstract class MovieDatabase : RoomDatabase() {

    abstract fun movieDao(): MovieDao
}