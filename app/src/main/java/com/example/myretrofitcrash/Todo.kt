package com.example.myretrofitcrash

data class Todo(
    val completed: Boolean,
    val id: Int,
    val title: String,
    val userId: Int
)