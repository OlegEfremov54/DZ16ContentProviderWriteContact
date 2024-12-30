package com.example.dz16contentproviderwritecontact

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.text.SimpleDateFormat
import java.util.Date
import java.util.TimeZone

class MainActivity : AppCompatActivity(),ContactAdapter.ContactClickListener  {
    private lateinit var contactViewModel: ContactViewModel
    private lateinit var toolbarMain: Toolbar
    private lateinit var nameET: EditText
    private lateinit var surnameET: EditText
    private lateinit var phoneET: EditText
    private lateinit var saveBTN: Button
    private lateinit var recyclerViewRV: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //Инициализация Тулбар
        toolbarMain = findViewById(R.id.toolbarMain)
        setSupportActionBar(toolbarMain)
        title = " Контакты ROOM -2"
        toolbarMain.subtitle = " Вер.2.Главная страница"
        toolbarMain.setLogo(R.drawable.db)

        nameET = findViewById(R.id.nameET)
        surnameET = findViewById(R.id.surnameET)
        phoneET = findViewById(R.id.phoneET)
        saveBTN = findViewById(R.id.saveBTN)
        recyclerViewRV = findViewById(R.id.recyclerViewRV)

        recyclerViewRV.layoutManager = LinearLayoutManager(this)
        val adapter = ContactAdapter(this, this)
        recyclerViewRV.adapter = adapter


        contactViewModel = ViewModelProvider(
            this,
            ViewModelProvider
                .AndroidViewModelFactory
                .getInstance(application))[ContactViewModel::class.java]

        contactViewModel.allContacts.observe(this, Observer { contacts ->
            contacts?.let {
                adapter.update(it)
            }
        })

        saveBTN.setOnClickListener {
            val name = nameET.text.toString()
            val surname = surnameET.text.toString()
            val phone = phoneET.text.toString()
            val timeStamp = formatMilliseconds(Date().time)
            if (name.isNotEmpty() && surname.isNotEmpty() && phone.isNotEmpty()) {
                contactViewModel.insert(Contact( name, surname, phone, timeStamp))
                Toast.makeText(this, "Контакт $name сохранён", Toast.LENGTH_SHORT).show()
            }
            nameET.text.clear()
            surnameET.text.clear()
            phoneET.text.clear()
        }
    }

    private fun formatMilliseconds(time: Long): String {
        val timeFormat = SimpleDateFormat("HH:mm, EEEE, dd MMMM yyyy ")
        timeFormat.timeZone = TimeZone.getTimeZone("GMT+03")
        return timeFormat.format(Date(time))
    }



    override fun onItemClicked(contact: Contact) {
        contactViewModel.delete(contact)
        Toast.makeText(this, "Контакт ${contact.name} ${contact.surname} удалён", Toast.LENGTH_SHORT).show()
    }


// Активация Меню
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.infoMenuMain -> {


                Toast.makeText(
                    applicationContext, "Автор Ефремов О.В. Создан 28.12.2024",
                    Toast.LENGTH_LONG
                ).show()
            }

            R.id.exitMenuMain -> {
                Toast.makeText(
                    applicationContext, "Работа приложения завершена",
                    Toast.LENGTH_LONG
                ).show()
                finishAffinity()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}