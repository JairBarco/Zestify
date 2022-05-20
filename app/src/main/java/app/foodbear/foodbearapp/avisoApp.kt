package app.foodbear.foodbearapp

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class avisoApp: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_aviso)
        val avisoBtn: Button = findViewById(R.id.avisoBtn)

        avisoBtn.setOnClickListener {
            val email = intent.getStringExtra("email").toString()
            val total = intent.getStringExtra("total").toString()
            var intent = Intent(this, PagarActivity::class.java)
            intent.putExtra("email", email)
            intent.putExtra("total", total)
            startActivity(intent)
            finish()
        }
    }
}