package app.foodbear.foodbearapp.Fragment

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
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
import app.foodbear.foodbearapp.*
import app.foodbear.foodbearapp.R


import app.foodbear.foodbearapp.Utils.FirebaseUtils.storageReference
import app.foodbear.foodbearapp.db.Card.CardViewModel
import app.foodbear.foodbearapp.loadingDialog
import com.google.android.gms.tasks.Continuation

import com.google.android.gms.tasks.Task

import com.google.firebase.storage.UploadTask
import de.hdodenhof.circleimageview.CircleImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import java.util.*



class PerfilFragment : Fragment() {



    lateinit var iconoImagen: CircleImageView

    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri? = null
    private lateinit var loadingDialog: loadingDialog

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

        iconoImagen = view.findViewById(R.id.iconoImagen)
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
            val email = bundle!!.getString("message")
            var intent = Intent(context, AjustesActivity::class.java)
            intent.putExtra("emailU", email)
            startActivity(intent)
        }

        iconoImagen.setOnClickListener {

            val popupMenu: PopupMenu = PopupMenu(context,iconoImagen)

            popupMenu.menuInflater.inflate(R.menu.cambiar_foto,popupMenu.menu)
            popupMenu.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item ->
                when(item.itemId) {
                    R.id.galeria ->
                        launchGallery()
                    R.id.camara ->
                        uploadImage()

                }
                true
            })
            popupMenu.show()

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

            val bundle = arguments
            val email = bundle!!.getString("message")
            val queque = Volley.newRequestQueue(context)
            val url = "http://foodbearapp.atwebpages.com/login.php?email=$email"
            val jsonObjectRequest = JsonObjectRequest(
                Request.Method.GET, url, null, Response.Listener{ response ->
                    nombreUsuario.setText(response.getString("nombre"))
                }, Response.ErrorListener { error ->
                    Toast.makeText(context, "Carga de datos fallida", Toast.LENGTH_LONG).show()
                })
            queque.add(jsonObjectRequest)


            withContext(Dispatchers.Main){

                perfilUsuario.text = email
                showLayout()
            }


        }catch (e:Exception){

        }
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Seleccione una imagen"), PICK_IMAGE_REQUEST)
    }

    private fun uploadImage(){

        if(filePath != null){
            val ref = storageReference?.child("profile_Image/" + UUID.randomUUID().toString())
            val uploadTask = ref?.putFile(filePath!!)

            val urlTask = uploadTask?.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                if (!task.isSuccessful) {
                    task.exception?.let {
                        throw it
                    }
                }
                return@Continuation ref.downloadUrl
            })?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val downloadUri = task.result


                    // show save...


                } else {
                    // Handle failures
                }
            }?.addOnFailureListener{

            }
        }else{

            Toast.makeText(context, "Suba una imagen", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if(data == null || data.data == null){
                return
            }

            filePath = data.data
            try {
                val bitmap = MediaStore.Images.Media.getBitmap(context?.contentResolver, filePath)
                iconoImagen.setImageBitmap(bitmap)
                subirImagen.visibility = View.VISIBLE
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

}