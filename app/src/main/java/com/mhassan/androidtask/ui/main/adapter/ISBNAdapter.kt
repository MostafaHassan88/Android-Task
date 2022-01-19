package com.mhassan.androidtask.ui.main.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mhassan.androidtask.databinding.IsbnCellBinding
import android.graphics.BitmapFactory

import android.graphics.Bitmap
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.mhassan.androidtask.ui.main.activities.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class ISBNAdapterprivate(
    private var mainActivity: MainActivity,
    var isbnList: List<String>,
) : RecyclerView.Adapter<ISBNAdapterprivate.ISBNViewHolder>() {

    class ISBNViewHolder(b: IsbnCellBinding) : RecyclerView.ViewHolder(b.getRoot()) {
        var binding: IsbnCellBinding = b

        fun bind(mainActivity: MainActivity, isbn: String, position: Int) {
            binding.txtISBN.text = isbn
            mainActivity.lifecycleScope.launch(Dispatchers.IO){
               getBitmapFromURL("https://covers.openlibrary.org/b/isbn/$isbn-M.jpg")?.let {
                   bitmap ->
                   mainActivity.runOnUiThread{
                       binding.ivBookCover.setImageBitmap(bitmap)
                   }
               }
            }
        }

        fun getBitmapFromURL(src: String?): Bitmap? {
            Log.i("getBitmapFromURL", "getBitmapFromURL $src")
            return try {
                val url = URL(src)
                val connection: HttpURLConnection = url
                    .openConnection() as HttpURLConnection
                connection.setDoInput(true)
                connection.connect()
                val input: InputStream = connection.getInputStream()
                BitmapFactory.decodeStream(input)
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ISBNViewHolder(IsbnCellBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun getItemCount(): Int = isbnList.size

    override fun onBindViewHolder(holder: ISBNViewHolder, position: Int) =
        holder.bind(mainActivity, isbnList[position], position)

    fun setData(list: List<String>) {
        isbnList = list
    }
}