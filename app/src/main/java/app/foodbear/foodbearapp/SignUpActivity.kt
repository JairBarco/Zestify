package app.foodbear.foodbearapp


import android.app.ProgressDialog
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


class SignUpActivity : AppCompatActivity() {


    private lateinit var nombreCompleto: EditText
    private lateinit var email: EditText
    private lateinit var contrasena: EditText
    private lateinit var contraDos: EditText

    lateinit var progressDialog:ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        val signUpBtn = findViewById<Button>(R.id.signUpBtn)
        nombreCompleto = findViewById(R.id.nombreCampo)
        email = findViewById(R.id.emailCampo)
        contrasena = findViewById(R.id.contraCampo)
        contraDos = findViewById(R.id.contraCampoDos)
        val signInBt = findViewById<TextView>(R.id.signInBtn)



        progressDialog = ProgressDialog(this)


        signInBt.setOnClickListener {
            intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }


        signUpBtn.setOnClickListener {
            revisarCampos()
        }
    }


    private fun revisarCampos() {
        if (nombreCompleto.text.isEmpty()){
            toast("Ingrese nombre completo")
            return
        }
        if (email.text.isEmpty()){
            toast("Por favor ingrese un correo")
            return
        }

        if(contrasena.text.isEmpty()){
            toast("Por favor ingrese una contraseña")
            return
        }
        if (contrasena.text.toString() != contraDos.text.toString()){
            toast("Contraseña incorrecta")
            return
        }

        val queque = Volley.newRequestQueue(this)
        val url = "http://foodbearapp.atwebpages.com/signup.php"
        var resultadoPost = object : StringRequest(Request.Method.POST,url, Response.Listener { response ->
            toast("Datos guardados")
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()

        }, Response.ErrorListener { error ->
            toast("Error $error")
        }){
            override fun getParams() : MutableMap<String, String> {
                val parametros = HashMap<String,String>()
                parametros.put("nombre",nombreCompleto.text.toString())
                parametros.put("email",email.text.toString())
                parametros.put("contrasena",contrasena.text.toString())
                return parametros
            }
        }
        queque.add(resultadoPost )


    }

}


