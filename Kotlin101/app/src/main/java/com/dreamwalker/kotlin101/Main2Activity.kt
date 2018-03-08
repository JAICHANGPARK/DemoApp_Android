package com.dreamwalker.kotlin101

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import com.dreamwalker.kotlin101.Adapter.MainAdapters
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_main2.*
import okhttp3.*
import java.io.IOException

class Main2Activity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        //recycler_view.setBackgroundColor(Color.BLUE)
        recycler_view.hasFixedSize()
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDeco = DividerItemDecoration(this, layoutManager.orientation)
        recycler_view.addItemDecoration(dividerItemDeco)
        recycler_view.layoutManager = layoutManager
//        recycler_view.adapter = MainAdapters()
        fetchJson()
    }
    fun fetchJson() {
        println("Attempting to Fetch JSON")
        val url = "https://api.letsbuildthatapp.com/youtube/home_feed"
        val client = OkHttpClient()
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call?, e: IOException?) {
                println("Failed to execute request")
            }
            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val homeFeed = gson.fromJson(body, HomeFeed::class.java)
                runOnUiThread {
                    recycler_view.adapter = MainAdapters(homeFeed)
                }
                println(body)
            }
        })
    }


}


