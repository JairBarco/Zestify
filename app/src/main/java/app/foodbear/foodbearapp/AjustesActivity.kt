package app.foodbear.foodbearapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import app.foodbear.foodbearapp.Utils.Extensions.toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AjustesActivity : AppCompatActivity() {

    lateinit var nombre: EditText
    lateinit var correo: EditText
    lateinit var botonGuardar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ajustes)

        nombre = findViewById(R.id.nombreCampo2)
        correo = findViewById(R.id.correoCampo)
        botonGuardar = findViewById(R.id.guardarBtn)
        val volverAtras: ImageView = findViewById(R.id.icRegresar)


        volverAtras.setOnClickListener {
            onBackPressed()
        }

        botonGuardar.setOnClickListener {
            checarDatosIngresados()
        }

        verificarDatos()
    }

    private fun checarDatosIngresados() {

        if (nombre.text.isEmpty()) {
            toast("Campo de nombre vacio")
            return
        }
        if (correo.text.isEmpty()) {
            toast("Campo de Email vacio")
            return
        }

        val emailU = intent.getStringExtra("emailU").toString()
        var nombre = nombre.text.toString()
        var email = correo.text.toString()

        actualizarDatosEnBD(nombre, email, emailU)
    }

    private fun actualizarDatosEnBD(nombre: String, email: String, emailU: String) =
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val queque = Volley.newRequestQueue(this@AjustesActivity)
                val url = "http://foodbearapp.atwebpages.com/editar.php?emailU=$emailU&nombre=$nombre&email=$email"
                val jsonObjectRequest = JsonObjectRequest(
                    Request.Method.GET, url, null, Response.Listener { response ->
                        toast("Datos actualizados")
                    }, Response.ErrorListener { error ->
                        Toast.makeText(
                            this@AjustesActivity,
                            "Se reinicio la aplicación para aplicar la actualización",
                            Toast.LENGTH_LONG
                        ).show()
                        var intent = Intent(this@AjustesActivity, LoginActivity::class.java)
                        startActivity(intent)
                        finish()
                    })

                queque.add(jsonObjectRequest)

                withContext(Dispatchers.Main) {
                    Toast.makeText(this@AjustesActivity, "Guardado", Toast.LENGTH_SHORT).show()
                    botonGuardar.visibility = View.GONE
                }

            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(
                        this@AjustesActivity,
                        "" + e.message.toString(),
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            }
        }
    private fun verificarDatos() {

        nombre.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                botonGuardar.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (count > 1) {
                    botonGuardar.visibility = View.VISIBLE
                }
            }
        })

        correo.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                botonGuardar.visibility = View.VISIBLE
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {

            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                        before: Int, count: Int
            ) {
                if (count > 1) {
                    botonGuardar.visibility = View.VISIBLE
                }
            }
        })
    }
}
