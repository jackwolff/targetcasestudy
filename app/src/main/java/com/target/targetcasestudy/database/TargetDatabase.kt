package com.target.targetcasestudy.database

import com.target.targetcasestudy.database.dataobjects.Product
import com.target.targetcasestudy.database.dataobjects.ProductResponse
import timber.log.Timber
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
//TODO: Inject a path generator
class TargetDatabase
@Inject constructor() {

    private val targetBase = "https://api.target.com/mobile_case_study_deals/v1"
    private val dealsBase = "$targetBase/deals"

    suspend fun getProductList(): JSONResult {
        return get<ProductResponse>(dealsBase)
    }

    suspend fun getProductInfo(id: Int): JSONResult {
        return get<Product>("$dealsBase/$id")
    }

    private suspend inline fun<reified T> get(url: String, connectTimeoutMS: Int = 10000, readTimeoutMS: Int = 5000): JSONResult {
        val response = getResponse(url)
        Timber.d("Got response for $url, response code is ${response.code}")
        return when(response.code) {
            ResponseCodes.SUCCESS -> JSONResult.Success<T>(jsonToObject(response.json!!))
            //Could add more response codes here with
            else -> JSONResult.GenericError("Error", 5)
        }
    }
}

public sealed class JSONResult {
    public data class GenericError(val message: String, val errorCode: Int) : JSONResult()
    data class Success<T>(val value: T) : JSONResult()
}