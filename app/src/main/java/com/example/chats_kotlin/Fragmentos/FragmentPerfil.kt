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
import com.bumptech.glide.Glide
import com.example.chats_kotlin.Constants
import com.example.chats_kotlin.EditarInformacionActivity
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

        binding.actualizarPerfil.setOnClickListener {
            startActivity(Intent(context, EditarInformacionActivity::class.java))
        }

        return binding.root
    }

    private fun cargarInformacion() {
        val ref = FirebaseDatabase.getInstance().getReference("usuarios")
        val currentUser = firebaseAuth.currentUser  // 🔹 Agregamos esta línea
        val uid = currentUser?.uid

        if (uid == null) {
            Toast.makeText(mContext, "Usuario no autenticado", Toast.LENGTH_LONG).show()
            return
        }

        ref.child(uid)
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val nombres = snapshot.child("nombres").value?.toString() ?: ""
                    val email = snapshot.child("email").value?.toString() ?: ""
                    val estado = snapshot.child("estado").value?.toString() ?: ""
                    val proveedor = snapshot.child("proveedor").value?.toString() ?: ""
                    val tiempoR = snapshot.child("tiempoR").value?.toString() ?: "0"
                    val imagen = snapshot.child("imagen").value?.toString() ?: ""

                    // Convertir tiempo de forma segura
                    val tiempoLong = tiempoR.toLongOrNull() ?: 0L
                    val fecha = Constants.formatoFecha(tiempoLong)

                    binding.tvNombres.text = nombres
                    binding.tvEmail.text = email
                    binding.tvProveedor.text = proveedor
                    binding.tvTRegistro.text = fecha

                    // 🔹 Si viene una imagen desde Realtime Database, la carga
                    if (imagen.isNotEmpty()) {
                        Glide.with(mContext)
                            .load(imagen)
                            .placeholder(R.drawable.ic_perfil)
                            .into(binding.ivPerfil)
                    } else {
                        // 🔹 Si el usuario se logueó con Google, usar su foto de Google
                        val photoUrl = currentUser.photoUrl
                        if (photoUrl != null) {
                            Glide.with(mContext)
                                .load(photoUrl)
                                .placeholder(R.drawable.ic_perfil)
                                .into(binding.ivPerfil)
                        } else {
                            binding.ivPerfil.setImageResource(R.drawable.ic_perfil)
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(mContext, error.message, Toast.LENGTH_LONG).show()
                }
            })
    }


}