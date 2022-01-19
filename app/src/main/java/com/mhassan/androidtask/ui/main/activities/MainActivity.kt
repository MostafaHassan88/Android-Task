package com.mhassan.androidtask.ui.main.activities

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
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.fragment.app.FragmentTransaction
import com.mhassan.androidtask.ui.main.view.DocumentDetailsFragment
import com.mhassan.androidtask.ui.main.view.DocumentListFragment
import com.mhassan.androidtask.ui.main.view.ErrorFragment
import java.net.URLEncoder


class MainActivity : AppCompatActivity() {

    private lateinit var mainViewModel: MainViewModel
    private lateinit var binding: ActivityMainBinding
    lateinit var searchView: SearchView
    lateinit var currentFragment: Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupViewModel()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.getRoot())

        currentFragment = DocumentListFragment()
        val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
        ft.replace(R.id.fragmentConatiner, currentFragment)
        ft.commit()

        setupObserver()
    }

    private fun setupObserver() {
        mainViewModel.getDocuments().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {

                }
                Status.LOADING -> {
                    replaceFragment(DocumentListFragment())
                }
                Status.ERROR -> {
                    replaceFragment(ErrorFragment())
                }
            }
        })
    }

   fun replaceFragment(fragment: Fragment){
       if(currentFragment != fragment) {
           val ft: FragmentTransaction = supportFragmentManager.beginTransaction()
           ft.replace(R.id.fragmentConatiner, fragment)
           if (currentFragment != null) {
               ft.addToBackStack("mainActivity")
           }
           ft.commit()
           currentFragment = fragment
       }
   }

    fun loadDetailsFragment(documentIndex: Int){
        val detailsFragment = DocumentDetailsFragment()
        val args = Bundle()
        args.putInt("document_index", documentIndex)
        detailsFragment.setArguments(args)
        replaceFragment(detailsFragment)
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
                mainViewModel.fetchDocumentsByQuery("q=${URLEncoder.encode(query, "UTF-8")}")
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return super.onPrepareOptionsMenu(menu)
    }
}