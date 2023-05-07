package com.dan.um_app.model.entitis

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class NClass (
    var id_gv: Int,
    var tlh: String,
    var tmh: String,
    var hsmh: Double,
    var stiet: Int,
    var ssv: Int,
    var hslh: Double,
    var hsgv: Double,
)
//ten_lop_hoc: tlh
//he so mon hoc: hsmn
//so_sinh_vien: ssv
//he_so_lop_hoc: hslh