package io.github.garykam.pasttext

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PastTextListAdapter(
    private val pastTexts: MutableList<PastText>
) : RecyclerView.Adapter<PastTextListAdapter.PastTextViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PastTextViewHolder {
        return PastTextViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_past_text, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PastTextViewHolder, position: Int) {
        holder.content.text = pastTexts[position].content
    }

    override fun getItemCount() = pastTexts.size

    class PastTextViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val content: TextView = itemView.findViewById(R.id.text_content)
    }
}