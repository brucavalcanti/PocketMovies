package com.cavalcantibruno.pocketmovies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
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


const val MAIN_ACTIVITY = "MainActivity"

const val GET_MOVIE_LIST = "GetMovieList"

class MainActivity : AppCompatActivity() {
    private val retrofitMovie by lazy {
        RetrofitHelper.retrofit
    }

    private val binding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }



    var selectPage = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)


        val apiKey = "{ApiKey}"
        binding.nextPage.isEnabled = false
        binding.prevPage.isEnabled = false
        var auxMovieTitle= ""
        Log.i(MAIN_ACTIVITY, "onCreate: App Started ")

        binding.btnSearch.setOnClickListener {
            val movieTitle = binding.searchBar.text.toString()
            auxMovieTitle = movieTitle
            binding.searchBar.text = null
            CoroutineScope(Dispatchers.IO).launch {
                Log.i(MAIN_ACTIVITY, "onCreate: Coroutine Started")
                getMovieList(movieTitle,"1",apiKey)
            }
        }



        binding.nextPage.setOnClickListener {

            selectPage+=1
            CoroutineScope(Dispatchers.IO).launch {
                Log.i(MAIN_ACTIVITY, "onCreate: NextPage Coroutine Started")
                getMovieList(auxMovieTitle,"$selectPage",apiKey)
            }
        }

        binding.prevPage.setOnClickListener {
                selectPage -=1
                CoroutineScope(Dispatchers.IO).launch {
                    Log.i(MAIN_ACTIVITY, "onCreate: PrevPage Coroutine Started")
                    getMovieList(auxMovieTitle,"$selectPage",apiKey)
                }

        }
    }



    private suspend fun getMovieList(title:String,page:String,apiKey:String){
        var dataReturn: Response<MovieSearch>? = null
        Log.i(GET_MOVIE_LIST, "getMovieList: Started List Request")
        try {
            val movieData = retrofitMovie.create(MediaAPI::class.java)
            dataReturn = movieData.getMovieList(title,page,apiKey)
            Log.i(GET_MOVIE_LIST, "getMovieList: Requesting data from API")
            Log.d(GET_MOVIE_LIST, " DataReturn: $dataReturn")
        }catch (e:Exception) {
            e.printStackTrace()
            Log.i(GET_MOVIE_LIST, "getMovieListError: ${e.message} ")
        }
            if(dataReturn!=null)
            {
                if(dataReturn.isSuccessful){
                    val omdbMovie = dataReturn.body()
                    val movieList = omdbMovie?.Search
                    Log.d(GET_MOVIE_LIST, "getMovieList: $omdbMovie")
                    Log.d(GET_MOVIE_LIST, "getMovieList: $movieList")
                    withContext(Dispatchers.Main){
                        with(binding){

                            prevPage.isEnabled = selectPage != 1
                            if(movieList!=null){
                                Log.d(GET_MOVIE_LIST, "getMovieList: $movieList")
                                rvMovies.adapter = omdbMovie.let {

                                    MovieAdapter(movieList){item->
                                        val intent = Intent(this@MainActivity,TitleActivity::class.java)
                                        intent.putExtra("mediaItem",item)
                                        startActivity(intent)
                                    }
                                }
                               rvMovies.layoutManager = GridLayoutManager(this@MainActivity,3)
                            }
                        }
                    }
                }
            }

    }
}