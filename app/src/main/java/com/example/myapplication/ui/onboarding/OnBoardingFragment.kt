package com.example.myapplication.ui.onboarding

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.myapplication.databinding.FragmentOnBoardingBinding

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class OnBoardingFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    private var _binding: FragmentOnBoardingBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOnBoardingBinding.inflate(inflater, container, false)

        val onBoard = arrayListOf(
            FirstOnBoarding(),
            SecondOnBoarding(),
            ThirdOnBoarding()
        )

        val boardAdapter = OnBoardingAdapter(onBoard, requireActivity().supportFragmentManager, lifecycle)
        binding.vpOnBoarding.adapter = boardAdapter

        // Return the root view of the binding
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}
