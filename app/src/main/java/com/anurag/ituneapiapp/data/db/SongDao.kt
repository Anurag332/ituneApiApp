package com.anurag.ituneapiapp.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.anurag.ituneapiapp.data.entities.Song

@Dao
interface SongDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(songs: List<Song>)

    @Query("SELECT * FROM Song WHERE artistName LIKE :name LIMIT 10")
    fun getSongs(name: String): List<Song>
}