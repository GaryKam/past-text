package io.github.garykam.pasttext

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import io.github.garykam.pasttext.databinding.ActivityPastTextDetailsBinding

class PastTextDetailsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPastTextDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPastTextDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        intent.extras?.let {
            binding.textTitle.text = it.getString(EXTRA_TITLE)
            binding.textContent.text = it.getString(EXTRA_CONTENT)
        }
    }

    companion object {
        private const val EXTRA_TITLE = "io.github.garykam.pasttext.TITLE"
        private const val EXTRA_CONTENT = "io.github.garykam.pasttext.CONTENT"

        fun startActivity(context: Context, title: String, content: String) {
            val intent = Intent(context, PastTextDetailsActivity::class.java)
            intent.putExtra(EXTRA_TITLE, title)
            intent.putExtra(EXTRA_CONTENT, content)
            context.startActivity(intent)
        }
    }
}