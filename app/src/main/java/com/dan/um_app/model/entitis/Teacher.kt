package com.dan.um_app.model.entitis

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "Teacher_tb")
data class Teacher(
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "name")
    val name: String?,
    @ColumnInfo(name = "idt")
    val idT: String?,
    @ColumnInfo(name = "bc")
    val bc: String?
): Serializable
