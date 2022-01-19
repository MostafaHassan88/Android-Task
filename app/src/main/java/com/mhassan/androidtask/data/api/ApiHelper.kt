package com.mhassan.testapp.data.api

class ApiHelper(private val apiService: ApiService) {

    fun getProducts(query: String) = apiService.getListOfDocuments(query)

}