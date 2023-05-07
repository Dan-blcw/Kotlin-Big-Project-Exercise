package com.dan.um_app.dbase

import androidx.lifecycle.LiveData
import com.dan.um_app.model.entitis.Teacher

class ForRepository(private val allDao: AllDao) {
    val allTeacher: LiveData<List<Teacher>> = allDao.getAllTeacher()

    suspend fun insert(teacher: Teacher){
        allDao.insert(teacher)
    }
    suspend fun delete(teacher: Teacher){
        allDao.delete(teacher)
    }
    suspend fun update(teacher: Teacher){
        allDao.update(teacher.id,teacher.name,teacher.idT,teacher.bc)
    }
}