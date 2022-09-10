package com.example.dictea.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictea.R
import com.example.dictea.models.Dictation
import com.example.dictea.models.DictationWord

class DictationsAdapter(private val dictations : List<DictationWord>) : RecyclerView.Adapter<DictationsAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvDictationName : TextView = view.findViewById(R.id.tv_dictation_name_dictation_item)
        val tvWordCount : TextView = view.findViewById(R.id.tv_nbr_word_dictation_item)
        val btnPlay : ImageButton = view.findViewById(R.id.btn_play_dictation_item)
        val btnEdit : ImageButton = view.findViewById(R.id.btn_edit_dictation_item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.dictation_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvDictationName.text = dictations[position].dictation.name
        val countText = "Number of word : " + dictations[position].words.size
        holder.tvWordCount.text = countText

        holder.btnEdit.setOnClickListener {

        }
        holder.btnPlay.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return dictations.size
    }
}