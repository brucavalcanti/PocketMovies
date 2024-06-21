package com.cavalcantibruno.pocketmovies

import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.cavalcantibruno.pocketmovies.api.model.SimpleMovie
import com.cavalcantibruno.pocketmovies.databinding.ActivityTitleBinding
import com.squareup.picasso.Picasso

class TitleActivity: AppCompatActivity() {

    private val binding by lazy{
        ActivityTitleBinding.inflate(layoutInflater)
    }



    @RequiresApi(Build.VERSION_CODES.TIRAMISU)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val detailedMedia = intent.getSerializableExtra("mediaItem",SimpleMovie::class.java)
        with(binding){
            Picasso.get().load(detailedMedia?.Poster).resize(300,400).into(mediaPoster)
            mediaTitle.text = detailedMedia?.Title
            mediaYear.text = detailedMedia?.Year
            mediaGenre.text = detailedMedia?.Type
            btnBack.setOnClickListener {
                finish()
            }
            btnDetailedTitle.setOnClickListener {
                val intent = Intent(this@TitleActivity,DetailedTitleActivity::class.java)
                intent.putExtra("mediaTitle",detailedMedia?.Title)
                startActivity(intent)
            }
        }
    }

}