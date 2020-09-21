package com.example.glossaryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.glossaryapp.fragments.ProductFragment

class AdapterTabView (fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var myFragmentList: ArrayList<Fragment> = ArrayList()
    var myTitleList: ArrayList<String> = ArrayList()

    override fun getCount(): Int {
        return myFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return myFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return myTitleList[position]
    }
    fun dataChange() {
        notifyDataSetChanged()
    }

    fun addFragment(title: String, subId: Int) {
        myFragmentList.add(ProductFragment.newInstance(title, subId))
        myTitleList.add(title)
    }
}