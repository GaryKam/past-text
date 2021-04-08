package io.github.garykam.pasttext

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import io.github.garykam.pasttext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pastTexts = mutableListOf(PastText("hello"), PastText("world"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerPastText.adapter = PastTextListAdapter(pastTexts)
    }
}