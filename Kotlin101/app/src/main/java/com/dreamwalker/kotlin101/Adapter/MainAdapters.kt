package com.dreamwalker.kotlin101.Adapter

import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.dreamwalker.kotlin101.HomeFeed
import com.dreamwalker.kotlin101.R
import com.dreamwalker.kotlin101.SecondActivity
import com.dreamwalker.kotlin101.Video
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_main.view.*

/**
 * @author JAICHANGPARK AKA DREAMWALKER
 * Created by 2E313JCP on 2018-03-08.
 */


class MainViewHolder(val v: View, var video: Video? = null) : RecyclerView.ViewHolder(v) {

    companion object {
        val VIDEO_TITLE_KEY = "VIDEO_TITLE"
        val VIDEO_ID_KEY = "VIDEO_ID"
    }

    init {
        v.setOnClickListener {
            println("")
            Toast.makeText(v.context, "test", Toast.LENGTH_SHORT).show()
            val intent = Intent(v.context, SecondActivity::class.java)
            intent.putExtra(VIDEO_TITLE_KEY, video?.name)
            intent.putExtra(VIDEO_ID_KEY, video?.id)
            v.context.startActivity(intent)
        }
    }
}

class MainAdapters(val homeFeed: HomeFeed) : RecyclerView.Adapter<MainViewHolder>() {

    val videoTitles = listOf<String>("First Title", "Second Title", "3rd")

    override fun onBindViewHolder(holder: MainViewHolder?, position: Int) {

        val video = homeFeed.videos.get(position)
        val thumbnailImageView = holder?.v?.imageViewPanel
        val channelImageView = holder?.v?.imageView2
        holder?.v?.text_video_title?.text = video.name
        holder?.v?.textChannelname?.text = video.channel.name

        Picasso.with(holder?.v?.context).load(video.imageUrl).into(thumbnailImageView)
        Picasso.with(holder?.v?.context).load(video.channel.profileImageUrl).into(channelImageView)

        holder?.video = video

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): MainViewHolder {
        val lil = LayoutInflater.from(parent?.context)
        val cellForRow = lil.inflate(R.layout.item_main, parent, false)
        return MainViewHolder(cellForRow)
    }

    override fun getItemCount(): Int {
        //return videoTitles.size
        return homeFeed.videos.count()
    }

}