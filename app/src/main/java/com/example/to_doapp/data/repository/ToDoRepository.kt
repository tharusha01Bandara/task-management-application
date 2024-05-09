package com.example.to_doapp.data.repository

import androidx.lifecycle.LiveData
import com.example.to_doapp.data.ToDoDao
import com.example.to_doapp.data.model.ToDoData

class ToDoRepository(private  val toDoDao: ToDoDao) {
    val getAllData: LiveData<List<ToDoData>> = toDoDao.getAllData()
    val sortByHighPriority: LiveData<List<ToDoData>> = toDoDao.sortByHighPriority()
    val sortByLowPriority: LiveData<List<ToDoData>> = toDoDao.sortByLowPriority()


    suspend fun insertData(toDoData: ToDoData) {
        toDoDao.insertData(toDoData)
    }
        suspend fun updateData(toDoData: ToDoData){
            toDoDao.updateData(toDoData)
    }

    suspend fun deleteData(toDoData: ToDoData){
        toDoDao.deleteData(toDoData)
    }

    suspend fun deleteAll(){
        toDoDao.deleteAll()
    }

    fun searchDataBase(searchQuery:String): LiveData<List<ToDoData>>{
        return toDoDao.searchDataBase(searchQuery)
    }
}