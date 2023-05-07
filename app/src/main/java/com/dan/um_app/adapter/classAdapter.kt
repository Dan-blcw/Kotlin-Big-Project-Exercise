package com.dan.um_app.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.dan.um_app.R
import com.dan.um_app.model.entitis.NClass
import com.dan.um_app.model.entitis.const

class classAdapter (var list: List<NClass>,val util: Util): RecyclerView.Adapter<classAdapter.TilleView>(){
    inner class TilleView(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TilleView {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.detail_class, parent,false)
        return TilleView(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: TilleView, position: Int) {
        holder.itemView.apply {
            val tenlop = findViewById<TextView>(R.id.txtTenlop)
            val monHoc = findViewById<TextView>(R.id.txtMonhoc)
            val soluong = findViewById<TextView>(R.id.txtsoluong)
            val hsmh = findViewById<TextView>(R.id.txthsmh)
            val hslh = findViewById<TextView>(R.id.txthslh)
            val sotiet = findViewById<TextView>(R.id.txtsotiet)

            val tongtien = findViewById<TextView>(R.id.txtt1lop)
            val btn = findViewById<ImageButton>(R.id.btn_delete_Class)

            tenlop.text = "Name Class:   ${list[position].tlh}"
            monHoc.text = "Subject :${list[position].tmh}"
            soluong.text = "Number of Student: ${list[position].ssv.toString()}"
            hsmh.text = "HSMH: ${list[position].hsmh.toString()}"
            hslh.text = "HSL: ${list[position].hslh.toString()}"
            sotiet.text = "Number of lessons: ${list[position].stiet}"

            tongtien.text = "1 class tuition fee: ${tinhtien1lop(list[position])} VND"
            btn.setOnClickListener {
                Toast.makeText(context,"Close", Toast.LENGTH_SHORT).show()
                val dialog = AlertDialog.Builder(context)
                dialog.apply {
                    setTitle("Confirm Window")
                    setMessage("Have you confirmed to remove the class from the list?")
                    setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }
                    setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                        const.listNClass.removeAt(position)
                    }
                }.show()
            }
            holder.itemView.setOnClickListener {
                util.OnClickTitle(position)
            }
        }
    }
    fun tinhtien1lop(nClass: NClass): Double{
        return const.Tien_day1H*(nClass.hslh + nClass.hsgv+ nClass.hsmh)*nClass.stiet
    }

}