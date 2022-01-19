package com.mhassan.testapp.data.api

import com.mhassan.androidtask.data.model.DocumentList

class ApiHelper(private val apiService: ApiService) {

    suspend fun queryListOfDocuments(query: String) = apiService.queryListOfDocuments(query)
    suspend fun titleListOfDocuments(query: String) = apiService.titleListOfDocuments(query)
    suspend fun authorListOfDocuments(query: String) = apiService.authorListOfDocuments(query)
}