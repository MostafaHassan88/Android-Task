package com.mhassan.testapp.data.api

import com.mhassan.androidtask.data.model.DocumentList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ApiServiceImpl : ApiService {

    val baseUrl = "https://openlibrary.org/search.json?"
    val generalQuery = "q="
    val titleQuery = "title="
    val authorQuery = "author="

    // makes a general search by keyword
    override suspend fun queryListOfDocuments(query: String): DocumentList {
        val response = sendGetRequest(URL("$baseUrl$generalQuery${URLEncoder.encode(query, "UTF-8")}"))
        return DocumentList(response)
    }

    // makes a search using a book title
    override suspend fun titleListOfDocuments(query: String): DocumentList {
        val response = sendGetRequest(URL("$baseUrl$titleQuery${URLEncoder.encode(query, "UTF-8")}"))
        return DocumentList(response)
    }
    // makes a search using an author name
    override suspend fun authorListOfDocuments(query: String): DocumentList {
        val response = sendGetRequest(URL("$baseUrl$authorQuery${URLEncoder.encode(query, "UTF-8")}"))
        return DocumentList(response)
    }

    // Method Used for all get Requests
    private fun sendGetRequest(mURL:URL) : String {
        with(mURL.openConnection() as HttpURLConnection) {
            requestMethod = "GET"

            println("URL : $url")
            println("Response Code : $responseCode")

            BufferedReader(InputStreamReader(inputStream)).use {
                val response = StringBuffer()

                var inputLine = it.readLine()
                while (inputLine != null) {
                    response.append(inputLine)
                    inputLine = it.readLine()
                }
                it.close()
                println("Response : $response")
                return response.toString()
            }
        }
    }
}