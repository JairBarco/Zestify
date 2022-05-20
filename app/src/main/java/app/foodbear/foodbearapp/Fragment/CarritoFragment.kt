package app.foodbear.foodbearapp.Fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle

import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast

import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProviders

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import app.foodbear.foodbearapp.Adapter.CarritoAdapter
import app.foodbear.foodbearapp.Adapter.CartItemClickAdapter

import app.foodbear.foodbearapp.R
import app.foodbear.foodbearapp.avisoApp


import app.foodbear.foodbearapp.db.CartViewModel
import app.foodbear.foodbearapp.db.ProductEntity

class CarritoFragment : Fragment(), CartItemClickAdapter {

    lateinit var carritoRecView:RecyclerView
    lateinit var carritoAdapter: CarritoAdapter
    lateinit var animationView: LottieAnimationView
    lateinit var montoTotal:TextView
    lateinit var Item: ArrayList<ProductEntity>
    private lateinit var cartViewModel: CartViewModel
    var sum:Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val view = inflater.inflate(R.layout.fragment_carrito, container, false)
        carritoRecView = view.findViewById(R.id.carritoRecView)
        animationView = view.findViewById(R.id.animationView)
        montoTotal = view.findViewById(R.id.montoTotal)
        val alimentosAgregadosRecView:LinearLayout = view.findViewById(R.id.alimentosAgregadosRecView)
        val animacionCarVacio:LinearLayout = view.findViewById(R.id.animacionCarVacio)
        val tituloCarrito:TextView = view.findViewById(R.id.tituloCarrito)
        val pagar:Button = view.findViewById(R.id.btnPagar)

        pagar.setOnClickListener {
            startActivity(Intent(context, avisoApp::class.java))
        }
        Item = arrayListOf()


        animationView.playAnimation()
        animationView.loop(true)
        alimentosAgregadosRecView.visibility = View.GONE
        tituloCarrito.visibility = View.GONE
        animacionCarVacio.visibility = View.VISIBLE


        carritoRecView.layoutManager = LinearLayoutManager(context)
        carritoAdapter = CarritoAdapter(activity as Context, this )
        carritoRecView.adapter = carritoAdapter


        cartViewModel = ViewModelProviders.of(this).get(CartViewModel::class.java)

        cartViewModel.allproducts.observe(viewLifecycleOwner, Observer {List ->
            List?.let {
                carritoAdapter.updateList(it)
                Item.clear()
                sum = 0
                Item.addAll(it)
            }

            if (List.size == 0){
                animationView.playAnimation()
                animationView.loop(true)
                alimentosAgregadosRecView.visibility = View.GONE
                tituloCarrito.visibility = View.GONE
                animacionCarVacio.visibility = View.VISIBLE

            }
            else{
                animacionCarVacio.visibility = View.GONE
                alimentosAgregadosRecView.visibility = View.VISIBLE
                tituloCarrito.visibility = View.VISIBLE
                animationView.pauseAnimation()
            }

            Item.forEach {
                sum += it.price
                //monto total
            }
            montoTotal.text = "$" + sum
        })

        return view
    }

    override fun onItemDeleteClick(product: ProductEntity) {
        cartViewModel.deleteCart(product)
        Toast.makeText(context,"Eliminado del carrito",Toast.LENGTH_SHORT).show()
    }

    override fun onItemUpdateClick(product: ProductEntity) {
        cartViewModel.updateCart(product)
    }


}