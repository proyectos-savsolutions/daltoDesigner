package co.savsolutions.daltodesigner


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_frg_area_dibujo.*
import kotlinx.android.synthetic.main.fragment_frg_filtros_colores.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [frgFiltrosColores.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [frgFiltrosColores.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class frgFiltrosColores() : Fragment()  {


    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var param3: Bitmap? = null
    private var listener: OnFragmentInteractionListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment


        return inflater.inflate(R.layout.fragment_frg_filtros_colores, container, false)
    }




    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        // Set up a click listener on the login button
        view?.findViewById<Button>(R.id.btnCompartirDiseno)?.setOnClickListener {
            // Navigate to the login destination
            view?.let { Navigation.findNavController(it).navigate(R.id.frgCompartirDiseno2) }
        }

        spnTipoDaltonismo.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (parent!!.id == spnTipoDaltonismo.id)
                {
                    aplicarFiltroParaDoltonismo(0)
                }
            }


        }


    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }


    fun applyHueFilter(source: Bitmap, level: Int) : Bitmap
    {
        // get image size
        var width :Int =  source.getWidth()
        var height : Int  = source.getHeight()
        var  pixels = IntArray(width * height)
        var HSV = floatArrayOf(0.0F, 0.0F, 0.0F)


        // get pixel array from source
        source.getPixels(pixels, 0, width, 0, 0, width, height);

        var index : Int = 0
        // iteration through pixels

        for(y  in  0 .. height)
        {
            for( x in 0 .. width)
            {
                // get current index in 2D-matrix
                index = y * width + x;
                // convert to HSV
                Color.colorToHSV(pixels[index], HSV);
                // increase Saturation level
                HSV[0] = HSV[0] * level;
                HSV[0] =  Math.max(0.0F, Math.min(HSV[0], 360.0F)) as Float
                // take color back

                pixels[index] = Color.HSVToColor(HSV)
            }
        }

        // output bitmap
        var bmOut : Bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bmOut.setPixels(pixels, 0, width, 0, 0, width, height);
        return bmOut;
    }

    fun aplicarFiltroParaDoltonismo(tipoDaltonismo : Int)
    {

      // let { imgVwImagenFiltro.setImageBitmap( applyHueFilter(cnvEspacioDibujoFlitro.mBitmap!!, 3)) }
    }


    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment frgFiltrosColores.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                frgFiltrosColores().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }

}
