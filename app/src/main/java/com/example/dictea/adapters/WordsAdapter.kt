package com.example.dictea.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictea.R
import com.example.dictea.models.Word

class WordsAdapter(private var words : List<Word>,
                   private val deleteListener : (word : Word) -> Unit,
                   private val playListener : (word: Word) -> Unit): RecyclerView.Adapter<WordsAdapter.ViewHolder>() {
    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val tvWord : TextView = view.findViewById(R.id.tv_word_word_item)
        val btnDelete : ImageButton = view.findViewById(R.id.btn_delete_word_item)
        val btnPlay : ImageButton = view.findViewById(R.id.btn_play_word_item)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return ViewHolder(view)

    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvWord.text = words[position].word
        holder.btnDelete.setOnClickListener {
            deleteListener(words[position])
        }
        holder.btnPlay.setOnClickListener {
            playListener(words[position])
        }
    }

    override fun getItemCount(): Int {
        return words.size
    }

    fun updateWords(words : List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }
}