package com.dreamwalker.kotlin101

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dreamwalker.kotlin101.Adapter.MainViewHolder
import com.google.gson.GsonBuilder
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_second.*
import kotlinx.android.synthetic.main.item_lesson.view.*
import okhttp3.*
import java.io.IOException

class SecondActivity : AppCompatActivity() {

    //https://api.letsbuildthatapp.com/youtube/course_detail?id=1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        val navBarTitle = intent.getStringExtra(MainViewHolder.VIDEO_TITLE_KEY)
        //val videoId = intent.getIntExtra(MainViewHolder.VIDEO_ID_KEY, -1)
        supportActionBar?.title = navBarTitle

        //recycler_view.setBackgroundColor(Color.RED)
        recycler_view.hasFixedSize()
        val layoutManager = LinearLayoutManager(this)
        val dividerItemDeco = DividerItemDecoration(this, layoutManager.orientation)
        recycler_view.addItemDecoration(dividerItemDeco)
        recycler_view.layoutManager = layoutManager
        //recycler_view.adapter = CourseDetailAdapter()

        //val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId

        //println(courseDetailUrl)

        fetchJSON()


    }

    fun fetchJSON() {

        val videoId = intent.getIntExtra(MainViewHolder.VIDEO_ID_KEY, -1)
        val courseDetailUrl = "https://api.letsbuildthatapp.com/youtube/course_detail?id=" + videoId

        val client = OkHttpClient()
        val request = Request.Builder().url(courseDetailUrl).build()
        client.newCall(request).enqueue(object : Callback {

            override fun onResponse(call: Call?, response: Response?) {
                val body = response?.body()?.string()
                val gson = GsonBuilder().create()
                val courseLessons = gson.fromJson(body, Array<CourseLesson>::class.java)

                runOnUiThread {
                    recycler_view.adapter = CourseDetailAdapter(courseLessons)
                }

                println(body)
            }

            override fun onFailure(call: Call?, e: IOException?) {
            }
        })
    }

    private class CourseDetailAdapter(val courseLesson: Array<CourseLesson>) : RecyclerView.Adapter<CourseDetailViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): CourseDetailViewHolder {
            val layoutInflater = LayoutInflater.from(parent?.context)
            val view = layoutInflater.inflate(R.layout.item_lesson, parent, false)
            return CourseDetailViewHolder(view)
        }

        override fun onBindViewHolder(holder: CourseDetailViewHolder?, position: Int) {
            val cl = courseLesson.get(position)
            holder?.customViews?.textViewCLTitle?.text = cl.name
            holder?.customViews?.textViewDuration?.text = cl.duration
            val imgUrl = cl.imageUrl
            val imgView = holder?.customViews?.imageViewCourseLesson
            Picasso.with(holder?.customViews?.context).load(imgUrl).into(imgView)
            holder?.cL = cl
        }
        override fun getItemCount(): Int {
            return courseLesson.size
        }
    }

    class CourseDetailViewHolder(val customViews: View, var cL: CourseLesson? = null) : RecyclerView.ViewHolder(customViews) {
        companion object {
            val LINK_URL_KEY = "LINK_URL"
        }
        init {
            customViews.setOnClickListener {
                Log.e("TAG", " " + cL?.link)
                val intent = Intent(customViews.context, CourseDetailActivity::class.java)
                intent.putExtra(LINK_URL_KEY, cL?.link)
                customViews.context.startActivity(intent)
            }
        }
    }
}
