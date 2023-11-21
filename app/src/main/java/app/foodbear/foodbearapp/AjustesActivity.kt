package app.foodbear.foodbearapp

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import app.foodbear.foodbearapp.Utils.Extensions.toast

class AjustesActivity : AppCompatActivity() {

    lateinit var nombre: EditText
    lateinit var correo: EditText
    lateinit var contrasena: EditText
    lateinit var botonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        nombre = findViewById(R.id.nombreCampo2)
        correo = findViewById(R.id.correoCampo)
        contrasena = findViewById(R.id.Contrasena)  // Nuevo campo de contraseña
        botonGuardar = findViewById(R.id.guardarBtn)
        val volverAtras: ImageView = findViewById(R.id.icRegresar)

        volverAtras.setOnClickListener {
            onBackPressed()
        }

        botonGuardar.setOnClickListener {
            checarDatosIngresados()
        }

        verificarDatos()

        // Mostrar datos actuales
        mostrarDatosActuales()
    }

    private fun checarDatosIngresados() {
        if (nombre.text.isEmpty() || correo.text.isEmpty() || contrasena.text.isEmpty()) {
            toast("Completa todos los campos")
            return
        }

        // Obtener el usuario actual desde SignUpActivity
        val usuarioActual = SignUpActivity.usuarioActual

        if (usuarioActual != null) {
            // Actualizar datos directamente en el objeto Usuario
            usuarioActual.nombreCompleto = nombre.text.toString()
            usuarioActual.email = correo.text.toString()
            usuarioActual.contrasena = contrasena.text.toString()  // Actualizar contraseña

            toast("Datos actualizados")
            botonGuardar.visibility = View.GONE
        } else {
            toast("Usuario no encontrado")
        }
    }

    private fun mostrarDatosActuales() {
        // Obtener el usuario actual desde SignUpActivity
        val usuarioActual = SignUpActivity.usuarioActual

        if (usuarioActual != null) {
            // Mostrar datos actuales en los campos correspondientes
            nombre.setText(usuarioActual.nombreCompleto)
            correo.setText(usuarioActual.email)
            contrasena.setText(usuarioActual.contrasena)  // Mostrar contraseña
        }
    }

    private fun verificarDatos() {
        nombre.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                botonGuardar.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count > 1) {
                    botonGuardar.visibility = View.VISIBLE
                }
            }
        })

        correo.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {
                botonGuardar.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (count > 1) {
                    botonGuardar.visibility = View.VISIBLE
                }
            }
        })
    }
}
