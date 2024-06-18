package com.example.myapplication.ui.home

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication.databinding.FragmentHomeBinding
import com.example.myapplication.detection.CameraActivity
import com.example.myapplication.factory.AuthViewModelFactory
import com.example.myapplication.factory.MainViewModelFactory
import com.example.myapplication.ui.history.HistoryAdapter
import com.example.myapplication.ui.main.MainViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var historyAdapter: HistoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainViewModel = ViewModelProvider(requireActivity(), MainViewModelFactory.getInstance(requireActivity()))[MainViewModel::class.java]

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

        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding.tvName.text = user.fullName
        }

        historyAdapter = HistoryAdapter()
        binding.rvHistory.apply {
            layoutManager = NonScrollableLinearLayoutManager(context)
            adapter = historyAdapter
            if (this.adapter?.itemCount == null) {
                binding.rvHistory.visibility = View.GONE
                binding.imgHistory.visibility = View.VISIBLE
            } else {
                binding.rvHistory.visibility = View.VISIBLE
                binding.imgHistory.visibility = View.GONE
            }
        }

        mainViewModel.historyResponse.observe(viewLifecycleOwner) { response ->
            if (response?.success == true) {
                val setData = response.data?.quiz?.take(10)
                historyAdapter.submitList(setData)
            } else {
                //handle error
            }
        }

        mainViewModel.isLoading.observe(viewLifecycleOwner) {isLoading ->
            if (isLoading) {
                binding.progressHistory.visibility = View.VISIBLE
            } else {
                binding.progressHistory.visibility = View.GONE
            }
        }

        //load histories
        mainViewModel.getHistory()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

class NonScrollableLinearLayoutManager(context: Context) : LinearLayoutManager(context) {
    override fun canScrollVertically(): Boolean {
        return false //disable vertical scrolling
    }
}