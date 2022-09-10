package com.example.dictea.ui.home.dictations

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dictea.R
import com.example.dictea.adapters.DictationsAdapter
import com.example.dictea.databinding.FragmentDictationsBinding
import com.example.dictea.databinding.FragmentWordsBinding
import com.example.dictea.models.Dictation
import com.example.dictea.models.DictationWord
import com.example.dictea.models.Word
import com.example.dictea.ui.edit.EditActivity
import com.example.dictea.ui.home.WordViewModel
import com.example.dictea.ui.home.WordViewModelFactory

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DictationsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class DictationsFragment : Fragment() {

    private var _binding : FragmentDictationsBinding? = null
    private val binding get() = _binding!!

    private val viewModel : WordViewModel by activityViewModels { WordViewModelFactory(requireContext()) }

    private lateinit var adapter : DictationsAdapter

    private lateinit var startForResult : ActivityResultLauncher<Intent>

    private var dictations : List<DictationWord> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDictationsBinding.inflate(inflater, container, false)

        bindRv()
        bindViewModel()
        bindButton()
        bindActivityResult()
        return binding.root
    }

    private fun bindRv() {
        adapter = DictationsAdapter(dictations) {
            val intent = Intent(requireContext(), EditActivity::class.java)
            intent.putExtra("dictation", it)
            startForResult.launch(intent)
        }
        binding.rvDictationsFragmentDictations.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.rvDictationsFragmentDictations.adapter = adapter
    }


    private fun bindViewModel() {
        viewModel.Dictations.observe(viewLifecycleOwner) {
            dictations = it
            adapter.updateDictation(dictations)
        }

    }

    private fun bindButton() {
        binding.btnAddDictationsFragmentDictations.setOnClickListener {
            val intent = Intent(requireContext(), EditActivity::class.java)
            startForResult.launch(intent)
        }
    }

    private fun bindActivityResult() {
        startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK) {
                val result = it.data?.getSerializableExtra("newDictations") as DictationWord
                val suppressedWord = it.data?.getStringArrayListExtra("suppressedWord")
                viewModel.deleteWordsFromDictation(suppressedWord as List<String>)
                Log.d("DFRAG", suppressedWord.toString())
                viewModel.saveDictation(result.dictation, result.words)
            }
            else {
                Log.d("DictationsFragment", "Error for the result")
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) = DictationsFragment()
    }
}