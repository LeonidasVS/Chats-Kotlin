package com.example.chats_kotlin

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.chats_kotlin.databinding.ActivityRegistroMailBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class RegistroMailActivity : AppCompatActivity() {

    private lateinit var binding:ActivityRegistroMailBinding
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding=ActivityRegistroMailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        //Crear instancia de Firebase
        firebaseAuth=FirebaseAuth.getInstance()

        //Crear instancia de Progress
        progressDialog= ProgressDialog(this)
        progressDialog.setTitle("Espere por favor..")
        progressDialog.setCanceledOnTouchOutside(false)

        //Evento del usuario en registro
        binding.registrar.setOnClickListener {
            validarInformacion()
        }

    }

    private var nombres=""
    private var email=""
    private var password=""
    private var r_Password=""

    private fun validarInformacion(){
        nombres=binding.etNombres.text.toString().trim()
        email=binding.etEmailRegistro.text.toString().trim()
        password=binding.etPassword.text.toString().trim()
        r_Password=binding.etRPassword.text.toString().trim()

        if(nombres.isEmpty()){
            binding.etNombres.error="Ingrese Un Nombre"
            binding.etNombres.requestFocus()
        }else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            binding.etEmailRegistro.error="Correo Invalido"
            binding.etEmailRegistro.requestFocus()
        }else if(email.isEmpty()){
            binding.etEmailRegistro.error="Ingrese Un Correo"
            binding.etEmailRegistro.requestFocus()
        }else if(password.isEmpty()){
            binding.etPassword.error="Ingrese Una Contraseña"
            binding.etPassword.requestFocus()
        }else if(r_Password.isEmpty()){
            binding.etRPassword.error="Repita La Contraseña"
            binding.etRPassword.requestFocus()
        }else if(password!=r_Password){
            binding.etRPassword.error="Las contraseñas no coinciden"
            binding.etRPassword.requestFocus()
        }
        else{
            registrarUsuario()
        }

    }

    private fun registrarUsuario(){
        progressDialog.setMessage("Creando Cuenta")
        progressDialog.show()

        firebaseAuth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener {
                actualizarInformacion()
            }
            .addOnFailureListener { e->
                progressDialog.dismiss()
                Toast.makeText(this, "Error al crear cuenta, debido a: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }

    private fun actualizarInformacion(){
        progressDialog.setMessage("Guardando Informacion")
        val uid=firebaseAuth.uid
        val nombresU=nombres
        val emailU=firebaseAuth.currentUser!!.email

        val tiempoR=Constants.obtenerTiempo()

        //Enviar datos informacion de FireBase
        val datosUsuarios=HashMap<String, Any>()
        datosUsuarios["uid"]="${uid}"
        datosUsuarios["nombres"]="${nombresU}"
        datosUsuarios["email"]="${emailU}"
        datosUsuarios["tiempoR"]="${tiempoR}"
        datosUsuarios["proveedor"]="Email"
        datosUsuarios["estado"]="Online"
        datosUsuarios["imagen"]=""

        //Guardar informacion en firebase

        val referencia=FirebaseDatabase.getInstance().getReference("usuarios")
        referencia.child(uid!!)
            .setValue(datosUsuarios)
            .addOnCompleteListener {
                progressDialog.dismiss()
                startActivity(Intent(applicationContext, MainActivity::class.java))
            }
            .addOnFailureListener {  e->
                progressDialog.dismiss()
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_SHORT).show()
            }
    }
}
