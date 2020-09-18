package com.example.glossaryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.glossaryapp.fragments.ProductFragment

class AdapterProductFragment(fm: FragmentManager) : FragmentPagerAdapter(fm) {
    var mFragmentList: ArrayList<Fragment> = ArrayList()
    var mTitleList: ArrayList<String> = ArrayList()


    override fun getCount(): Int {
        return mFragmentList.size
    }

    override fun getItem(position: Int): Fragment {
        return mFragmentList.get(position)
    }

    fun addFragment(productName: String) {
        mFragmentList.add(ProductFragment.newInstance(productName))
        mTitleList.add(productName)
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return mTitleList.get(position)

    }
}