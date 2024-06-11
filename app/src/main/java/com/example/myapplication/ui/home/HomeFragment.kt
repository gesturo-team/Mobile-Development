package com.example.myapplication.ui.home

import android.content.Intent
import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.detection.CameraActivity
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.factory.AuthViewModelFactory
import com.example.myapplication.factory.MainViewModelFactory
import com.example.myapplication.ui.history.HistoryAdapter
import com.example.myapplication.ui.main.MainViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var historyAdapter: HistoryAdapter

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity(), AuthViewModelFactory.getInstance(requireActivity()))[MainViewModel::class.java]

        // TODO: Use the ViewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.btnCamera.setOnClickListener {
            val cameraIntent = Intent(activity, CameraActivity::class.java)
            activity?.startActivity(cameraIntent)
        }

        historyAdapter = HistoryAdapter()
        binding.rvHistory.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = historyAdapter
        }

        mainViewModel.historyResponse.observe(viewLifecycleOwner, Observer { response ->
            if (response?.success == true) {
                historyAdapter.submitList(response.data?.quiz)
            } else {
                // Handle error
            }
        })

        // Muat data riwayat
        mainViewModel.getHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}