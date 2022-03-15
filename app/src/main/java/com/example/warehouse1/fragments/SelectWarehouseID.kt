package com.example.warehouse1.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.example.warehouse1.R
import com.example.warehouse1.dashboard.Dashboard
class SelectWarehouseID : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view= inflater.inflate(R.layout.fragment_first, container, false)
        view.findViewById<Button>(R.id.bt_submit).setOnClickListener{
            startActivity(Intent(view.context, Dashboard::class.java))
        }
        return view
    }
}