package com.mudio.movies.DataRetrievers

import android.util.Log
import com.mudio.movies.TempDebugClass
import okhttp3.*
import java.io.IOException

class OkHttpDataRetriever{

    private var result = ""
    private var finished = false

    companion object {
        val FAILED = "failed"
    }

    fun getResult(Url: String): String{
        run(Url)
        while(!finished){}
        return result
    }

    private fun run(Url: String) {
        val request = Request.Builder()
                .url(Url)
                .build()

        OkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                Log.e("OkHttpError","Failed to get Data")
                result = FAILED
                finished = true
            }

            override fun onResponse(call: Call, response: Response) {
                Log.e("OkHttp","Succeeded")
                if (!response.isSuccessful){result = FAILED}

                result = response.body()!!.string()

                Log.e("Url = ",Url)
                Log.e("JsonString[${TempDebugClass.instance.httpResultCount}]",result)
                finished = true
                ++TempDebugClass.instance.httpResultCount
            }
        })
    }
}
