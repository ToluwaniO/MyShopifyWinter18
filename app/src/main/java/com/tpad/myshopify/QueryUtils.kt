package com.tpad.myshopify

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import org.json.JSONObject
import android.os.AsyncTask.execute



/**
 * Created by oguns on 8/15/2017.
 */
class QueryUtils{
    companion object {
        val LOG_TAG = QueryUtils::class.java.simpleName
        fun getJson(url: String): String?
        {
            val client = OkHttpClient()
            val request = Request.Builder().url(url).build()

            client.newCall(request).execute().use { response -> return response.body()?.string() }
            return null
        }

        fun parseJsonAndGetTotal(jsonString: String): Double
        {
            val json = JSONObject(jsonString)
            //Log.d(LOG_TAG, jsonString.toString())
            val orders = json.getJSONArray("orders")
            //Log.d(LOG_TAG, "orders ${orders.toString()}")
            var total = 0.0

            for (i in 0..orders.length()-1)
            {
                val ordersItem = orders.getJSONObject(i)

                if (ordersItem.has("customer")) {
                    val customer = orders.getJSONObject(i).getJSONObject("customer")
                    Log.d(LOG_TAG, "customer ${customer.toString()}")

                    if (customer.getString("first_name") == "Napoleon" && customer.getString("last_name") == "Batz") {
                        total += orders.getJSONObject(i).getDouble("total_price")
                    }
                }
            }

            return total
        }

        fun getData(url: String): NameTotal
        {
            val json = getJson(url)
            Log.d(LOG_TAG, json.toString())
            var total = 0.0
            json?.let { total = parseJsonAndGetTotal(json) }
            return NameTotal("Napoleon Batz", total)
        }

    }
}