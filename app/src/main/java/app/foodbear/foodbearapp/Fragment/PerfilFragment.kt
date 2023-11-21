package app.foodbear.foodbearapp.Fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProviders
import app.foodbear.foodbearapp.AjustesActivity
import app.foodbear.foodbearapp.LoginActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import app.foodbear.foodbearapp.R
import app.foodbear.foodbearapp.SignUpActivity
import app.foodbear.foodbearapp.db.Card.CardViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext




class PerfilFragment : Fragment() {

    lateinit var subirImagen:Button
    lateinit var nombreUsuario:TextView
    lateinit var perfilUsuario:TextView

    private lateinit var cardViewModel: CardViewModel



    var cards: Int = 0

    lateinit var linearLayout2:LinearLayout
    lateinit var linearLayout3:LinearLayout
    lateinit var linearLayout4:LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_perfil, container, false)

        val irAjustes = view.findViewById<CardView>(R.id.irAjustes)
        subirImagen = view.findViewById(R.id.subirImagen)
        nombreUsuario = view.findViewById(R.id.nombreUsuario)
        perfilUsuario = view.findViewById(R.id.perfilUsuario)
        linearLayout2 = view.findViewById(R.id.linearLayout2)
        linearLayout3 = view.findViewById(R.id.linearLayout3)
        linearLayout4 = view.findViewById(R.id.linearLayout4)
        val cerrarSesion = view.findViewById<CardView>(R.id.logout)


        cardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)

        cardViewModel.allCards.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            cards = it.size
        })


        cerrarSesion.setOnClickListener {
            startActivity(Intent(context, LoginActivity::class.java))
        }

        hideLayout()

        subirImagen.visibility = View.GONE


        getUserData()

        irAjustes.setOnClickListener {
            val bundle = arguments
            val email = bundle!!.getString("email")
            var intent = Intent(context, AjustesActivity::class.java)
            intent.putExtra("emailU", email)
            startActivity(intent)
        }

        return view
    }

    private fun hideLayout(){
        linearLayout2.visibility = View.GONE
        linearLayout3.visibility = View.GONE
        linearLayout4.visibility = View.GONE
    }
    private fun showLayout(){
        linearLayout2.visibility = View.VISIBLE
        linearLayout3.visibility = View.VISIBLE
        linearLayout4.visibility = View.VISIBLE
    }

    private fun getUserData() = CoroutineScope(Dispatchers.IO).launch {
        try {
            val usuarioActual = SignUpActivity.usuarioActual

            withContext(Dispatchers.Main){
                if (usuarioActual != null) {
                    perfilUsuario.text = usuarioActual.email
                    nombreUsuario.text = usuarioActual.nombreCompleto
                    showLayout()
                } else {
                    Toast.makeText(context, "Usuario no encontrado", Toast.LENGTH_LONG).show()
                }
            }
        } catch (e: Exception) {
            withContext(Dispatchers.Main){
                Toast.makeText(context, "Carga de datos fallida", Toast.LENGTH_LONG).show()
            }
        }
    }

}