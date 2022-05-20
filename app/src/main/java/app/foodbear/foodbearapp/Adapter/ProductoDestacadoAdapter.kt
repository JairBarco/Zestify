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

class ProductoDestacadoAdapter(var ctx: Context, private val coverAlimentoList: ArrayList<Alimento>) :RecyclerView.Adapter<ProductoDestacadoAdapter.ViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val productView = LayoutInflater.from(parent.context).inflate(R.layout.producto_destacado,parent,false)
        return ProductoDestacadoAdapter.ViewHolder(productView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        val coverPro:Alimento = coverAlimentoList[position]

        holder.productNoteCover.text = coverPro.etiquetaAlimento
        Glide.with(ctx)
            .load(coverPro.imagenAlimento)
            .into(holder.productImage_coverPage)


        holder.productCheck_coverPage.setOnClickListener {

            goDetailsPage(position)


        }

    }




    override fun getItemCount(): Int {
        return coverAlimentoList.size
    }

    public class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){

        val productImage_coverPage: ImageView = itemView.findViewById(R.id.productImage_coverPage)
        val productNoteCover: TextView = itemView.findViewById(R.id.productNoteCover)
        val productCheck_coverPage: Button = itemView.findViewById(R.id.productCheck_coverPage)


    }

    private fun goDetailsPage(position: Int) {
        val intent = Intent(ctx , ProductoDescipcionActivity::class.java)
        intent.putExtra("ProductIndex", position)
        intent.putExtra("ProductFrom", "Cover")
        ctx.startActivity(intent)
    }
}