package com.dan.um_app

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dan.um_app.adapter.TotalAdapter
import com.dan.um_app.databinding.ActivityMainBinding
import com.dan.um_app.dbase.AllDB
import com.dan.um_app.model.NotesViewModel
import com.dan.um_app.model.entitis.Teacher

private lateinit var binding: ActivityMainBinding
@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() ,TotalAdapter.NotesOnClickListener{
    private lateinit var binding: ActivityMainBinding
    private lateinit var dbase: AllDB
    lateinit var viewModel: NotesViewModel
    lateinit var adapter: TotalAdapter
    lateinit var takeTeacher: Teacher
    private  val updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if(result.resultCode == Activity.RESULT_OK){
            val teacher = result.data?.getSerializableExtra("teacher") as? Teacher
            if(teacher != null){
                viewModel.updateNote(teacher)
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        Roll0()
        Roll1()
        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NotesViewModel::class.java)
        viewModel.allTeacher.observe(this){list->
            list?.let{
                adapter.updateList(list)
            }
        }
        dbase = AllDB.getDB(this)

    }
    private fun Roll1() {
        binding.rview.setHasFixedSize(true)
        binding.rview.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.VERTICAL,
            false
        )
        adapter = TotalAdapter(this,this)
        binding.rview.adapter = adapter
        val getContext = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            val teacher = result.data?.getSerializableExtra("teacher") as? Teacher
            if(teacher != null){
                viewModel.insert(teacher)
            }
        }
//        binding.btnListImg.setOnClickListener {
//            val inte = Intent(this@MainActivity,ListImage::class.java)
//            startActivity(inte)
//        }
        binding.btnCreateNote.setOnClickListener{
            val intent = Intent(this, CreateUser::class.java)
            getContext.launch(intent)
        }
        //search
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText !=null){
                    adapter.searchFilter(newText)
                }
                return true
            }
        })
    }

    override fun onItemClick(teacher: Teacher) {
        val intent = Intent(this@MainActivity, CreateUser::class.java)
        intent.putExtra("current_teacher",teacher)
        updateNote.launch(intent)
    }

    override fun onDeleteItemClick(teacher: Teacher) {
        viewModel.deleteNote(teacher)
    }

}