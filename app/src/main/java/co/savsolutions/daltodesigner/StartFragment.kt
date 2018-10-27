package co.savsolutions.daltodesigner


import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_start.*

class StartFragment : Fragment() {

    private lateinit var viewModel: StartViewModel
    private var fragmento: Fragment? = null

    companion object {
        fun newInstance() = StartFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        // Obtain ViewModel from ViewModelProviders, using this fragment as LifecycleOwner.
        viewModel = ViewModelProviders.of(this).get(StartViewModel::class.java)

        // Observe data on the ViewModel, exposed as a LiveData
        viewModel.data.observe(this, Observer { data ->
            // Set the text exposed by the LiveData
            view?.findViewById<TextView>(R.id.text)?.text = data
        })

        // Set up a click listener on the login button
        view?.findViewById<Button>(R.id.btnNuevoDiseno)?.setOnClickListener {

            //
            val bdl = Bundle()

            fragmento = frgAreaDibujo()
            bdl.putString("param1","https://heytaxisavsolutions.wordpress.com/contacto/")
            fragmento!!.arguments = bdl


            // Navigate to the login destination
            view?.let { Navigation.findNavController(it).navigate(R.id.frgAreaDibujo) }
        }

        // Set up a click listener on the login button
        view?.findViewById<Button>(R.id.btnCargarDiseno)?.setOnClickListener {
            // Navigate to the login destination
            view?.let { Navigation.findNavController(it).navigate(R.id.frgCargar_Diseno) }
        }

        // Set up a click listener on the login button
        view?.findViewById<Button>(R.id.navigate_bt)?.setOnClickListener {
            // Navigate to the login destination
            view?.let { Navigation.findNavController(it).navigate(R.id.EndFragment) }
        }
    }


}