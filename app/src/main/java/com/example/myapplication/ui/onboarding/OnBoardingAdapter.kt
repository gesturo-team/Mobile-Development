package com.example.myapplication.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter

class OnBoardingAdapter(data: ArrayList<Fragment>, fragment: FragmentManager, cycle: Lifecycle) : FragmentStateAdapter(fragment, cycle) {
    private val showOnboard = data

    override fun getItemCount(): Int {
        return showOnboard.size
    }

    override fun createFragment(position: Int): Fragment {
        return showOnboard[position]
    }

}