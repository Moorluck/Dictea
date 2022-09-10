package com.example.dictea.ui.home.words

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictea.adapters.WordsAdapter
import com.example.dictea.databinding.FragmentWordsBinding
import com.example.dictea.helpers.AudioHelper
import com.example.dictea.models.Word
import com.example.dictea.ui.home.WordViewModel
import com.example.dictea.ui.home.WordViewModelFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WordsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WordsFragment : Fragment() {

    private var _binding : FragmentWordsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : WordViewModel by activityViewModels { WordViewModelFactory(requireContext()) }

    private lateinit var adapter : WordsAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentWordsBinding.inflate(inflater, container, false)

        bindViewModel()
        bindRv()
        bindButton()
        return binding.root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
    }

    private fun bindViewModel() {
        viewModel.Words.observe(viewLifecycleOwner) {
            adapter.updateWords(it)
        }
    }

    private fun bindRv() {
        binding.rvWordsFragmentWords.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        adapter = WordsAdapter(listOf(), {
            viewModel.deleteWord(it)
        }, {
            lifecycleScope.launch {
                withContext(Dispatchers.IO) {
                    AudioHelper.playAudio(requireContext(), it.audio)
                }
            }
        })
        binding.rvWordsFragmentWords.adapter = adapter
    }

    private fun bindButton() {
        binding.btnAddFragmentWords.setOnClickListener() {
            // Disable button
            binding.btnAddFragmentWords.isEnabled = false

            // Stock word
            val wordToTest : String = binding.tiWordFragmentWords.text.toString()
            viewModel.testWord(wordToTest) { word ->

                binding.tiWordFragmentWords.setText("")
                binding.btnAddFragmentWords.isEnabled = true

                if (word == null) return@testWord

                if (wordToTest == word.word) {
                    viewModel.saveWord(word)
                }

                else {
                    val alertDialog : AlertDialog? = activity?.let {
                        val builder = AlertDialog.Builder(it)
                        builder.apply {
                            setMessage("Did you mean '${word.word}'")
                            setPositiveButton("Yes !") { _, _ ->
                                viewModel.saveWord(word)
                            }
                            setNegativeButton("No !") { _, _ ->
                                Toast.makeText(requireContext(), "The word hasn't been add", Toast.LENGTH_LONG).show()
                            }

                        }
                        builder.show()
                    }

                }

            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WordsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}