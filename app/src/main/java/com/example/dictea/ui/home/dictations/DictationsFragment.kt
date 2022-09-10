package com.example.dictea.ui.home.dictations

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.dictea.R
import com.example.dictea.databinding.FragmentDictationsBinding
import com.example.dictea.databinding.FragmentWordsBinding
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDictationsBinding.inflate(inflater, container, false)

        binding.btnAddDictationsFragmentDictations.setOnClickListener {
            viewModel.getDictation()
        }

        viewModel.Dictations.observe(viewLifecycleOwner) {
            Log.d("DictationFrag", it.toString())
        }

        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) = DictationsFragment()
    }
}