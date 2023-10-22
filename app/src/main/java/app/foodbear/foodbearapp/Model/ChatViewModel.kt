import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ChatViewModel : ViewModel() {
    private val _chatMessages = MutableLiveData<List<String>>()
    val chatMessages: LiveData<List<String>> = _chatMessages

    init {
        _chatMessages.value = emptyList()
    }

    fun sendMessage(message: String) {
        val currentMessages = _chatMessages.value.orEmpty().toMutableList()
        currentMessages.add("You: $message")
        _chatMessages.value = currentMessages

        // Simula la respuesta del chatbot
        val botResponse = "Bot: Gracias por tu mensaje: $message"
        currentMessages.add(botResponse)
        _chatMessages.value = currentMessages
    }
}
