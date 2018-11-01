package co.savsolutions.daltodesigner.ui.main

import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.Navigation
import co.savsolutions.daltodesigner.R
import co.savsolutions.daltodesigner.frgAreaDibujo

class MainFragment : Fragment() {

    companion object {
        fun newInstance() = MainFragment()
    }

    private lateinit var viewModel: MainViewModel
    private var fragmento: Fragment? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.main_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        // TODO: Use the ViewModel


        // Set up a click listener on the login button
        view?.findViewById<Button>(R.id.btnLanzar)?.setOnClickListener {

            //
            val bdl = Bundle()

            fragmento = frgAreaDibujo()
            bdl.putString("param1","https://heytaxisavsolutions.wordpress.com/contacto/")
            fragmento!!.arguments = bdl


            // Navigate to the login destination
            view?.let { Navigation.findNavController(it).navigate(R.id.frgAreaDibujo2) }
        }
    }

}
