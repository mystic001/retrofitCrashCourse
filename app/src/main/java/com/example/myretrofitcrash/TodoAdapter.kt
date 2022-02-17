package com.example.myretrofitcrash

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myretrofitcrash.databinding.StandardtodoBinding

class TodoAdapter: RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {
    object DiffCallBack :DiffUtil.ItemCallback<Todo>(){
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }

    private val differ = AsyncListDiffer(this,DiffCallBack)


    var todos : List<Todo>
        get() = differ.currentList
        set(value) {
            differ.submitList(value)
        }

    inner class TodoViewHolder(val binding: StandardtodoBinding): RecyclerView.ViewHolder(binding.root)



    override fun getItemCount() = todos.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(StandardtodoBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        holder.binding.apply {
            val todo = todos[position]
            textView.text = todo.title
            checkBox.isChecked = todo.completed
        }
    }

}