package com.example.glossaryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterSubCategory
import com.example.glossaryapp.models.Product
import com.example.glossaryapp.models.ProductData
import com.google.gson.Gson


private const val ARG_PARAM1 = "param1"

class ProductFragment : Fragment() {
    var myList: ArrayList<ProductData> = ArrayList()

    private var subcategoryName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subcategoryName = it.getString(ARG_PARAM1)
            }
//        generateData(subcategoryName!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_product_list_view, container, false)
        init(view)
        generateData(subcategoryName!!)
        return view
    }

    private fun init(view: View) {
        var myAdapter = AdapterSubCategory(view.context, myList)

    }

    private fun generateData(subcategoryName: String) {
        when(subcategoryName){
            "Fried/Barbeque/Combos" -> {

            }
            "Veg Fried/Veg Combos" -> {

            }
            "Fruits/Vegetables" -> {

            }
            "Veg Fried" -> {

            }
            "Fruits" -> {

            }
            "Vegetables" -> {

            }
            "Beef Kababs" -> {

            }
        }

    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, subcategoryName)

                }
            }
    }
}