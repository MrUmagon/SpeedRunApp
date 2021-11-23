package com.mrumagon.speedrunaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.mrumagon.speedrunaa.databinding.ActivityTutorialesBinding
import java.util.regex.Pattern

class tutoriales : YouTubeBaseActivity() {

    private lateinit var binding: ActivityTutorialesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTutorialesBinding.inflate(layoutInflater)
        setContentView(binding.root)


        iniciarReproductor(getYoutubeVideoFromURL("https://www.youtube.com/watch?v=DJc9Z9ryjSo&ab_channel=Glitchymon")!!)
    }

    private fun iniciarReproductor(videoId : String) {
        binding.youtubePlayer.initialize(getString(R.string.api_key), object : YouTubePlayer.OnInitializedListener {
            override fun onInitializationSuccess(
                p0: YouTubePlayer.Provider?,
                p1: YouTubePlayer?,
                p2: Boolean
            ) {
                p1!!.loadVideo(videoId)
                p1.pause()
            }

            override fun onInitializationFailure(
                p0: YouTubePlayer.Provider?,
                p1: YouTubeInitializationResult?
            ) {
                Toast.makeText(applicationContext, "Error al reproducir Video", Toast.LENGTH_SHORT).show()
            }

        })
    }

    fun getYoutubeVideoFromURL(inUrl: String): String? {
        if(inUrl.toLowerCase().contains("youtu.be")){
            return inUrl.substring(inUrl.lastIndexOf("/") + 1)
        }
        val pattern = "(?<=watch\\?v=|/videos/embed\\/)[^#\\&\\?]*"
        val compiledPattern = Pattern.compile(pattern)
        val matcher = compiledPattern.matcher(inUrl)
        return if(matcher.find()){
            matcher.group()
        } else null

    }
}