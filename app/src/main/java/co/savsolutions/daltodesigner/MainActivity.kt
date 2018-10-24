package co.savsolutions.daltodesigner

import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import co.savsolutions.daltodesigner.ui.main.MainFragment

class MainActivity : AppCompatActivity(),
    frgAreaDibujo.OnFragmentInteractionListener,
    frgCargar_Diseno.OnFragmentInteractionListener,
    frgFiltrosColores.OnFragmentInteractionListener,
    frgCompartirDiseno.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MainFragment.newInstance())
                .commitNow()
        }
    }

    override fun onFragmentInteraction(uri: Uri) {

        // Prueba
    }

}
