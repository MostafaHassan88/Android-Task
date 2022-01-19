package com.mhassan.androidtask.ui.main.viewmodel

import com.mhassan.androidtask.data.model.DocumentList
import com.mhassan.testapp.data.repositry.MainRepository
import com.mhassan.testapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import androidx.lifecycle.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.w3c.dom.Document
import java.net.URLEncoder

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val documents = MutableLiveData<Resource<DocumentList>>()
    lateinit var lastLoadedDocument : DocumentList
    var latestQuery: String = "q=${URLEncoder.encode("lord of the rings", "UTF-8")}"
    init {
        // load random query to populate the list view with data
        fetchDocumentsByQuery(latestQuery)
    }

    fun reloadLatestQuery(){
        fetchDocumentsByQuery(latestQuery)
    }

     fun fetchDocumentsByQuery(query: String) {
        documents.postValue(Resource.loading(null))
        // run the following enclosed code on a bg thread
         viewModelScope.launch(Dispatchers.IO) {
            try {
                lastLoadedDocument = mainRepository.queryListOfDocuments(query)
                documents.postValue(Resource.success(lastLoadedDocument))
            } catch (ex: IOException) {
                ex.printStackTrace()
                documents.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
    }

    fun getDocuments(): LiveData<Resource<DocumentList>> {
        return documents
    }

}