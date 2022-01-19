package com.mhassan.testapp.data.api

import com.mhassan.androidtask.data.model.DocumentList

interface ApiService {
    suspend fun queryListOfDocuments(query: String): DocumentList
    suspend fun titleListOfDocuments(query: String): DocumentList
    suspend fun authorListOfDocuments(query: String): DocumentList
}