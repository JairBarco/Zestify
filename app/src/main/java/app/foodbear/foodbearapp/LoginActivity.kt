package app.foodbear.foodbearapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import app.foodbear.foodbearapp.Utils.Extensions.toast

class LoginActivity : AppCompatActivity() {

    lateinit var signInEmail: String
    lateinit var signInPassword: String
    lateinit var signInBtn: Button
    lateinit var emailTxt: EditText
    lateinit var contrasenaTxt: EditText

    lateinit var loadingDialog: loadingDialog

    lateinit var emailError: TextView
    lateinit var contrasenaError: TextView
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

        loadingDialog = loadingDialog(this)

        signUpBtn.setOnClickListener {
            intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        signInBtn.setOnClickListener {
            emailEts = emailTxt.text.toString().trim()
            contrasenaEts = contrasenaTxt.text.toString().trim()

            // Validación del usuario master
            if (emailEts == "jair@main.com" && contrasenaEts == "Test1234") {
                // Crear un usuario master
                val usuarioMaster = Usuario("Jair Main", emailEts, contrasenaEts)

                // Establecer el usuario master como usuario actual en SignUpActivity
                SignUpActivity.usuarioActual = usuarioMaster

                // Agregar el usuario master a la lista de usuarios en SignUpActivity
                SignUpActivity.listaUsuarios.add(usuarioMaster)

                // Inicio de sesión como usuario master
                var intent = Intent(this, InicioActivity::class.java)
                intent.putExtra("email", emailEts)
                startActivity(intent)
                finish()
                toast("Inicio de sesión completado")
            } else {
                // Verifica si el email y la contraseña coinciden en la lista de usuarios
                val usuarioEncontrado =
                    SignUpActivity.listaUsuarios.find { it.email == emailEts && it.contrasena == contrasenaEts }

                if (usuarioEncontrado != null) {
                    // Establece el usuario actual en SignUpActivity
                    SignUpActivity.usuarioActual = usuarioEncontrado

                    // Inicio de sesión permitido
                    var intent = Intent(this, InicioActivity::class.java)
                    intent.putExtra("email", emailEts)
                    startActivity(intent)
                    finish()
                    toast("Inicio de sesión completado")
                } else {
                    // Email o contraseña incorrectos
                    toast("Email o contraseña incorrectos")
                }
            }
        }
    }
}
