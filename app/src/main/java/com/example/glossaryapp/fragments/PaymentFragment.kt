package com.example.glossaryapp.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import com.example.glossaryapp.R
import com.example.glossaryapp.activities.AddressActivity
import com.example.glossaryapp.adapters.AdapterShoppingCart
import com.example.glossaryapp.database.DBHelper
import com.example.glossaryapp.models.CartProductData
import kotlinx.android.synthetic.main.activity_shopping_cart.*
import kotlinx.android.synthetic.main.fragment_payment.*
import kotlinx.android.synthetic.main.fragment_payment.view.*

private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class PaymentFragment : Fragment() {
    private var param1: String? = null
    private var param2: String? = null
    lateinit var dbHelper: DBHelper
    var myList: ArrayList<CartProductData> = ArrayList()
    private var adapterShoppingCart: AdapterShoppingCart? = null
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
    ): View? {
        var view = inflater.inflate(R.layout.fragment_payment, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        myList = dbHelper.getProducts()
//        recycler_view_payment.layoutManager = GridLayoutManager(context, 1)

        view.button_payment_credit_card.setOnClickListener{
            Toast.makeText(context, "Credit Card payment Unavailable", Toast.LENGTH_SHORT).show()
        }
        view.button_payment_change_address.setOnClickListener{
            startActivity(Intent(context, AddressActivity::class.java))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            PaymentFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}