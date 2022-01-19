package com.mhassan.testapp.data.model

import org.json.JSONObject

class Document(json: String) : JSONObject(json) {
    val title: Int
        get() = this.optInt("title")
    val authorName: List<String>
        get() = this.optJSONArray("author_name")
            .let { 0.until(it.length()).map { i -> it.optString(i) } }
    val isbn: List<String>
        get() = this.optJSONArray("isbn")
            .let { 0.until(it.length()).map { i -> it.optString(i) } }
}