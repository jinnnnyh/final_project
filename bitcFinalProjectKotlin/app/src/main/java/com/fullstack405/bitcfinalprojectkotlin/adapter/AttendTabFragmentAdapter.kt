package com.fullstack405.bitcfinalprojectkotlin.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.fullstack405.bitcfinalprojectkotlin.fragments.attend.AttendAllFragment
import com.fullstack405.bitcfinalprojectkotlin.fragments.attend.AttendCompleteFragment
import com.fullstack405.bitcfinalprojectkotlin.fragments.attend.AttendNoneFragment

class AttendTabFragmentAdapter(private val fragmentActivity: FragmentActivity):
    FragmentStateAdapter(fragmentActivity) {
        var fragment = listOf<Fragment>(AttendAllFragment(),AttendCompleteFragment(),AttendNoneFragment())

        override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }
}