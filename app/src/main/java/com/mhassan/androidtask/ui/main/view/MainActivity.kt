package com.mhassan.androidtask.ui.main.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mhassan.testapp.data.api.ApiHelper
import com.mhassan.testapp.data.api.ApiServiceImpl
import com.mhassan.testapp.ui.base.ViewModelFactory
import com.mhassan.testapp.ui.main.adapter.MainAdapter
import com.mhassan.androidtask.ui.main.viewmodel.MainViewModel
import com.mhassan.testapp.utils.Status
import com.mhassan.androidtask.databinding.ActivityMainBinding
import com.mhassan.testapp.data.model.Document
import android.view.Menu
import com.mhassan.androidtask.R
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: MainAdapter
    private lateinit var binding: ActivityMainBinding
    lateinit var searchView: SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        setupUI()
        setupViewModel()
        setupObserver()
    }

    private fun setupUI() {
        binding.rvDocuments.layoutManager = LinearLayoutManager(this)
        adapter = MainAdapter(arrayListOf())
        binding.rvDocuments.addItemDecoration(
            DividerItemDecoration(
                binding.rvDocuments.context,
                (binding.rvDocuments.layoutManager as LinearLayoutManager).orientation
            )
        )
        binding.rvDocuments.adapter = adapter
    }

    private fun setupObserver() {
        mainViewModel.getDocuments().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progressBar.visibility = View.GONE
                    it.data?.let { documentsObj ->
                        renderList(documentsObj.documentList)
                        binding.rvDocuments.visibility = View.VISIBLE
                        binding.errorView.root.visibility = View.GONE
                    }
                }
                Status.LOADING -> {
                    binding.progressBar.visibility = View.VISIBLE
                    binding.rvDocuments.visibility = View.GONE
                    binding.errorView.root.visibility = View.GONE
                }
                Status.ERROR -> {
                    binding.progressBar.visibility = View.GONE
                    binding.rvDocuments.visibility = View.GONE
                    binding.errorView.root.visibility = View.VISIBLE
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

    private fun setupViewModel() {
        mainViewModel = ViewModelProvider(
            this,
            ViewModelFactory(ApiHelper(ApiServiceImpl()))
        ).get(MainViewModel::class.java)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        val searchViewMenuItem: MenuItem = menu!!.findItem(R.id.search)
        searchView = searchViewMenuItem.actionView as SearchView
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchView.setQuery("", false)
                searchView.isIconified = true
                mainViewModel.fetchDocumentsByQuery(query)
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onPrepareOptionsMenu(menu)
    }
}