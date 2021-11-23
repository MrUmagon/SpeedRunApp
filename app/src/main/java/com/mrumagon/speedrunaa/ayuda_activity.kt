package com.mrumagon.speedrunaa

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mrumagon.speedrunaa.databinding.ActivityAyudaBinding
import com.mrumagon.speedrunaa.databinding.ActivityMainBinding

class ayuda_activity : AppCompatActivity() {
    private lateinit var binding: ActivityAyudaBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAyudaBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

}