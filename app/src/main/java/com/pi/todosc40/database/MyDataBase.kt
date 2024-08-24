package com.pi.todosc40.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.pi.todosc40.database.dao.TodoDao
import com.pi.todosc40.database.entity.Todo

@Database(entities = [Todo::class], version = 1)
abstract class MyDataBase: RoomDatabase() {
    companion object{
        private var myDataBase: MyDataBase? = null

        fun getInstance(context: Context): MyDataBase{
            if(myDataBase == null){
                myDataBase = Room.databaseBuilder(
                    context,
                    MyDataBase::class.java, "todos database"
                ).allowMainThreadQueries().fallbackToDestructiveMigration().build()
            }
            return myDataBase!!;
        }
    }
    abstract fun getTodosDao(): TodoDao
}
