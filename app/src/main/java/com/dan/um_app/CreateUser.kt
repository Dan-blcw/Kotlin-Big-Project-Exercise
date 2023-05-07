package com.dan.um_app

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.dan.um_app.adapter.Util
import com.dan.um_app.adapter.classAdapter
import com.dan.um_app.databinding.ActivityCreateUserBinding
import com.dan.um_app.databinding.CustomAddclassBinding
import com.dan.um_app.model.Subject
import com.dan.um_app.model.Tien_day1H
import com.dan.um_app.model.entitis.NClass
import com.dan.um_app.model.entitis.Teacher
import com.dan.um_app.model.entitis.const

@SuppressLint("StaticFieldLeak")
private lateinit var binding: ActivityCreateUserBinding
@Suppress("DEPRECATION")
class CreateUser : AppCompatActivity() {
    lateinit var diolog: AlertDialog
    private lateinit var binding: ActivityCreateUserBinding
    private lateinit var teacher: Teacher
    private lateinit var oldTeacher: Teacher
    var tt = 0.0;
    var hsgv = 0.0
    lateinit var Monhoc: Subject
    var hsoLophoc= 0.0
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldTeacher = intent.getSerializableExtra("current_teacher") as Teacher
            binding.edtTitle.setText(oldTeacher.name)
            binding.edtNote.setText(oldTeacher.idT)
            binding.edtbc.setText(oldTeacher.bc)
            hsgv = ex(oldTeacher.bc.toString())
            binding.txthsgv.setText(hsgv.toString())
            Glide.with(applicationContext).load(const.listImg[oldTeacher.id!!-1]).into(binding.imgView)
            isUpdate = true
        }catch (exception: Exception){
            exception.printStackTrace()
        }
        binding.btnDone.setOnClickListener {
            val title_str = binding.edtTitle.text.toString()
            val note_str = binding.edtNote.text.toString()
            val note_bc = binding.edtbc.text.toString()
            if(title_str.isNotEmpty() || note_str.isNotEmpty()){
                if(isUpdate){
                    teacher = Teacher(oldTeacher.id,title_str,note_str,note_bc)
                }else{
                    teacher = Teacher(null,title_str,note_str,note_bc)
                }
                val intent = Intent()
                intent.putExtra("teacher",teacher)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this@CreateUser, "Please Typing some data !!!", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }
        }
        binding.btnExit.setOnClickListener {
            onBackPressed()
        }
//        binding.btnFavorite.setOnClickListener {
//            Toast.makeText(this@CreateUser,"Added to success list", Toast.LENGTH_LONG).show()
//        }
//        binding.txtRandomNote.setText(const.createNote())

        buildADD()

        val list = const.listNClass.filter { it.id_gv == oldTeacher.id}
        val rvAdapter = classAdapter(list,object: Util{
            override fun OnClickTitle(pos: Int) {
                Toast.makeText(
                    this@CreateUser,
                    "Click ${list[pos].tlh}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        },)
        binding.rvClass.adapter = rvAdapter
        binding.rvClass.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        binding.btnTt.setOnClickListener {
            var tt1lop: Double = 0.0
            var hs: Double = 0.0
            for(d in list){
                tt1lop = d.stiet*(d.hsmh + d.hslh + hsgv)*Tien_day1H
                tt = tt + tt1lop
            }
            binding.txttt.setText("Tong tien :${tt}")
        }

    }
//
//    private fun buildInti() {
//
//    }

    private fun buildADD() {
        binding.btnAddclass.setOnClickListener {
            val build = AlertDialog.Builder(this,R.style.ThemeCustom)
            // khác so với extension là binding tự động gọi nên inflate from this là được
            val diologBinding = CustomAddclassBinding.inflate(LayoutInflater.from(this))
            build.setView(diologBinding.root)
            diologBinding.btnExitimg.setOnClickListener { diolog.dismiss() }
            diologBinding.btnAdd.setOnClickListener{
//                Toast.makeText(this@CreateUser,"You just click on ADD Now",Toast.LENGTH_SHORT).show()
                if(diologBinding.rd1.isChecked){
                    Monhoc = const.Toan
                }else if(diologBinding.rd2.isChecked){
                    Monhoc = const.Van
                }else if(diologBinding.rd3.isChecked){
                    Monhoc = const.Anh
                }else if(diologBinding.rd4.isChecked){
                    Monhoc = const.Ly
                }else if(diologBinding.rd5.isChecked){
                    Monhoc = const.Hoa
                }
                var tenlh = diologBinding.edtTenLopHoc.text.toString()
                var sl = diologBinding.edtSoluong.text.toString().toInt()
                if(sl <20){
                    hsoLophoc = -0.5
                }else if(sl >= 40){
                    hsoLophoc = 0.2
                }else {
                    hsoLophoc = 0.0
                }
                val nClass = NClass(oldTeacher.id!!,tenlh,Monhoc.name,Monhoc.heso,Monhoc.stiet,sl,hsoLophoc,hsgv)
                buildCl(nClass)
                diolog.dismiss()

            }
            diolog = build.create()
            diolog.show()
        }
    }

    private fun buildCl(nClass: NClass) {
        const.listNClass.add(nClass)
        val list = const.listNClass
        val rvAdapter = classAdapter(list,object: Util{
            override fun OnClickTitle(pos: Int) {
                Toast.makeText(
                    this@CreateUser,
                    "Click ${list[pos].tlh}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        },)
        binding.rvClass.adapter = rvAdapter
        binding.rvClass.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
    }

    fun ex(dio: String): Double{
        val find = dio.lowercase()
        when(find){
            "dai hoc"-> return 1.3
            "tien si"-> return 1.4
            "thac si"-> return 1.5
            "pho giao su"-> return 1.6
            "giao su"-> return 1.7
            else -> return  0.0
        }
    }
}