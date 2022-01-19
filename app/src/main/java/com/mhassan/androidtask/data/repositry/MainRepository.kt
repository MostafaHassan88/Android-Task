package com.mhassan.testapp.data.repositry

import com.mhassan.testapp.data.api.ApiHelper
import io.reactivex.Single

class MainRepository(private val apiHelper: ApiHelper) {
    fun getProducts(): Single<CityList> {
        return apiHelper.getProducts()
    }
}