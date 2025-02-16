package com.pi.todosc40

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.pi.todosc40.database.entity.Todo
import com.pi.todosc40.databinding.ItemTodoBinding

class TodosAdapter(var todosList: List<Todo>) : Adapter<TodosAdapter.TodosViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodosViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context)
            , parent, false)
        return TodosViewHolder(binding = binding)
    }


    override fun onBindViewHolder(holder: TodosViewHolder, position: Int) {
        val todo = todosList[position]
        holder.binding.todoTitleTv.text = todo.title
        holder.binding.todoDescriptionTv.text = todo.description
        getStatusStyle(holder,todo)
        holder.binding.leftView.setOnClickListener {
            listener?.onDelete(todo)
        }
        Log.e("onBindViewHolder", "BINDING DATA OF TODO: ${todo.id}")

        holder.binding.doneClick.setOnClickListener{
            listener?.onDoneClick(todo)
        }
        holder.binding.clItem.setOnClickListener{
            listener?.onItemViewClick(todo)
        }

    }


    private fun getStatusStyle(holder: TodosAdapter.TodosViewHolder, todo: Todo) {
        if(todo.isDone==true){
            holder.binding.doneClick.setImageResource(R.drawable.is_done)
            holder.binding.icDone.setCardBackgroundColor(
                ContextCompat.getColor(holder.binding.icDone.context,
                    R.color.white)
            )
            holder.binding.verticalLine.background=ContextCompat.getDrawable(
                holder.binding.verticalLine.context,
                R.drawable.vertical_line_background_isdone
            )
            holder.binding.todoTitleTv.setTextAppearance(R.style.TitleTextStyleIsDone)
        }
        else{
            holder.binding.doneClick.setImageResource(R.drawable.ic_done)
            holder.binding.icDone.setCardBackgroundColor(
                ContextCompat.getColor(holder.binding.icDone.context,
                    R.color.blue)
            )
            holder.binding.verticalLine.background=ContextCompat.getDrawable(
                holder.binding.verticalLine.context,
                R.drawable.vertical_line_background
            )
            holder.binding.todoTitleTv.setTextAppearance(R.style.TitleTextStyle)
        }
    }


    override fun getItemCount(): Int = todosList.size
    public fun refreshList(newList: List<Todo>) {
        todosList = newList
        notifyDataSetChanged()
    }

    var listener: OnItemClickListener? = null

    interface OnItemClickListener {
        fun onDelete(todo: Todo)
        fun onDoneClick(todo: Todo)
        fun onItemViewClick(todo: Todo)
    }

    class TodosViewHolder(val binding: ItemTodoBinding) : ViewHolder(binding.root)



}