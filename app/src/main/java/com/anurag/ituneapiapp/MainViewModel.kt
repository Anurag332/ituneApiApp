package com.anurag.ituneapiapp

import android.view.View
import androidx.lifecycle.ViewModel
import com.anurag.ituneapiapp.data.ApiResponse
import com.anurag.ituneapiapp.repositories.RepositoryListener
import com.anurag.ituneapiapp.repositories.SongRepository

class MainViewModel (
    private val  repository: SongRepository,
): ViewModel(){
    var artist: String? =null
    var listeners: ResListeners? =null
    var _res: ApiResponse? = null

    fun onSearchClick(veiw: View){
        repository.listener =object : RepositoryListener {
            override fun onSuccess(res: ApiResponse, of: String) {
                     _res = res
                listeners?.processRv(res.results, of)


            }
        }
        if (artist.isNullOrEmpty()) {
            listeners?.onFailure("No result found : (")
        }

        repository.getSongs(artist!!)
    }
}