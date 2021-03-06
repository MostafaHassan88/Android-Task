package com.mhassan.testapp.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mhassan.androidtask.databinding.DocumentCellBinding
import com.mhassan.androidtask.ui.main.activities.MainActivity
import com.mhassan.testapp.data.model.Document

class MainAdapter(
    private var mainActivity: MainActivity,
    private var documents: List<Document>,
) : RecyclerView.Adapter<MainAdapter.ProductViewHolder>() {

    class ProductViewHolder(b: DocumentCellBinding) : RecyclerView.ViewHolder(b.getRoot()) {
        var binding: DocumentCellBinding = b

        fun bind(mainActivity: MainActivity, document: Document, position: Int) {
            binding.txtBookTitle.text = document.title
            if(document.authorName != null){
                binding.txtBookAuthor.text = document.authorName!!.joinToString(", ")
            }else{
                binding.txtBookAuthor.visibility = View.GONE
            }

            binding.root.setOnClickListener{
                mainActivity.loadDetailsFragment(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder(DocumentCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = documents.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(mainActivity, documents[position], position)

    fun setData(list: List<Document>) {
        documents = list
    }

}