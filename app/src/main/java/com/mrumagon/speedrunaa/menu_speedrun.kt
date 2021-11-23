package com.mrumagon.speedrunaa

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.mrumagon.speedrunaa.databinding.ActivityMenuSpeedrunBinding

class menu_speedrun : AppCompatActivity() {

    private lateinit var binding: ActivityMenuSpeedrunBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMenuSpeedrunBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.newList.setOnClickListener{
            val intent = Intent(this, SpeedRunTimer::class.java)
            intent.putExtra("numero", 0)
            startActivity(intent)
        }

        binding.buttonOcarina.setOnClickListener{
            val intent = Intent(this, SpeedRunTimer::class.java)
            intent.putExtra("numero", 1)
            startActivity(intent)
        }

        binding.buttonMario.setOnClickListener{
            val intent = Intent(this, SpeedRunTimer::class.java)
            intent.putExtra("numero", 2)
            startActivity(intent)
        }

        binding.buttonMinecraft.setOnClickListener{
            val intent = Intent(this, SpeedRunTimer::class.java)
            intent.putExtra("numero", 3)
            startActivity(intent)
        }

    }
}