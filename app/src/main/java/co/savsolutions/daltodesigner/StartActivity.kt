package co.savsolutions.daltodesigner

import android.net.Uri
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

class StartActivity : AppCompatActivity() ,
        frgAreaDibujo.OnFragmentInteractionListener,
        frgCargar_Diseno.OnFragmentInteractionListener,
        frgFiltrosColores.OnFragmentInteractionListener,
        frgCompartirDiseno.OnFragmentInteractionListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)
    }

    override fun onFragmentInteraction(uri: Uri) {

        // Prueba
    }
}
