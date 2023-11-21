package app.foodbear.foodbearapp.Adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import app.foodbear.foodbearapp.Model.Alimento
import app.foodbear.foodbearapp.ProductoDescipcionActivity
import app.foodbear.foodbearapp.R

class OfertaProductosAdapter(private val ofertasAlimentoList: ArrayList<Alimento>, context: Context):  RecyclerView.Adapter<OfertaProductosAdapter.ViewHolder>()  {

    val ctx: Context = context


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val alimentoView = LayoutInflater.from(parent.context).inflate(R.layout.mostrar_producto,parent,false)
        return OfertaProductosAdapter.ViewHolder(alimentoView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val alimento: Alimento = ofertasAlimentoList[position]
        holder.tienda.text = alimento.tiendaAlimento
        holder.alimento.text = alimento.nombreAlimento
        holder.precio.text = "$"+ alimento.precioAlimento
        holder.valoraciones.rating = alimento.valoracionAlimento

        Glide.with(ctx)
            .load(alimento.imagenAlimento)
            .placeholder(R.drawable.logoapp)
            .into(holder.ImagenDelAlimento)


        if(alimento.disponibleAlimento == true){
            holder.desc.text = alimento.descuentoAlimento
            holder.descuento.visibility = View.VISIBLE
        }

        if(alimento.disponibleAlimento == false){

            holder.descuento.visibility = View.VISIBLE
            holder.desc.text = "New"

        }

        holder.itemView.setOnClickListener {
            goDetailsPage(position)
        }
    }

    override fun getItemCount(): Int {
        return ofertasAlimentoList.size
    }





    public class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val ImagenDelAlimento: ImageView = itemView.findViewById(R.id.fotoAlimento)
        val valoraciones: RatingBar = itemView.findViewById(R.id.estrellasValoraciones)
        val tienda: TextView = itemView.findViewById(R.id.nombreTienda)
        val desc: TextView = itemView.findViewById(R.id.decuentoEnAlimento)
        val alimento: TextView = itemView.findViewById(R.id.nombreDelAlimento)
        val precio: TextView = itemView.findViewById(R.id.precio)
        val descuento = itemView.findViewById<LinearLayout>(R.id.descuento2)


    }

    private fun goDetailsPage(position: Int) {
        val intent = Intent(ctx , ProductoDescipcionActivity::class.java)
        intent.putExtra("ProductIndex", position)
        intent.putExtra("ProductFrom", "Cover")
        ctx.startActivity(intent)
    }
}