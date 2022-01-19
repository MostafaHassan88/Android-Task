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
import com.mhassan.androidtask.databinding.FragmentDocumentListBinding
import com.mhassan.androidtask.ui.main.activities.MainActivity
import com.mhassan.androidtask.ui.main.viewmodel.MainViewModel
import com.mhassan.testapp.data.model.Document
import com.mhassan.testapp.ui.main.adapter.MainAdapter
import com.mhassan.testapp.utils.Status

class DocumentListFragment : Fragment() {
    private lateinit var binding: FragmentDocumentListBinding
    private lateinit var adapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocumentListBinding.inflate(layoutInflater)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        binding.rvDocuments.layoutManager = LinearLayoutManager(context)
        adapter = MainAdapter(activity as MainActivity, arrayListOf())
        binding.rvDocuments.addItemDecoration(
            DividerItemDecoration(
                binding.rvDocuments.context,
                (binding.rvDocuments.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvDocuments.adapter = adapter
    }

    private fun setupViewModel() {
        activity?.let { mainViewModel = ViewModelProvider(it)[MainViewModel::class.java] }
    }

    private fun setupObserver() {
        mainViewModel.getDocuments().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { documentsObj ->
                        renderList(documentsObj.documentList)
                        binding.rvDocuments.visibility = View.VISIBLE
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvDocuments.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvDocuments.visibility = View.GONE
                }
            }
        })
    }

    private fun renderList(documents: List<Document>?) {
        documents?.let {
            adapter.setData(documents)
            adapter.notifyDataSetChanged()
        }
    }

}