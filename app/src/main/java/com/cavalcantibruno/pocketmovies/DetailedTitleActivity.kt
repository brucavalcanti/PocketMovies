package com.cavalcantibruno.pocketmovies

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.cavalcantibruno.pocketmovies.api.MediaAPI
import com.cavalcantibruno.pocketmovies.api.RetrofitHelper
import com.cavalcantibruno.pocketmovies.api.model.MovieData
import com.cavalcantibruno.pocketmovies.api.model.MovieSearch
import com.cavalcantibruno.pocketmovies.databinding.ActivityDetailedTitleBinding
import com.squareup.picasso.Picasso
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response

const val GET_DETAILED_TITLE = "GetDetailedTitle"

class DetailedTitleActivity:AppCompatActivity() {

    private val retrofitDetailedMovie by lazy {
        RetrofitHelper.retrofit
    }

    private val binding by lazy {
        ActivityDetailedTitleBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val title = intent.getStringExtra("mediaTitle")
        val apiKey = "{ApiKey}"

        CoroutineScope(Dispatchers.IO).launch {
            getDetailedTitle(title,apiKey)
        }

        binding.btnReturn.setOnClickListener {
            finish()
        }

    }

    private suspend fun getDetailedTitle(title: String?, apiKey: String) {
        var dataReturn: Response<MovieData>? = null
        Log.i(GET_DETAILED_TITLE, "getDetailedTitle: Starting Request")
        try {
            val movieData = retrofitDetailedMovie.create(MediaAPI::class.java)
            dataReturn = movieData.getDetailedTitle(title!!,apiKey)
            Log.i(GET_DETAILED_TITLE, "getDetailedTitle: Requesting data from API")
            Log.d(GET_DETAILED_TITLE, "getDetailedTitle: $dataReturn")
        }catch (e:Exception) {
            e.printStackTrace()
            Log.i(GET_DETAILED_TITLE, "getTitleError: ${e.message} ")
        }

        if(dataReturn!=null)
        {
            if(dataReturn.isSuccessful)
            {
                val omdbMedia = dataReturn.body()
                withContext(Dispatchers.Main){
                    val mediaPoster = omdbMedia?.Poster
                    val mediaTitle = omdbMedia?.Title
                    val mediaDate = omdbMedia?.Released
                    val mediaLanguage = omdbMedia?.Language
                    val mediaRuntime = omdbMedia?.Runtime
                    val mediaGenre = omdbMedia?.Genre
                    val mediaPlot = omdbMedia?.Plot
                    val mediaRated = omdbMedia?.Rated
                    val mediaRating = omdbMedia?.imdbRating

                    with(binding){
                        Picasso.get().load(mediaPoster)
                            .resize(moviePosterBg.width,moviePosterBg.height)
                            .into(moviePosterBg)
                        detailedTitle.text = mediaTitle
                        detailedLanguage.text = "$mediaLanguage"
                        detailedReleasedDate.text = "Released Date\n$mediaDate"
                        detailedRuntime.text = "Runtime\n$mediaRuntime"
                        detailedGenre.text= "$mediaGenre"
                        detailedPlot.text = mediaPlot
                        detailedRated.text = "Rated\n$mediaRated"
                        detailedImdbRating.text = "$mediaRating/10 Imdb"
                    }
                }
            }
        }
    }

}