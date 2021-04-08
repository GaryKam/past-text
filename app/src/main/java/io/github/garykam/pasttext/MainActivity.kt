package io.github.garykam.pasttext

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.garykam.pasttext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pastTexts = mutableListOf(PastText("t1", "hello"), PastText("t2", "world"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerPastText.adapter = PastTextListAdapter(pastTexts)

        binding.floatingButtonCreate.setOnClickListener {
            startActivity(Intent(this, CreatePastTextActivity::class.java))
        }
    }
}