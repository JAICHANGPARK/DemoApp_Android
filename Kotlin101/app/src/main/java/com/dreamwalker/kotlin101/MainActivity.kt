package com.dreamwalker.kotlin101

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //val listView = findViewById<ListView>(R.id.list_view)
        list_view.adapter = MyAdapter(this)
    }

    private class MyAdapter(context: Context) : BaseAdapter() {

        private val mContxt: Context
        private val names = arrayListOf<String>(" name 1", " Jobs ", "Tim Cook")

        init {
            mContxt = context
        }

        override fun getCount(): Int {
            return names.size
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            val layoutInflateer = LayoutInflater.from(mContxt)
            val views = layoutInflateer.inflate(R.layout.item_rows, parent, false)
            val textLow = views.findViewById<TextView>(R.id.textView2)
            val nameText = views.findViewById<TextView>(R.id.textView)
            textLow.text = "Row number : $position"
            nameText.text = names.get(position)
            return views
        }

        override fun getItem(position: Int): Any {
            return "Test String"
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }
    }
}
