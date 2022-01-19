package com.mhassan.androidtask.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mhassan.androidtask.databinding.FragmentErrorBinding
import com.mhassan.androidtask.ui.main.viewmodel.MainViewModel
import com.mhassan.testapp.data.model.Document
import com.mhassan.testapp.ui.main.adapter.MainAdapter
import com.mhassan.testapp.utils.Status

class ErrorFragment : Fragment() {
    private lateinit var binding: FragmentErrorBinding
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentErrorBinding.inflate(layoutInflater)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        setupViewModel()

        binding.btnReload.setOnClickListener{
            mainViewModel.reloadLatestQuery()
        }
    }

    private fun setupViewModel() {
        activity?.let { mainViewModel = ViewModelProvider(it)[MainViewModel::class.java] }
    }

}