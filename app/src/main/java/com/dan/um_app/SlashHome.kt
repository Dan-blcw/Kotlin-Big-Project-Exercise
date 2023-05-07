package com.dan.um_app

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dan.um_app.databinding.ActivitySlashHomeBinding

private lateinit var binding: ActivitySlashHomeBinding
class SlashHome : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySlashHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSlashhome.setOnClickListener {
            val intent = Intent(this@SlashHome, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}