package com.mhassan.testapp.ui.main.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mhassan.androidtask.databinding.DocumentCellBinding
import com.mhassan.testapp.data.model.Document

class MainAdapter(
    private var documents: List<Document>,
) : RecyclerView.Adapter<MainAdapter.ProductViewHolder>() {

    class ProductViewHolder(b: DocumentCellBinding) : RecyclerView.ViewHolder(b.getRoot()) {
        var binding: DocumentCellBinding = b

        fun bind(document: Document) {
            binding.txtBookTitle.text = document.title
            if(document.authorName != null){
                binding.txtBookAuthor.text = document.authorName!!.joinToString(", ")
            }else{
                binding.txtBookAuthor.visibility = View.GONE
            }

            binding.root.setOnClickListener{
//                val intent = Intent(binding.root.context, ProductDescriptionActivity::class.java)
//                intent.putExtra("document_details", document.toString())
//                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ProductViewHolder(DocumentCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = documents.size

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) =
        holder.bind(documents[position])

    fun setData(list: List<Document>) {
        documents = list
    }

}