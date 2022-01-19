package com.mhassan.androidtask.ui.main.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.mhassan.androidtask.databinding.FragmentDocumentDetailsBinding
import com.mhassan.androidtask.ui.main.viewmodel.MainViewModel
import com.mhassan.testapp.data.model.Document
import androidx.recyclerview.widget.GridLayoutManager
import com.mhassan.androidtask.ui.main.activities.MainActivity
import com.mhassan.androidtask.ui.main.adapter.ISBNAdapterprivate
import java.net.URLEncoder


class DocumentDetailsFragment : Fragment() {
    private lateinit var binding: FragmentDocumentDetailsBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: ISBNAdapterprivate

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocumentDetailsBinding.inflate(layoutInflater)
        return binding.root;
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupViewModel()

        arguments?.let { args ->
            mainViewModel.lastLoadedDocument.documentList?.let { list ->
                loadViewData(
                    list.get(args.getInt("document_index"))
                )
            }
        }
    }

    fun loadViewData(document: Document) {
        binding.txtBookTitle.text = document.title
        if (document.authorName != null) {
            binding.txtBookAuthor.text = document.authorName!!.joinToString(", ")
        } else {
            binding.txtBookAuthor.visibility = View.GONE
        }

        binding.rvISBNs.setLayoutManager(GridLayoutManager(activity, 2))
        document.isbn?.let {
            adapter = ISBNAdapterprivate(
                activity as MainActivity, it.subList(
                    0, if (it.size > 5) {
                        5
                    } else {
                        it.size
                    }
                )
            )
            binding.rvISBNs.adapter = adapter
        }

        binding.txtBookTitle.setOnClickListener {
            mainViewModel.fetchDocumentsByQuery(
                "title=${
                    URLEncoder.encode(
                        binding.txtBookTitle.text.toString(),
                        "UTF-8"
                    )
                }"
            )
        }

        binding.txtBookAuthor.setOnClickListener {
            document.authorName?.let { list ->
                mainViewModel.fetchDocumentsByQuery(
                    "author=${
                        URLEncoder.encode(
                            list.get(0),
                            "UTF-8"
                        )
                    }"
                )
            }
        }
    }

    private fun setupViewModel() {
        activity?.let { mainViewModel = ViewModelProvider(it)[MainViewModel::class.java] }
    }
}