//package com.example.myapplication.ui.profile
//
//import android.os.Bundle
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import androidx.fragment.app.Fragment
//import androidx.lifecycle.ViewModelProvider
//import com.example.myapplication.databinding.FragmentProfileBinding
//import com.example.myapplication.ui.main.MainViewModel
//import com.example.myapplication.factory.AuthViewModelFactory
//
//class ProfileFragment : Fragment() {
//    private var _binding: FragmentProfileBinding? = null
//    private val binding get() = _binding!!
//
//    private lateinit var mainViewModel: MainViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        mainViewModel = ViewModelProvider(requireActivity(), AuthViewModelFactory.getInstance(requireActivity()))[MainViewModel::class.java]
//    }
//
//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View {
//        _binding = FragmentProfileBinding.inflate(inflater, container, false)
//        return binding.root
//    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
//            binding.txtFullName.text = user.fullName
//            binding.txtEmail.text = user.email
//        }
//
//        binding.btnLogout.setOnClickListener{
//            mainViewModel.logout()
//            requireActivity().finish()
//        }
//    }
//
//    override fun onDestroyView() {
//        super.onDestroyView()
//        _binding = null
//    }
//}

package com.example.myapplication.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.myapplication.R
import com.example.myapplication.databinding.FragmentProfileBinding
import com.example.myapplication.ui.main.MainViewModel
import com.example.myapplication.factory.AuthViewModelFactory

class ProfileFragment : Fragment() {
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity(), AuthViewModelFactory.getInstance(requireActivity()))[MainViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.getSession().observe(viewLifecycleOwner) { user ->
            binding.txtFullName.text = user.fullName
            binding.txtEmail.text = user.email
        }

        binding.btnLogout.setOnClickListener {
            mainViewModel.logout()
            requireActivity().finish()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

