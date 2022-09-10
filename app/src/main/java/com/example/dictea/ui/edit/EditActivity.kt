package com.example.dictea.ui.edit

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictea.R
import com.example.dictea.adapters.EditWordAdapter
import com.example.dictea.api.LingueeApi
import com.example.dictea.api.RetrofitClient
import com.example.dictea.api.repository.LingueeRepository
import com.example.dictea.databinding.ActivityEditBinding
import com.example.dictea.databinding.FragmentWordsBinding
import com.example.dictea.helpers.AudioHelper
import com.example.dictea.models.Dictation
import com.example.dictea.models.DictationWord
import com.example.dictea.models.Word
import retrofit2.create
import java.io.Serializable
import java.util.ArrayList

class EditActivity : AppCompatActivity() {

    private lateinit var binding : ActivityEditBinding

    private lateinit var adapter: EditWordAdapter

    private var dictation : DictationWord? = null

    private var words : MutableList<Word> = mutableListOf()

    private var suppressedWords : MutableList<Word> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        bindRv()
        bindButton()

        dictation = intent.getSerializableExtra("dictation") as DictationWord?

        if (dictation != null) {
            Log.d("EditActivity", "Update dictation")
            setData()
        }
    }

    private fun setData() {
        binding.tiDictationNameEditActivity.setText(dictation!!.dictation.name)
        words = dictation!!.words as MutableList<Word>
        adapter.editWords(dictation!!.words)
    }


    private fun bindRv() {
        adapter = EditWordAdapter(words, {
            suppressedWords.add(it)
            words.remove(it)
            adapter.editWords(words)
        }, { word, cb ->
            LingueeRepository.testWord(word, RetrofitClient.client.create()) {
                if (it == null) {
                    Toast.makeText(this, "An error occured", Toast.LENGTH_LONG).show()
                }
                else if (it.word == word) {
                    if (!words.map { w -> w.word }.contains(it.word)) {
                        it.order = words.size
                        words.add(it)
                        adapter.editWords(words)
                    }
                }
                else {
                    val alertDialog : AlertDialog? = this.let { activity ->
                        val builder = AlertDialog.Builder(activity)
                        builder.apply {
                            setMessage("Did you mean '${it.word}'")
                            setPositiveButton("Yes !") { _, _ ->
                                if (!words.contains(it)) {
                                    it.order = words.size
                                    words.add(it)
                                    adapter.editWords(words)
                                }
                            }
                            setNegativeButton("No !") { _, _ ->
                                Toast.makeText(this@EditActivity, "The word hasn't been add", Toast.LENGTH_LONG).show()
                            }

                        }
                        builder.show()
                    }
                }
                cb()
            }
        }, {
            AudioHelper.playAudio(this, it.audio)
        })

        binding.rvEditWordActivity.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.rvEditWordActivity.adapter = adapter
    }

    private fun bindButton() {
        binding.btnSaveEditActivity.setOnClickListener {

            if (words.size < 4) {
                Toast.makeText(this, "A dictation need at least 3 words !", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            if (binding.tiDictationNameEditActivity.text.toString().length < 3) {
                Toast.makeText(this, "A dictation name need at least 3 letters !", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val newDictations = DictationWord(Dictation(
                if (dictation != null) dictation!!.dictation.id else null,
                binding.tiDictationNameEditActivity.text.toString()),
                words)
            setResult(Activity.RESULT_OK, Intent().apply {
                putExtra("newDictations", newDictations)
                putStringArrayListExtra("suppressedWord", suppressedWords.map { it.word } as ArrayList<String>)})
            finish()
        }
    }
}