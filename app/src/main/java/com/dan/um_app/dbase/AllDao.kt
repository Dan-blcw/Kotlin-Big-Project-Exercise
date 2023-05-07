package com.dan.um_app.dbase

import androidx.lifecycle.LiveData
import androidx.room.*
import com.dan.um_app.model.entitis.NClass
import com.dan.um_app.model.entitis.Teacher

@Dao
interface AllDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(teacher: Teacher)
    @Delete
    suspend fun delete(teacher: Teacher)
    @Query("SELECT * FROM Teacher_tb ORDER BY id ASC")
    fun getAllTeacher(): LiveData<List<Teacher>>
    @Query("UPDATE Teacher_tb SET name = :name, bc = :bc , idt = :idt WHERE id = :id")
    suspend fun update(id: Int?, name: String?,idt: String?,bc: String?)
}