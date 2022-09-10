package com.example.dictea.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictea.R
import com.example.dictea.models.DictationWord

class DictationsAdapter(private var dictations : List<DictationWord>, private val editListener : (dictation : DictationWord) -> Unit) : RecyclerView.Adapter<DictationsAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvDictationName : TextView = view.findViewById(R.id.tv_dictation_name_dictation_item)
        val tvWordPreview : TextView = view.findViewById(R.id.tv_preview_word_dictation_item)
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
        val previewText = dictations[position].words[0].word + ", " + dictations[position].words[1].word + ", " + dictations[position].words[2].word + ", ..."
        holder.tvWordPreview.text = previewText

        holder.btnEdit.setOnClickListener {
            editListener(dictations[position])
        }
        holder.btnPlay.setOnClickListener {
            //TODO t'en est là
        }
    }

    override fun getItemCount(): Int {
        return dictations.size
    }

    fun updateDictation(dictations : List<DictationWord>) {
        this.dictations = dictations
        notifyDataSetChanged()
    }
}