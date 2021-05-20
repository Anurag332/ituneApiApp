package com.anurag.ituneapiapp

import com.anurag.ituneapiapp.data.entities.Song

interface ResListeners {
    fun onFailure(msg:String)

    fun  onSuccess()
    fun processRv(songs: List<Song>, of: String)
}