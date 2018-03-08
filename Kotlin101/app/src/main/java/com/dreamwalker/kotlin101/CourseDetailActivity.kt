package com.dreamwalker.kotlin101

import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_course_detail.*

class CourseDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_course_detail)

        webView.setBackgroundColor(Color.BLUE)
        val linkUrl = intent.getStringExtra(SecondActivity.CourseDetailViewHolder.LINK_URL_KEY)
        println(linkUrl)
        webView.settings.javaScriptEnabled = true
        webView.settings.loadWithOverviewMode = true
        webView.settings.useWideViewPort = true
        webView.loadUrl(linkUrl)
    }
}
