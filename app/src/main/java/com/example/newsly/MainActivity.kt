package com.example.newsly

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.StackView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    lateinit var adapter : NewsAdapter
    lateinit var newsList : RecyclerView
    private var articles = mutableListOf<Article>()
    var pageNum = 1
    var totalResults = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        newsList = findViewById(R.id.newsList)

//        val layoutManager = LinearLayoutManager(this)
        adapter = NewsAdapter(this@MainActivity, articles)
        newsList.adapter = adapter
        val layoutManager = LinearLayoutManager(this@MainActivity)
        newsList.layoutManager = layoutManager

        if(totalResults > layoutManager.itemCount && layoutManager.findFirstVisibleItemPosition() >= layoutManager.itemCount - 5){
            pageNum++;
            getNews()
        }

        getNews()

    }

    private fun getNews() {
        Log.d("ISHANK", "Request sent for $pageNum")
        val news: Call<News> = NewsService.newsInstance.getHeadlines("in", pageNum)
        news.enqueue(object : Callback<News> {
            override fun onResponse(call: Call<News>, response: Response<News>) {
                val news = response.body()
                if (news != null) {
//                    Log.d("ISHANK", news.toString())
                    totalResults = news.totalResults
                    articles.addAll(news.articles)
                    adapter.notifyDataSetChanged()

                }
            }

            override fun onFailure(call: Call<News>, t: Throwable) {
                Log.d("ISHANK", "Error in Fetching News", t)
            }

        })
    }
}