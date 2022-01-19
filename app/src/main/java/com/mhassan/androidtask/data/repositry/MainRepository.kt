package com.mhassan.testapp.data.repositry

import com.mhassan.androidtask.data.model.DocumentList
import com.mhassan.testapp.data.api.ApiHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

class MainRepository(private val apiHelper: ApiHelper) {

    suspend fun queryListOfDocuments(query: String): DocumentList {
        return apiHelper.queryListOfDocuments(query)
    }
}