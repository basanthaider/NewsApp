package com.example.newsapp

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.example.newsapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    //https://newsapi.org
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadNews()
        binding.swipeRefresh.setOnRefreshListener {
            loadNews()
        }
    }
    private fun loadNews(){
        val retrofit = Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val callableInterface = retrofit.create(NewsCallable::class.java)
        callableInterface.getNews().enqueue(object:Callback<News>{
            override fun onResponse(call: Call<News>, respones: Response<News>) {
                val news = respones.body()
                val articles = news?.articles
                articles?.removeAll {
                    it.title =="[Removed]"
                }
                Log.d("articles","Articles:$articles")
                if (articles != null) {
                    showNews(articles)
                    binding.progress.isVisible=false
                    binding.swipeRefresh.isRefreshing = false

                }
            }

            override fun onFailure(p0: Call<News>, t: Throwable) {
                Log.d("trace","Error:${t.localizedMessage}")
                binding.progress.isVisible=false
                binding.swipeRefresh.isRefreshing = false

            }

        })

    }
    private fun showNews(articles:ArrayList<Article>){
        val adapter = NewsAdapter(this,articles)
        binding.newsList.adapter = adapter
    }
    override fun onBackPressed() {
        val exit =ExitDialog()
        exit.isCancelable =false
        exit.show(supportFragmentManager,null)
    }
}