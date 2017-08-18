package com.tpad.myshopify

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tpad.myshopify.databinding.ActivityTotalBinding
import android.net.NetworkInfo
import android.content.Context.CONNECTIVITY_SERVICE
import android.net.ConnectivityManager
import android.widget.Toast


class TotalActivity : AppCompatActivity() {

    val URL = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_total)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_total)

        if(isConnected()) {
            GetDataAsync().execute(URL, null, null)
        }
        else
        {
            Toast.makeText(this, getString(R.string.no_network), Toast.LENGTH_LONG).show()
        }
    }

    fun isConnected() : Boolean
    {
        val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    class GetDataAsync: AsyncTask<String, Void, NameTotal>() {
        override fun doInBackground(vararg url: String): NameTotal? {
            val napoleonData = QueryUtils.getData(url[0])
            return napoleonData
        }

        override fun onPostExecute(result: NameTotal?) {
            //super.onPostExecute(result)
            setBinderData(result)
        }
    }

    companion object {
        lateinit var binder: ActivityTotalBinding
        fun setBinderData(data: NameTotal?)
        {
            if(data != null)
                binder.customer = data
            else
                binder.customer = NameTotal("Napoleon Batz", 0.0)
        }
    }
}
