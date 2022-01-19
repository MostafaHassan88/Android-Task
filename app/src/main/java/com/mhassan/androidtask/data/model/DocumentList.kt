package com.mhassan.androidtask.data.model

import com.mhassan.testapp.data.model.Document
import org.json.JSONObject

class DocumentList(json: String) : JSONObject(json) {
    val documentList: List<Document>?
        get() = this.optJSONArray("docs")
            ?.let { 0.until(it.length()).map { i -> it.optString(i) }?.map { Document(it.toString()) } }
}