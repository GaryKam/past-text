package io.github.garykam.pasttext

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import io.github.garykam.pasttext.model.PastText
import java.util.*

class PastTextListAdapter(
    private val context: Context,
    private val pastTexts: List<PastText>
) : RecyclerView.Adapter<PastTextListAdapter.PastTextViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastTextViewHolder {
        return PastTextViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_past_text, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PastTextViewHolder, position: Int) {
        val pastText = pastTexts[position]

        holder.titleText.text = pastText.title

        if (pastText.isUnlocked()) {
            // Indicate that the Past Text is unlocked.
            holder.timeImage.setImageResource(R.drawable.ic_lock_open)
            holder.timeText.visibility = View.INVISIBLE
        } else {
            // Show the time remaining until the Past Text will unlock.
            var timeDifference = pastText.unlockDate.time - Date().time
            val days = timeDifference / (1000 * 60 * 60 * 24)
            timeDifference %= 1000 * 60 * 60 * 24
            val hours = timeDifference / (1000 * 60 * 60)
            holder.timeText.text = context.getString(R.string.format_time, days, hours)
        }
    }

    override fun getItemCount() = pastTexts.size

    class PastTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleText: TextView = itemView.findViewById(R.id.text_title)
        val timeText: TextView = itemView.findViewById(R.id.text_time)
        val timeImage: ImageView = itemView.findViewById(R.id.image_time)

        /*init {
            itemView.setOnClickListener {
                val pastText = pastTexts[adapterPosition]

                if (pastText.isUnlocked()) {
                    // Display details activity when the Past Text is clicked.
                    DetailsFragment.startActivity(
                        it.context, pastText.title, pastText.content
                    )
                } else {
                    // Alert the user that the Past Text is still locked.
                    MaterialAlertDialogBuilder(itemView.context)
                        .setMessage(R.string.locked_past_text)
                        .setPositiveButton(R.string.ok) { dialog, _ -> dialog.dismiss() }
                        .show()
                }
            }
        }*/
    }
}