package com.example.glossaryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.glossaryapp.R
import com.example.glossaryapp.adapters.AdapterProducts
import com.example.glossaryapp.models.ProductDataItem
import com.example.glossaryapp.models.ProductResults
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_product_list_view.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class ProductFragment : Fragment() {

    private var title: String? = null
    private var subId: Int? = null
    var myList: ArrayList<ProductDataItem> = ArrayList()
    var adapterProducts: AdapterProducts? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            title = it.getString(ARG_PARAM1)
            subId = it.getInt(ARG_PARAM2)
            }
            generateData(subId!!)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        var view = inflater.inflate(R.layout.fragment_product_list_view, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        adapterProducts = AdapterProducts(activity!!, myList)
        view.recycler_view_fragment.layoutManager = LinearLayoutManager(activity!!)
        view.recycler_view_fragment.adapter = adapterProducts
    }

    private fun generateData(subId: Int) {
        var url = "https://grocery-second-app.herokuapp.com/api/products/sub/$subId"
        var requestQueue = Volley.newRequestQueue(activity!!)
        var request = StringRequest(Request.Method.GET, url, Response.Listener {
            var gson = Gson()
            var productResults = gson.fromJson(it, ProductResults::class.java)
            myList.addAll(productResults.data)
            adapterProducts?.dataChange()
        },
        Response.ErrorListener {

        })
        requestQueue.add(request)
    }

    companion object {

        @JvmStatic
        fun newInstance(title: String, subId: Int) =
            ProductFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, title)
                    putInt(ARG_PARAM2, subId)

                }
            }
    }
}