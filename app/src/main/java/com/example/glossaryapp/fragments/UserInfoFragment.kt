package com.example.glossaryapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.glossaryapp.R
import com.example.glossaryapp.helpers.SessionManager
import kotlinx.android.synthetic.main.fragment_user_info.view.*


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class UserInfoFragment : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

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

        var view = inflater.inflate(R.layout.fragment_user_info, container, false)
        init(view)
        return view
    }

    private fun init(view: View) {
        var sessionManager = SessionManager(context!!)
        view.text_view_user_info_first_name.text = sessionManager.getFirstName()
        view.text_view_user_info_email.text = sessionManager.getEmail()
        view.text_view_user_info_mobile.text = sessionManager.getMobile()
        view.button_change_password.setOnClickListener{
//            startActivity(Intent(this, ))
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserInfoFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}