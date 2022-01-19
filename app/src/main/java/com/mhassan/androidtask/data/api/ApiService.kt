package com.mhassan.testapp.data.api

import com.mhassan.androidtask.data.model.DocumentList

interface ApiService {
    fun getListOfDocuments(query: String): DocumentList
}