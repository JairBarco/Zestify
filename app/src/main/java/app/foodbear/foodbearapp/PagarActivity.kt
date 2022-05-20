package app.foodbear.foodbearapp

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import app.foodbear.foodbearapp.Utils.validarTarjeta.isValid
import app.foodbear.foodbearapp.Utils.Extensions.toast
import app.foodbear.foodbearapp.db.Card.CardEntity
import app.foodbear.foodbearapp.db.Card.CardViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.zxing.BarcodeFormat
import com.journeyapps.barcodescanner.BarcodeEncoder

class PagarActivity : AppCompatActivity(){

    lateinit var bottomSheetDialod: BottomSheetDialog
    lateinit var bottomSheetView: View
    lateinit var cardRec: RecyclerView
    private lateinit var cardViewModel: CardViewModel
    lateinit var codeQR: ImageView
    lateinit var txt2: TextView
    lateinit var txt4: TextView
    lateinit var txt5: TextView
    lateinit var Item: ArrayList<CardEntity>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pagar)

        cardRec = findViewById(R.id.cardRecView)
        val icRegresar = findViewById<ImageView>(R.id.icRegresar2)
        codeQR = findViewById<ImageView>(R.id.codigoQR)
        txt2 = findViewById(R.id.textView2)
        txt4 = findViewById(R.id.textView4)
        txt5 = findViewById(R.id.textView5)
        val irInicio = findViewById<FloatingActionButton>(R.id.irInicio)

        Item = arrayListOf()
        cardViewModel = ViewModelProviders.of(this).get(CardViewModel::class.java)
        cardRec.layoutManager = LinearLayoutManager(this)

        icRegresar.setOnClickListener {
            onBackPressed()
        }

        bottomSheetDialod = BottomSheetDialog(
            this, R.style.BottomSheetDialogTheme
        )

        bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.agregar_datos_tarjeta,
            findViewById<ConstraintLayout>(R.id.cardBottomSheet)
        )

      irInicio.setOnClickListener {
          val email = intent.getStringExtra("email").toString()
          var intent = Intent(this, InicioActivity::class.java)
          intent.putExtra("email", email)
          startActivity(intent)
          finish()
        }
        ventanaEmergente()

    }

    private fun ventanaEmergente(){

        bottomSheetView.findViewById<EditText>(R.id.campoNombre).text.clear()
        bottomSheetView.findViewById<EditText>(R.id.campoNumeroTarjeta).text.clear()
        bottomSheetView.findViewById<EditText>(R.id.campoVencimiento).text.clear()
        bottomSheetView.findViewById<EditText>(R.id.campoCVV).text.clear()
        bottomSheetView.findViewById<EditText>(R.id.direccion).text.clear()
        bottomSheetView.findViewById<EditText>(R.id.codigoPostal).text.clear()

        bottomSheetView.findViewById<Button>(R.id.pagarBoton).setOnClickListener {
            if((bottomSheetView.findViewById<EditText>(R.id.campoNombre).text.isEmpty()) and (bottomSheetView.findViewById<EditText>(R.id.campoNumeroTarjeta).text.isEmpty()) and (bottomSheetView.findViewById<EditText>(R.id.direccion).text.isEmpty()) and (bottomSheetView.findViewById<EditText>(R.id.codigoPostal).text.isEmpty())) {
                toast("Ingrese todos los datos requeridos")
            }else{
                finPago()
            }
        }

        bottomSheetDialod.setContentView(bottomSheetView)
        bottomSheetDialod.show()
    }

    private fun finPago() {

        val nombreCompleto:String = bottomSheetView.findViewById<EditText>(R.id.campoNombre).text.toString()
        val direccion:String = bottomSheetView.findViewById<EditText>(R.id.direccion).text.toString()
        val codigop:String = bottomSheetView.findViewById<EditText>(R.id.codigoPostal).text.toString()
        val total = intent.getStringExtra("total").toString()
        var contenidoQR = "$nombreCompleto ha realizado esta compra.\n\nDirección de pago: $direccion\nCodigo postal: $codigop\nPago realizado total......$total"
        if(!isValid(bottomSheetView.findViewById<EditText>(R.id.campoNumeroTarjeta).text.toString().toLong())) {

            toast("Pago realizado")

            bottomSheetDialod.dismiss()

            var barcodeEncoder: BarcodeEncoder = BarcodeEncoder()
            var bitmap: Bitmap = barcodeEncoder.encodeBitmap(
                contenidoQR,
                BarcodeFormat.QR_CODE,
                400,
                400
            )
            codeQR.setImageBitmap(bitmap)
            codeQR.visibility = View.VISIBLE
            txt2.visibility = View.VISIBLE
            txt4.visibility = View.VISIBLE
            txt5.visibility = View.VISIBLE

        }
        else{
            toast("Pago no realizado")
        }

    }
}

