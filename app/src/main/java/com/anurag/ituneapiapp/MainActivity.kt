package com.anurag.ituneapiapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.anurag.ituneapiapp.data.db.SongDatabase
import com.anurag.ituneapiapp.data.entities.Song
import com.anurag.ituneapiapp.databinding.ActivityMainBinding
import com.anurag.ituneapiapp.network.ApiCalls
import com.anurag.ituneapiapp.network.NetworkConnectionInterceptor
import com.anurag.ituneapiapp.repositories.SongRepository

class MainActivity : AppCompatActivity(), ResListeners {
    private var adapter: Adapter? = null
    var recyclerView: RecyclerView? = null
    private var songsModels: ArrayList<SongViewModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val interceptor = NetworkConnectionInterceptor(this)
        val api = ApiCalls(interceptor)
        val db = SongDatabase(this)
        val repository = SongRepository(api, db)

        val binding: ActivityMainBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.recyclerView.layoutManager= LinearLayoutManager(this)
        adapter = Adapter(this,songsModels)
        binding.recyclerView.adapter = adapter

        val viewModel = MainViewModel(repository)
        viewModel.listeners = this
        binding.viewmodel = viewModel

    }

    override fun onFailure(msg: String) {
        Toast.makeText(this,msg, Toast.LENGTH_SHORT).show()
    }

    override fun onSuccess() {

    }

    override fun processRv(
        songs: List<Song>,
        of: String
    ) {
        songsModels.clear()
        for (item in songs) {
            var songViewModel = SongViewModel()
            songViewModel.artistName = item.artistName
            songViewModel.trackName = item.trackName
            songViewModel.url = item.artworkUrl100
            songViewModel.of = of
            songsModels.add(songViewModel)
        }

        adapter!!.notifyDataSetChanged()
    }
}