package com.pi.todosc40.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.pi.todosc40.database.entity.Todo

@Dao
abstract class TodoDao {

    @Insert
    abstract fun addTodo(todo: Todo)
    @Delete
    abstract fun deleteTodo(todo: Todo)

    @Update
    abstract fun updateTodo(todo: Todo)

    @Query("select * from Todo")
    abstract fun getTodos(): List<Todo>

    @Query("select * from Todo where date = :date  ")
    abstract fun getTodosByDate(date: Long): List<Todo>
}