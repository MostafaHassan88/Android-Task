package com.mhassan.testapp.data.api

import com.mhassan.androidtask.data.model.DocumentList
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

class ApiServiceImpl : ApiService {

    val baseUrl = "https://openlibrary.org/search.json?"

    // makes a general search by keyword
    override suspend fun queryListOfDocuments(query: String): DocumentList {
        val response = sendGetRequest(URL("$baseUrl$query"))
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