package com.target.targetcasestudy.database

import com.google.gson.Gson
import com.target.targetcasestudy.R
import com.target.targetcasestudy.database.dataobjects.Price
import com.target.targetcasestudy.database.dataobjects.Product
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.lang.Exception
import java.net.HttpURLConnection
import java.net.URL
import java.net.UnknownHostException

public suspend fun getResponse(url: String, connectTimeoutMS: Int = 10000, readTimeoutMS: Int = 5000): Response {
    return withContext(Dispatchers.IO) {
        try {
            val url = URL(url)
            val urlConnection = url.openConnection() as HttpURLConnection
            urlConnection.requestMethod = "GET"
            urlConnection.connectTimeout = connectTimeoutMS
            urlConnection.readTimeout = readTimeoutMS
            urlConnection.doOutput = false
            urlConnection.doInput = true
            urlConnection.connect()

            val reader = BufferedReader(InputStreamReader(url.openStream()))
            val sBuilder = StringBuilder()

            reader.forEachLine {
                sBuilder.append(it)
            }

            val jsonString = sBuilder.toString()
            val response = Response(urlConnection.responseCode, null, jsonString)
            urlConnection.disconnect()

            response
        } catch(ex: UnknownHostException) {
            //Catching this as an example that we could set different messages depending on the error
            Response(ResponseCodes.UNKNOWN_HOST_EXCEPTION, R.string.generic_error_message, null)
        } catch (ex: Exception) {
            Response(ResponseCodes.UNKNOWN_ERROR, R.string.generic_error_message, null)
        }

    }
}

public inline fun<reified T> jsonToObject(json: String): T {
    return Gson().fromJson(json, T::class.java)
}

public data class Response(val code: Int, val errorMessageID: Int?, val json: String?)