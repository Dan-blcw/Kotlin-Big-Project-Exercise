package com.dan.um_app.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dan.um_app.R
import com.dan.um_app.model.entitis.Teacher
import com.dan.um_app.model.entitis.const

class TotalAdapter(private val context: Context, val ltn: NotesOnClickListener):
    RecyclerView.Adapter<TotalAdapter.NoteViewHolder>() {

    private val teacherList = ArrayList<Teacher>()
    private val fullList = ArrayList<Teacher>()

    inner class NoteViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.item_user,parent,false)
        )
    }

    override fun getItemCount(): Int {
        return teacherList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.itemView.apply {
            val colorLayout = findViewById<CardView>(R.id.cart_layout)
            val image = findViewById<ImageView>(R.id.imgNote)
            val name = findViewById<TextView>(R.id.txtName)
            val bc = findViewById<TextView>(R.id.txtBc)
            val idT = findViewById<TextView>(R.id.txtId)
            val btn_delete = findViewById<ImageButton>(R.id.btn_delete)

//            val index= random()
            const.create()
//            const.suf()
            name.text = "Full name: ${teacherList[position].name}"
            name.isSelected = true
            bc.text = "literacy: ${teacherList[position].bc}"
            idT.text = "ID: ${teacherList[position].idT}"
            idT.isSelected = true
            colorLayout.setOnClickListener {
                ltn.onItemClick(teacherList[holder.adapterPosition])
            }
            btn_delete.setOnClickListener {
                val dialog = AlertDialog.Builder(context)
                dialog.apply {
                    setTitle("Confirm Delete Teacher")
                    setMessage("Have you confirmed to remove the item from the cart?")
                    setNegativeButton("No"){ dialogInterface: DialogInterface, i: Int ->
                        dialogInterface.dismiss()
                    }
                    setPositiveButton("Yes"){ dialogInterface: DialogInterface, i: Int ->
                        ltn.onDeleteItemClick(teacherList[holder.adapterPosition])
                    }
                }.show()
            }
            Glide.with(context).load(const.listImg[teacherList[position].id!!-1]).into(image)
        }
    }
    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Teacher>){
        fullList.clear()
        fullList.addAll(newList)

        teacherList.clear()
        teacherList.addAll(fullList)
        notifyDataSetChanged()
    }
    fun searchFilter(content: String){
        teacherList.clear()
        for(nowIT in fullList){
            if(nowIT.name?.lowercase()?.contains(content.lowercase()) == true ||
                nowIT.idT?.lowercase()?.contains(content.lowercase()) == true ||
                nowIT.bc?.lowercase()?.contains(content.lowercase()) == true)
                teacherList.add(nowIT)
        }
        notifyDataSetChanged()
    }
    interface NotesOnClickListener{
        fun onItemClick(teacher: Teacher)
        fun onDeleteItemClick(teacher: Teacher)
    }
}