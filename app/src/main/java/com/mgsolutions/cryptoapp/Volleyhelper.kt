package com.mgsolutions.cryptoapp

import android.content.Context
import com.android.volley.Request
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class Volleyhelper (context: Context) {

    val requestQueue: RequestQueue = Volley.newRequestQueue(context.applicationContext)

    companion object {

        private var instance:Volleyhelper? = null

        fun getInstance(context: Context): Volleyhelper?{

            return if(instance != null){
                instance
            }else{
                instance = Volleyhelper(context)
                instance
            }

        }

    }

    fun <T> addToRequestQueue(req: Request<T>) {
        requestQueue.add(req)
    }


}