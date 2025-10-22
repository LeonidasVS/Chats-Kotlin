package com.example.chats_kotlin.Fragmentos

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.chats_kotlin.R
import com.example.chats_kotlin.databinding.FragmentPerfilBinding
import com.google.firebase.auth.FirebaseAuth
import android.content.Context
import android.content.Intent
import com.google.firebase.database.FirebaseDatabase

import android.widget.Toast
import com.example.chats_kotlin.OpcionesLoginActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FragmentPerfil : Fragment() {

    private lateinit var binding: FragmentPerfilBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var mContext: Context

    override fun onAttach(context: Context){
        mContext= context
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //Llamando al usuario logeado
        firebaseAuth=FirebaseAuth.getInstance()

        //Inflar el layout para mostrar el layout
        binding=FragmentPerfilBinding.inflate(layoutInflater,container, false)

        cargarInformacion()

        //Funcion cerrar sesion del usuario
        binding.btnCerrarSesion.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(context, OpcionesLoginActivity::class.java))
            activity?.finishAffinity()
        }

        return binding.root
    }

    private fun cargarInformacion() {
        var ref=FirebaseDatabase.getInstance().getReference("usuarios")
        ref.child("${firebaseAuth}")
            .addValueEventListener(object :  ValueEventListener{
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nombres="${snapshot.child("nombres").value}"
                    val email="${snapshot.child("email").value}"
                    val estado="${snapshot.child("estado").value}"
                    val proveedor="${snapshot.child("proveedor").value}"
                    var tiempoR="${snapshot.child("tiempoR").value}"
                    val imagen="${snapshot.child("imagen").value}"

                    //Condicion del tiempo
                    if(tiempoR==null){
                        tiempoR="0";
                    }
                }

                override fun onCancelled(error: DatabaseError) {

                }

            })
    }
}