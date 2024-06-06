package com.cavalcantibruno.pocketmovies

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.cavalcantibruno.pocketmovies.api.MediaAPI
import com.cavalcantibruno.pocketmovies.api.RetrofitHelper
import com.cavalcantibruno.pocketmovies.api.model.MovieData
import com.cavalcantibruno.pocketmovies.api.model.MovieSearch
import com.cavalcantibruno.pocketmovies.api.model.SimpleMovie
import com.cavalcantibruno.pocketmovies.databinding.ActivityMainBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private val retrofitMovie by lazy {
        RetrofitHelper.retrofit
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        /*8279c620*/
        val apiKey = "{ApiKey}"
        Log.i("MainActivity", "onCreate: App Started ")

        binding.btnTeste.setOnClickListener {
            val movieTitle = binding.searchBar.text.toString()
            binding.searchBar.text = null
            CoroutineScope(Dispatchers.IO).launch {
                Log.i("Pão", "onCreate: Chegou aqui")
                getDetailedTitle(movieTitle,apiKey)
            }
        }

    }

    private suspend fun getDetailedTitle(title:String,apiKey:String){
        var dataReturn: Response<MovieData>? = null
        Log.i("Pão", "getTitle: Entrou aqui")
        try {
            val movieData = retrofitMovie.create(MediaAPI::class.java)
            dataReturn = movieData.getDetailedTitle(title,apiKey)
            Log.i("Pão", "getTitle: Entrou aqui2")
            Log.i("Pão", "geoConvert: $dataReturn")
        }catch (e:Exception) {
            e.printStackTrace()
            Log.i("Pão", "getTitleError: ${e.message} ")
        }
            if(dataReturn!=null)
            {
                Log.i("Pão", "geoConvert: $dataReturn")
                if(dataReturn.isSuccessful){
                    val omdbMovie = dataReturn.body()
                    Log.i("Pão", "getTitle: $omdbMovie ")
                    val omdbName = omdbMovie?.Title
                    val omdbDate = omdbMovie?.Released
                    val omdbPlot = omdbMovie?.Plot
                    val omdbPoster = omdbMovie?.Poster
                    withContext(Dispatchers.Main){
                        with(binding){
                            movieName.text = omdbName
                            date.text = omdbDate
                            moviePlot.text = omdbPlot
                            Picasso.get().load(omdbPoster).into(moviePoster)
                        }
                    }
                }
            }

    }


}