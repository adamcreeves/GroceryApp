package com.example.glossaryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.glossaryapp.fragments.ProductFragment

class AdapterProductFragment(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var myFragmentList: ArrayList<Fragment> = ArrayList()
    var myTitleList: ArrayList<String> = ArrayList()


    override fun getCount(): Int {
        return myFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return myFragmentList.get(position)
    }

    fun addFragment(productName: String) {
        myFragmentList.add(ProductFragment.newInstance(productName))
        myTitleList.add(productName)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return myTitleList.get(position)

    }
}