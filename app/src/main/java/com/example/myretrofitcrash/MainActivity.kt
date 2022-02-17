package com.example.myretrofitcrash

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myretrofitcrash.databinding.ActivityMainBinding
import retrofit2.HttpException
import java.io.IOException


const val TAG = "MainActivity"
class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var todoAdapter : TodoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setUpRecyclerView()
        Log.d(TAG,"This is na error occuring for the reason")

        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getTodos()
            }catch (e: IOException){
                Log.e(TAG,"This is na error occuring for the reason ${e.message}")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }catch (http: HttpException){
                Log.e(TAG,"This is na error occurring for the reason ${http.message}")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }

            if(response.isSuccessful && response.body() != null){
                todoAdapter.todos = response.body()!!
                Log.d(TAG,"Was succesful with size ${todoAdapter.todos.size}")
            }else{
                Log.e(TAG,"Wasnt succesful")
            }
            binding.progressBar.isVisible = false

        }
    }

    private fun setUpRecyclerView() = binding.recyclerView.apply {
        todoAdapter = TodoAdapter()
        adapter = todoAdapter
        layoutManager = LinearLayoutManager(this@MainActivity)
    }
}