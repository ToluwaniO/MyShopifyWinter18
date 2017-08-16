package com.tpad.myshopify

import android.databinding.DataBindingUtil
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.tpad.myshopify.databinding.ActivityTotalBinding

class TotalActivity : AppCompatActivity() {

    val URL = "https://shopicruit.myshopify.com/admin/orders.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_total)
        binder = DataBindingUtil.setContentView(this, R.layout.activity_total)

        GetDataAsync().execute(URL, null, null)
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
