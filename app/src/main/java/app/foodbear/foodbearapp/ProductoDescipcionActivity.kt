package app.foodbear.foodbearapp

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.foodbear.foodbearapp.Adapter.NuevosProductosAdapter
import app.foodbear.foodbearapp.Model.Alimento
import app.foodbear.foodbearapp.Utils.Extensions.toast
import app.foodbear.foodbearapp.db.CartViewModel
import app.foodbear.foodbearapp.db.ProductEntity
import com.bumptech.glide.Glide
import app.foodbear.foodbearapp.R
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException

class ProductoDescipcionActivity : AppCompatActivity() {

    var indexAlimento: Int = -1
    lateinit var alimentoFrom: String
    private lateinit var cartViewModel: CartViewModel
    private val TAG = "TAG"
    lateinit var imagenDelAlimento: ImageView
    lateinit var icRegresar: ImageView
    lateinit var alimentoNombre: TextView
    lateinit var alimentoPrecio: TextView
    lateinit var alimentoTitulo: TextView
    lateinit var descripcion: TextView
    lateinit var valoraciones: TextView
    lateinit var estrellasValoraciones: RatingBar



    lateinit var RecomRecView: RecyclerView
    lateinit var nuevoAlimentoAdapterNuevos: NuevosProductosAdapter
    lateinit var nuevosAlimentos: ArrayList<Alimento>

    lateinit var pNombreAlimento: String
    var cant: Int = 1
    var pPrecioAlimento: Int = 0
    var cantidad: Int = 1
    lateinit var pPid: String
    lateinit var pImagen: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_descripcion_producto)

        getWindow()?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);


        indexAlimento = intent.getIntExtra("ProductIndex", -1)
        alimentoFrom = intent.getStringExtra("ProductFrom").toString()

        imagenDelAlimento = findViewById(R.id.imagenDelAlimento)
        alimentoNombre = findViewById(R.id.alimentoNombre)
        alimentoPrecio = findViewById(R.id.alimentoPrecio)
        alimentoTitulo = findViewById(R.id.alimentoTitulo)
        descripcion = findViewById(R.id.descripcion)
        estrellasValoraciones = findViewById(R.id.estrellasValoraciones)
        valoraciones = findViewById(R.id.valoraciones)
        RecomRecView = findViewById(R.id.RecomRecView)
        icRegresar = findViewById(R.id.icRegresar)
        val btnAgregarCarrito: Button = findViewById(R.id.btnAgregarCarrito)

        nuevosAlimentos = arrayListOf()
        ponerDatos()
        extraerDatos()

        RecomRecView.layoutManager = LinearLayoutManager(
            this,
            LinearLayoutManager.HORIZONTAL, false
        )
        RecomRecView.setHasFixedSize(true)
        nuevoAlimentoAdapterNuevos = NuevosProductosAdapter(nuevosAlimentos, this)
        RecomRecView.adapter = nuevoAlimentoAdapterNuevos

        icRegresar.setOnClickListener {
            onBackPressed()
        }

        btnAgregarCarrito.setOnClickListener {

            val bottomSheetDialod = BottomSheetDialog(
                this, R.style.BottomSheetDialogTheme
            )

            val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
                R.layout.fragment_agregar_carrito,
                findViewById<ConstraintLayout>(R.id.ventanaEmergente)
            )

            bottomSheetView.findViewById<View>(R.id.agregarAlCarrito_BottomSheet).setOnClickListener {

                pPrecioAlimento *= bottomSheetView.findViewById<EditText>(R.id.btnCantidad).text.toString()
                    .toInt()
                cantidad *= bottomSheetView.findViewById<EditText>(R.id.btnCantidad).text.toString()
                    .toInt()
                agregarAlimentoAlCarrito()
                bottomSheetDialod.dismiss()
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.signoMenos).setOnClickListener {
                if(bottomSheetView.findViewById<EditText>(R.id.btnCantidad).text.toString()
                        .toInt() > 1){
                    cant--
                    bottomSheetView.findViewById<EditText>(R.id.btnCantidad).setText(cant.toString())
                }else{
                    toast("Ingrese una cantidad")
                }
            }

            bottomSheetView.findViewById<LinearLayout>(R.id.signoMas).setOnClickListener {
                if(bottomSheetView.findViewById<EditText>(R.id.btnCantidad).text.toString()
                        .toInt() < 10){
                    cant++
                    bottomSheetView.findViewById<EditText>(R.id.btnCantidad).setText(cant.toString())
                }
            }

            bottomSheetDialod.setContentView(bottomSheetView)
            bottomSheetDialod.show()
        }

    }

    private fun agregarAlimentoAlCarrito() {

        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        cartViewModel.insert(ProductEntity(pNombreAlimento, cantidad, pPrecioAlimento, pPid, pImagen))
        val productos = ArrayList<String>()
        productos.add(pNombreAlimento)
        productos.add(cant.toString())
        productos.add(pPrecioAlimento.toString())
        var intent = Intent(this, PagarActivity::class.java)
        intent.putStringArrayListExtra("productos", productos)
        toast("Agregado al carrito")
    }

    fun getJsonData(context: Context, fileName: String): String? {


        val jsonString: String
        try {
            jsonString = context.assets.open(fileName).bufferedReader().use { it.readText() }
        } catch (ioException: IOException) {
            ioException.printStackTrace()
            return null
        }

        return jsonString
    }

    private fun ponerDatos() {

        var fileJson: String = ""

        if (alimentoFrom.equals("Cover")) {
            fileJson = "OfertaAlimentos.json"
        }
        if (alimentoFrom.equals("New")) {
            fileJson = "NuevosAlimentos.json"
        }


        val jsonFileString = this?.let {

            getJsonData(it, fileJson)
        }

        val gson = Gson()


        val listCoverType = object : TypeToken<List<Alimento>>() {}.type

        var coverD: List<Alimento> = gson.fromJson(jsonFileString, listCoverType)

        Glide.with(applicationContext)
            .load(coverD[indexAlimento].imagenAlimento)
            .into(imagenDelAlimento)

        alimentoNombre.text = coverD[indexAlimento].nombreAlimento
        alimentoPrecio.text = "$" + coverD[indexAlimento].precioAlimento
        alimentoTitulo.text = coverD[indexAlimento].tiendaAlimento
        descripcion.text = coverD[indexAlimento].descripcionAlimento
        estrellasValoraciones.rating = coverD[indexAlimento].valoracionAlimento
        valoraciones.text = coverD[indexAlimento].valoracionAlimento.toString() + " estrellas"

        pNombreAlimento = coverD[indexAlimento].nombreAlimento
        pPrecioAlimento = coverD[indexAlimento].precioAlimento.toInt()
        pPid = coverD[indexAlimento].idAlimento
        pImagen = coverD[indexAlimento].imagenAlimento

    }

    private fun extraerDatos() {


        var fileJson: String = ""

        if (alimentoFrom.equals("Cover")) {
            fileJson = "NuevosAlimentos.json"
        }
        if (alimentoFrom.equals("New")) {
            fileJson = "OfertaAlimentos.json"
        }


        val jsonFileString = this?.let {

            getJsonData(it, fileJson)
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Alimento>>() {}.type

        var coverD: List<Alimento> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, person ->

            if (idx < 9) {
                nuevosAlimentos.add(person)
            }


        }


    }

}


