package app.foodbear.foodbearapp

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ChatBot : AppCompatActivity() {
    private lateinit var chatListView: ListView
    private lateinit var chatAdapter: ArrayAdapter<String>
    private val chatMessages = ArrayList<String>()

    private lateinit var questionsListView: ListView
    private lateinit var questionsAdapter: ArrayAdapter<String>
    private val questions = ArrayList<String>()

    private val answers = mapOf(
        "¿Cuáles son los métodos de pago disponibles?" to "Además de tarjetas de crédito, aceptamos opciones de pago como PayPal y transferencias bancarias.",
        "¿Cuánto tiempo tarda la entrega de los productos?" to "El tiempo de entrega depende de tu ubicación y la opción de entrega que elijas. Normalmente, entregamos en pocos días hábiles.",
        "¿Ofrecen descuentos por cantidad?" to "Sí, ofrecemos descuentos por la compra de cantidades mayores de productos cercanos a la fecha de caducar.",
        "¿Tienen productos orgánicos cercanos a la fecha de caducar?" to "Sí, tenemos una selección de productos orgánicos cercanos a la fecha de caducar.",
        "¿Se pueden devolver los productos?" to "Sí, aceptamos devoluciones según nuestra política de devoluciones. Puedes encontrar más información en nuestra aplicación.",
        "¿Qué hago si el producto está dañado?" to "Si un producto llega dañado, comunícate con nuestro servicio de atención al cliente y te ayudaremos a resolver el problema.",
        "¿Puedo cancelar mi pedido?" to "Sí, puedes cancelar tu pedido si lo haces dentro del período permitido. Comunícate con nuestro servicio de atención al cliente para obtener ayuda."
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.chatbot)

        chatListView = findViewById(R.id.chatListView)
        chatAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, chatMessages)
        chatListView.adapter = chatAdapter

        questionsListView = findViewById(R.id.questionsListView)
        questionsAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, questions)
        questionsListView.adapter = questionsAdapter

        questions.addAll(answers.keys) // Agrega las preguntas al ListView de selección

        questionsListView.setOnItemClickListener { _, _, position, _ ->
            val selectedQuestion = questionsAdapter.getItem(position)
            if (selectedQuestion != null) {
                showAnswer(selectedQuestion)
            }
        }
    }

    private fun showAnswer(question: String) {
        val answer = answers[question]
        if (answer != null) {
            chatMessages.add("Usuario: $question")
            chatMessages.add("Bot: $answer")
            chatAdapter.notifyDataSetChanged()

            // Desplázate automáticamente hacia la última entrada en la lista
            chatListView.setSelection(chatMessages.size - 1)
        } else {
            Toast.makeText(this, "Lo siento, no tengo una respuesta para esa pregunta.", Toast.LENGTH_SHORT).show()
        }
    }
}
