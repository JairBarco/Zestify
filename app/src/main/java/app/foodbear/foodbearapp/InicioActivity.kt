package app.foodbear.foodbearapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import app.foodbear.foodbearapp.Fragment.CarritoFragment
import app.foodbear.foodbearapp.Fragment.InicioFragment
import app.foodbear.foodbearapp.Fragment.MapaFragment
import app.foodbear.foodbearapp.Fragment.PerfilFragment
import app.foodbear.foodbearapp.Fragment.*
import com.google.android.material.bottomnavigation.BottomNavigationView

class InicioActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    lateinit var bottomNavigationView:BottomNavigationView




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_inicio)



        bottomNavigationView = findViewById(R.id.bottomNavMenu)
        bottomNavigationView.setOnNavigationItemSelectedListener(this)

        supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, InicioFragment() ).commit()
    }


    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        var email = intent.getStringExtra("email").toString()
        when (item.itemId) {
            R.id.inicioMenu -> {
                val fragment = InicioFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.carritoMenu -> {
                val fragment = CarritoFragment()
                val bundle = Bundle()
                bundle .putString("email", email)
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
           R.id.mapaMenu -> {
                val fragment = MapaFragment()
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
            R.id.perfilMenu -> {
                val fragment = PerfilFragment()
                val bundle = Bundle()
                bundle .putString("email", email)
                fragment.arguments = bundle
                supportFragmentManager.beginTransaction().replace(R.id.nav_fragment, fragment, fragment.javaClass.simpleName)
                    .commit()
                return true
            }
        }
        return false
    }
}


