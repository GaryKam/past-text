package io.github.garykam.pasttext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.garykam.pasttext.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val pastTexts = mutableListOf(PastText("t1", "hello"), PastText("t2", "world"))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerPastText.adapter = PastTextListAdapter(pastTexts)

        // Floating action button to navigate to the Past Text creation screen.
        binding.floatingButtonCreate.setOnClickListener {
            startActivity(Intent(this, CreatePastTextActivity::class.java))
        }

        // Add a newly created Past Text using data from the intent.
        intent.extras?.let {
            pastTexts.add(PastText(it.getString("TITLE")!!, it.getString("CONTENT")!!))
        }
    }

    companion object {
        private const val EXTRA_TITLE = "io.github.garykam.pasttext.TITLE"
        private const val EXTRA_CONTENT = "io.github.garykam.pasttext.CONTENT"

        fun newIntent(context: Context, title: String, content: String): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_CONTENT, content)
            return intent
        }
    }
}