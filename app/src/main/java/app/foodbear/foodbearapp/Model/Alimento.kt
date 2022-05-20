package app.foodbear.foodbearapp.Model

data class Alimento (
    val nombreAlimento:String = "",
    val idAlimento:String = "",
    val precioAlimento:String = "",
    val descripcionAlimento:String = "",
    val valoracionAlimento: Float = 0.0F,
    val descuentoAlimento:String = "",
    val disponibleAlimento:Boolean = false,
    val tiendaAlimento:String = "",
    val imagenAlimento:String = "",
    val categoriaAlimento:String = "",
    val etiquetaAlimento:String = "",
)