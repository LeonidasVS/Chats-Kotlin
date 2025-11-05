package com.example.chats_kotlin

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chats_kotlin.Fragmentos.FragmentPerfil
import com.example.chats_kotlin.databinding.ActivityEditarInformacionBinding
import com.example.chats_kotlin.databinding.ActivityLoginEmailBinding

class EditarInformacionActivity : AppCompatActivity() {

    private lateinit var binding:ActivityEditarInformacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityEditarInformacionBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.IvEditarImg.setOnClickListener {
            startActivity(Intent(this, FragmentPerfil::class.java))
        }
    }
}