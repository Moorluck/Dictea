package com.example.dictea.adapters

import android.view.LayoutInflater
import android.view.OnReceiveContentListener
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.dictea.R
import com.example.dictea.models.Word

class EditWordAdapter(private var words : List<Word>,
                      private val deleteListener: (word : Word) -> Unit,
                      private val addListener: (word : String, cb : () -> Unit) -> Unit,
                      private val playListener: (word: Word) -> Unit) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val EDIT_VIEW_TYPE = 1
    private val MAIN_VIEW_TYPE = 0

    class EditViewHolder(view : View) : RecyclerView.ViewHolder(view) {
        val etWord : EditText = view.findViewById(R.id.et_edit_word_item)
        val btnAdd : ImageButton = view.findViewById(R.id.btn_add_edit_word_item)
    }

    class MainViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvWord : TextView = view.findViewById(R.id.tv_word_word_item)
        val btnDelete : ImageButton = view.findViewById(R.id.btn_delete_word_item)
        val btnPlay : ImageButton = view.findViewById(R.id.btn_play_word_item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        if (viewType == EDIT_VIEW_TYPE) {
            val view: View =
                LayoutInflater.from(parent.context).inflate(R.layout.edit_word_item, parent, false)
            return EditViewHolder(view)
        }
        val view : View =
            LayoutInflater.from(parent.context).inflate(R.layout.word_item, parent, false)
        return MainViewHolder(view)
    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return EDIT_VIEW_TYPE
        }
        return MAIN_VIEW_TYPE
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (position > 0) {
            val mainHolder = holder as MainViewHolder
            mainHolder.tvWord.text = words[position - 1].word
            mainHolder.btnDelete.setOnClickListener {
                deleteListener(words[position - 1])
            }
            mainHolder.btnPlay.setOnClickListener {
                playListener(words[position - 1])
            }
        }
        else {
            val editHolder = holder as EditViewHolder
            editHolder.btnAdd.setOnClickListener {
                editHolder.btnAdd.isEnabled = false
                addListener(editHolder.etWord.text.toString()) {
                    editHolder.btnAdd.isEnabled = true
                    editHolder.etWord.setText("")
                    editHolder.etWord.requestFocus()
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return words.size + 1
    }

    fun editWords(words: List<Word>) {
        this.words = words
        notifyDataSetChanged()
    }
}