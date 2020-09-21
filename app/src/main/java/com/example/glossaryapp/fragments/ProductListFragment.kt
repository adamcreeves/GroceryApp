package com.example.glossaryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterProducts
import com.example.glossaryapp.app.Endpoints
import com.example.glossaryapp.models.Product
import com.example.glossaryapp.models.ProductResults
import com.example.glossaryapp.models.SubCategory
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_product_list.view.*

class ProductListFragment : Fragment() {
    var myList: ArrayList<Product> = ArrayList()
    lateinit var adapterProducts: AdapterProducts
    private var subId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            subId = it.getInt(SubCategory.KEY_TO_SUB_ID)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_product_list, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        getData()
        adapterProducts = AdapterProducts(view.context)
        view.recycler_view.adapter = adapterProducts
        view.recycler_view.layoutManager = LinearLayoutManager(activity)


    }

    private fun getData(){
        var request = StringRequest(Request.Method.GET, Endpoints.getProductBySubId(subId),{
            var gson = Gson()
            var productResult = gson.fromJson(it, ProductResults::class.java)
            myList.addAll(productResult.data)
            adapterProducts.setData(myList)
        },
            {

            })
        Volley.newRequestQueue(activity).add(request)
    }

    companion object {
        @JvmStatic
        fun newInstance(subId: Int) =
            ProductListFragment().apply {
                arguments = Bundle().apply {
                    putInt(SubCategory.KEY_TO_SUB_ID, subId)
                }
            }
    }
}