package com.example.netconnectionchecker

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

class ConnectionCheck {

    fun checkInternet(listener:(connected:Boolean)->Unit){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                Socket().use {
                    it.connect(InetSocketAddress(HOST, PORT), TIMEOUT)
                }
                withContext(Dispatchers.Main){
                    listener(true)
                }
            }catch (e:IOException){
                withContext(Dispatchers.Main){
                    listener(false)
                }
            }
        }
    }

    suspend fun checkInternet():Boolean{
        return withContext(Dispatchers.IO){
            try {
                Socket().use {
                    it.connect(InetSocketAddress(HOST, PORT), TIMEOUT)
                }
                return@withContext true
            }catch (e:IOException){
                return@withContext false
            }
        }
    }



    companion object{
        private const val HOST="8.8.8.8"
        private const val PORT = 53
        private const val TIMEOUT = 1500
    }
}