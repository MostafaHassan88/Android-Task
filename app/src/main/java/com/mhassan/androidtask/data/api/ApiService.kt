package com.mhassan.testapp.data.api

import com.mhassan.androidtask.data.model.DocumentList

interface ApiService {
    fun queryListOfDocuments(query: String): DocumentList
    fun titleListOfDocuments(query: String): DocumentList
    fun authorListOfDocuments(query: String): DocumentList
}