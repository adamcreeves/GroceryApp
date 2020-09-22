package com.example.glossaryapp.adapters

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.example.glossaryapp.fragments.ProductListFragment
import com.example.glossaryapp.models.SubCategory

class AdapterFragment(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    var myFragmentList: ArrayList<Fragment> = ArrayList()
    var myTitleList: ArrayList<String> = ArrayList()


    override fun getCount(): Int {
        return myTitleList.size
    }

    override fun getItem(position: Int): Fragment {
        return myFragmentList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return myTitleList[position]
    }

    fun addFragment(subCategory: SubCategory){
        myTitleList.add(subCategory.subName)
        myFragmentList.add(ProductListFragment.newInstance(subCategory.subId))
    }
}