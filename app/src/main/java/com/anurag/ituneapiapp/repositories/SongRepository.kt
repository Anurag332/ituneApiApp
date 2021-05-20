package com.anurag.ituneapiapp.repositories

import com.anurag.ituneapiapp.data.ApiResponse
import com.anurag.ituneapiapp.data.db.SongDatabase
import com.anurag.ituneapiapp.data.entities.Song
import com.anurag.ituneapiapp.network.ApiCalls
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SongRepository(
    private val api: ApiCalls,
    val db : SongDatabase,

    ){

    var listener: RepositoryListener? = null

    fun getSongs(name: String): ApiResponse? {
        var _response: ApiResponse? =null
        api.getSongs(name,10)
            .enqueue(object : Callback<ApiResponse> {
                override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                    _response = response.body()
                    if (_response != null)  {
                        db.getSongDao().upsert(_response!!.results)
                        listener?.onSuccess(_response!!,"Online")
                    }
                }

                override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                    var songs: List<Song> = getSongsFromDatabase(name)
                    _response = ApiResponse(songs)
                    listener?.onSuccess(_response!!,"offline")
                }


            })
        return _response

    }

    private fun getSongsFromDatabase(name: String): List<Song> {
        return db.getSongDao().getSongs("%$name")

    }
}