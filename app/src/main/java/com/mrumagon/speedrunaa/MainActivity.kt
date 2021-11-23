package com.mrumagon.speedrunaa

import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mrumagon.speedrunaa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSpeedrun.setOnClickListener{
            val intent: Intent = Intent(this, menu_speedrun::class.java)
            startActivity(intent)
        }

        binding.buttonTutoriales.setOnClickListener{
            val intent: Intent = Intent(this, tutoriales::class.java)
            startActivity(intent)
        }

        binding.buttonAyuda.setOnClickListener{
            val intent: Intent = Intent(this, ayuda_activity::class.java)
            startActivity(intent)
        }

    }

}