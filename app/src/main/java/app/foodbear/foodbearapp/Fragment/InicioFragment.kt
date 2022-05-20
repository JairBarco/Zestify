package app.foodbear.foodbearapp.Fragment


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

import androidx.recyclerview.widget.RecyclerView
import app.foodbear.foodbearapp.Adapter.NuevosProductosAdapter
import com.airbnb.lottie.LottieAnimationView
import app.foodbear.foodbearapp.Adapter.ProductoDestacadoAdapter
import app.foodbear.foodbearapp.Adapter.OfertaProductosAdapter

import app.foodbear.foodbearapp.Model.Alimento

import app.foodbear.foodbearapp.R
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException


class InicioFragment : Fragment() {

//    NuevosAlimentos.json

    lateinit var coverRecView:RecyclerView
    lateinit var aNuevosRecView:RecyclerView
    lateinit var aOfertaRecView:RecyclerView
    lateinit var coverAlimento:ArrayList<Alimento>
    lateinit var newAlimento:ArrayList<Alimento>
    lateinit var saleAlimento:ArrayList<Alimento>

    lateinit var ProductoDestacadoAdapter: ProductoDestacadoAdapter
    lateinit var newNuevosProductosAdapter: NuevosProductosAdapter
    lateinit var ofertaProductosAdapter: OfertaProductosAdapter

    lateinit var animacionView: LottieAnimationView

    lateinit var alimentosNuevos:LinearLayout
    lateinit var alimentosOferta:LinearLayout



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_inicio, container, false)

        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        coverAlimento = arrayListOf()
        newAlimento = arrayListOf()
        saleAlimento = arrayListOf()

        coverRecView = view.findViewById(R.id.coverRecView)
        aNuevosRecView = view.findViewById(R.id.aNuevosRecView)
        aOfertaRecView = view.findViewById(R.id.aOfertaRecView)
        alimentosNuevos = view.findViewById(R.id.alimentosNuevos)
        alimentosOferta = view.findViewById(R.id.alimentosOferta)
        animacionView = view.findViewById(R.id.animacionView)

        hideLayout()

        mostrarAlimentosOferta()
        mostrarAlimentosNuevos()

        coverRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        coverRecView.setHasFixedSize(true)
        ProductoDestacadoAdapter = ProductoDestacadoAdapter(activity as Context, coverAlimento )
        coverRecView.adapter = ProductoDestacadoAdapter



        aNuevosRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        aNuevosRecView.setHasFixedSize(true)
        newNuevosProductosAdapter = NuevosProductosAdapter(newAlimento, activity as Context)
        aNuevosRecView.adapter = newNuevosProductosAdapter


        aOfertaRecView.layoutManager = LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL, false)
        aOfertaRecView.setHasFixedSize(true)
        ofertaProductosAdapter = OfertaProductosAdapter(saleAlimento, activity as Context )
        aOfertaRecView.adapter = ofertaProductosAdapter

        showLayout()

        return view
    }



    private fun hideLayout(){
        animacionView.playAnimation()
        animacionView.loop(true)
        coverRecView.visibility = View.GONE
        alimentosNuevos.visibility = View.GONE
        alimentosOferta.visibility = View.GONE
        animacionView.visibility = View.VISIBLE
    }
    private fun showLayout(){
        animacionView.pauseAnimation()
        animacionView.visibility = View.GONE
        coverRecView.visibility = View.VISIBLE
        alimentosNuevos.visibility = View.VISIBLE
        alimentosOferta.visibility = View.VISIBLE
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

    private fun mostrarAlimentosOferta() {

        val jsonFileString = context?.let {

            getJsonData(it, "OfertaAlimentos.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Alimento>>() {}.type

        val coverD: List<Alimento> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, person ->

            coverAlimento.add(person)
            saleAlimento.add(person)

        }


    }

    private fun mostrarAlimentosNuevos() {

        val jsonFileString = context?.let {

            getJsonData(it, "NuevosAlimentos.json")
        }
        val gson = Gson()

        val listCoverType = object : TypeToken<List<Alimento>>() {}.type

        val coverD: List<Alimento> = gson.fromJson(jsonFileString, listCoverType)

        coverD.forEachIndexed { idx, person ->


            newAlimento.add(person)


        }


    }

}


