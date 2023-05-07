package com.dan.um_app.model

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.dan.um_app.dbase.AllDB
import com.dan.um_app.dbase.ForRepository
import com.dan.um_app.model.entitis.Teacher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotesViewModel(application: Application): AndroidViewModel(application) {
    private val repo: ForRepository
    val allTeacher: LiveData<List<Teacher>>
    init {
        val dao = AllDB.getDB(application).getNoteDao()
        repo = ForRepository(dao)
        allTeacher = repo.allTeacher
    }
    fun deleteNote(teacher: Teacher) = viewModelScope.launch(Dispatchers.IO){
        repo.delete(teacher)
    }
    fun insert(teacher: Teacher) = viewModelScope.launch(Dispatchers.IO){
        repo.insert(teacher)
    }
    fun updateNote(teacher: Teacher) = viewModelScope.launch(Dispatchers.IO){
        repo.update(teacher)
    }
}