package app.foodbear.foodbearapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import app.foodbear.foodbearapp.R
import app.foodbear.foodbearapp.db.ProductEntity

class CarritoAdapter(private val ctx: Context, val listener:CartItemClickAdapter ):RecyclerView.Adapter<CarritoAdapter.CartViewHolder>() {

    private val listaCarrito: ArrayList<ProductEntity> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val carritoView = LayoutInflater.from(ctx).inflate(R.layout.producto_carrito,parent,false)

        return CartViewHolder(carritoView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {


        val carritoItem:ProductEntity = listaCarrito[position]

        holder.carritoNombre.text = carritoItem.name
        holder.carritoPrecio.text = "$"+ carritoItem.price
        holder.carritoCantidad.text = carritoItem.qua.toString()
        holder.agregarACarrito.setOnClickListener {

        }

        Glide.with(ctx)
            .load(carritoItem.Image)
            .into(holder.imagenCarrito)

        holder.agregarACarrito.setOnClickListener {
            listener.onItemDeleteClick(carritoItem)
        }
    }

    override fun getItemCount(): Int {
        return listaCarrito.size
    }



    public class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val imagenCarrito: ImageView = itemView.findViewById(R.id.imgAlimento)
        val agregarACarrito: ImageView = itemView.findViewById(R.id.agregar)
        val carritoNombre: TextView = itemView.findViewById(R.id.nombreAlimento)
        val carritoPrecio: TextView = itemView.findViewById(R.id.precioAlimento)
        val carritoCantidad: TextView = itemView.findViewById(R.id.cantidadAlimento)


    }

    fun updateList(newList: List<ProductEntity>){
        listaCarrito.clear()
        listaCarrito.addAll(newList)
        notifyDataSetChanged()
    }


}

interface CartItemClickAdapter{
    fun onItemDeleteClick(product: ProductEntity)
    fun onItemUpdateClick(product: ProductEntity)


}