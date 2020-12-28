package com.example.carsmanagementapp.ui.prognosis

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.carsmanagementapp.R

class PrognosisFragment : Fragment() {

    companion object {
        fun newInstance() = PrognosisFragment()
    }

    private lateinit var viewModel: PrognosisViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.prognosis_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PrognosisViewModel::class.java)
        // TODO: Use the ViewModel
    }

}