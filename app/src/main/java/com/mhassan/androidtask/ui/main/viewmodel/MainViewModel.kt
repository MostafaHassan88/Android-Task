package com.mhassan.androidtask.ui.main.viewmodel

import com.mhassan.androidtask.data.model.DocumentList
import com.mhassan.testapp.data.repositry.MainRepository
import com.mhassan.testapp.utils.Resource
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MainViewModel(private val mainRepository: MainRepository) : ViewModel() {

    private val documents = MutableLiveData<Resource<DocumentList>>()

    init {
        fetchDocumentsByQuery("lord of the rings")
    }

     fun fetchDocumentsByQuery(query: String) {
        documents.postValue(Resource.loading(null))
        // run the following enclosed code on a bg thread
         viewModelScope.launch(Dispatchers.IO) {
            try {
                val documentList = mainRepository.queryListOfDocuments(query)
                documents.postValue(Resource.success(documentList))
            } catch (ex: IOException) {
                ex.printStackTrace()
                documents.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    suspend fun fetchDocumentsByAuthor(query: String) {
        documents.postValue(Resource.loading(null))
        // run the following enclosed code on a bg thread
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val documentList = mainRepository.authorListOfDocuments(query)
                documents.postValue(Resource.success(documentList))
            } catch (ex: IOException) {
                ex.printStackTrace()
                documents.postValue(Resource.error("Something Went Wrong", null))
            }
        }
    }

    suspend fun fetchDocumentsByTitle(query: String) {
        documents.postValue(Resource.loading(null))
        // run the following enclosed code on a bg thread
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val documentList = mainRepository.titleListOfDocuments(query)
                documents.postValue(Resource.success(documentList))
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