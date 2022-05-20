package app.foodbear.foodbearapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import app.foodbear.foodbearapp.Utils.Extensions.toast

class LoginActivity : AppCompatActivity() {

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInBtn: Button
    lateinit var emailTxt: EditText
    lateinit var contrasenaTxt: EditText

    lateinit var loadingDialog: loadingDialog

    lateinit var emailError:TextView
    lateinit var contrasenaError:TextView
    lateinit var contrasenaEts: String
    lateinit var emailEts: String




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val signUpBtn = findViewById<TextView>(R.id.signUpBtnLgn)
        signInBtn = findViewById(R.id.loginBtn)
        emailTxt = findViewById(R.id.emailTxt)
        contrasenaTxt = findViewById(R.id.contrasenaTxt)
        emailError = findViewById(R.id.emailError)
        contrasenaError = findViewById(R.id.contrasenaError)
        emailEts = emailTxt.text.toString().trim()



        //textAutoCheck()

        loadingDialog = loadingDialog(this)

        signUpBtn.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {

            emailEts = emailTxt.text.toString().trim()
            contrasenaEts = contrasenaTxt.text.toString().trim()

            val queque = Volley.newRequestQueue(this)
            val url = "http://foodbearapp.atwebpages.com/login.php?email=$emailEts"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null, Response.Listener{ response ->
                    val contra = response.getString("contrasena")
                    if (contrasenaEts == contra){
                        var intent = Intent(this, InicioActivity::class.java)
                        intent.putExtra("email", emailEts)
                        startActivity(intent)
                        finish()
                        toast("Inicio de sesión completado")

                    }else{
                        toast("Contraseña incorrecta")
                    }
                }, Response.ErrorListener { error ->
                    Toast.makeText(this, "Email incorrecto", Toast.LENGTH_LONG).show()
                })
            queque.add(jsonObjectRequest)

        }

    }
}





