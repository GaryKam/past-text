package io.github.garykam.pasttext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import io.github.garykam.pasttext.databinding.ItemPastTextBinding
import io.github.garykam.pasttext.model.PastText
import java.util.*

class PastTextListAdapter(
    private val pastTexts: List<PastText>,
    private val pastTextListener: PastTextListener
) : RecyclerView.Adapter<PastTextListAdapter.PastTextViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastTextViewHolder {
        return PastTextViewHolder(
            ItemPastTextBinding.inflate(LayoutInflater.from(parent.context), parent, false),
            pastTextListener
        )
    }

    override fun onBindViewHolder(holder: PastTextViewHolder, position: Int) {
        holder.bind(pastTexts[position])
    }

    override fun getItemCount() = pastTexts.size

    class PastTextViewHolder(
        private val binding: ItemPastTextBinding,
        private val pastTextListener: PastTextListener
    ) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            pastTextListener.onClick(adapterPosition)
        }

        fun bind(pastText: PastText) {
            binding.textTitle.text = pastText.title

            if (pastText.isUnlocked()) {
                // Indicate that the Past Text is unlocked.
                binding.imageTime.setImageResource(R.drawable.ic_lock_open)
                binding.textTime.visibility = View.INVISIBLE
            } else {
                // Show the time remaining until the Past Text will unlock.
                var timeDifference = pastText.unlockDate.time - Date().time
                val days = timeDifference / (1000 * 60 * 60 * 24)
                timeDifference %= 1000 * 60 * 60 * 24
                val hours = timeDifference / (1000 * 60 * 60)

                binding.textTime.text =
                    itemView.context.getString(R.string.format_time, days, hours)
            }
        }
    }

    class PastTextListener(private val clickListener: (index: Int) -> Unit) {
        fun onClick(index: Int) = clickListener(index)
    }
}