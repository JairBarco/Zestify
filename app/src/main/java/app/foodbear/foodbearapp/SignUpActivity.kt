package app.foodbear.foodbearapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import app.foodbear.foodbearapp.Utils.Extensions.toast

class Usuario(var nombreCompleto: String, var email: String, var contrasena: String,var pedidos: List<String> = listOf())

class SignUpActivity : AppCompatActivity() {

    private lateinit var nombreCompleto: EditText
    private lateinit var email: EditText
    private lateinit var contrasena: EditText
    private lateinit var contraDos: EditText

    // Lista para almacenar usuarios
    companion object {
        var listaUsuarios = mutableListOf<Usuario>()
        var usuarioActual: Usuario? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signUpBtn = findViewById<Button>(R.id.signUpBtn)
        nombreCompleto = findViewById(R.id.nombreCampo)
        email = findViewById(R.id.emailCampo)
        contrasena = findViewById(R.id.contraCampo)
        contraDos = findViewById(R.id.contraCampoDos)
        val signInBt = findViewById<TextView>(R.id.signInBtn)

        signInBt.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

        signUpBtn.setOnClickListener {
            revisarCampos()
        }
    }

    private fun revisarCampos() {
        if (nombreCompleto.text.isEmpty() || email.text.isEmpty() || contrasena.text.isEmpty() || contrasena.text.toString() != contraDos.text.toString()) {
            toast("Por favor complete todos los campos correctamente")
            return
        }

        // Almacenar datos en la lista de usuarios
        val nuevoUsuario = Usuario(nombreCompleto.text.toString(), email.text.toString(), contrasena.text.toString())
        listaUsuarios.add(nuevoUsuario)
        usuarioActual = nuevoUsuario

        toast("Registro exitoso")

        // Finalmente, navegar a la actividad de inicio de sesión
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        finish()
    }
}
