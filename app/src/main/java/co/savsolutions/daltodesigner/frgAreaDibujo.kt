package co.savsolutions.daltodesigner


import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.service.autofill.Validators.not
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
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
 * [frgAreaDibujo.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [frgAreaDibujo.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class frgAreaDibujo : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var listener: OnFragmentInteractionListener? = null

    private var canvas:CanvasView? = null

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
        return inflater.inflate(R.layout.fragment_frg_area_dibujo, container, false)
    }

    // TODO: Rename method, update argument and hook method into UI event
    fun onButtonPressed(uri: Uri) {
        listener?.onFragmentInteraction(uri)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        canvas = CanvasView (context!!,null,0)
        canvas = cnvEspacioDibujoFlitro
        canvas!!.ajustarPincel()

        // Escucha para pasar a la vista de aplicar filtros
        view?.findViewById<Button>(R.id.btnAplicarFiltros)?.setOnClickListener {
            // Navigate to the login destination
            view?.let { Navigation.findNavController(it).navigate(R.id.frgFiltrosColores2) }
        }

        // Escucha para el botón de Color Azul
        view?.findViewById<Button>(R.id.btnColorAzul)?.setOnClickListener {
            canvas!!.colorDelPincel(Color.BLUE)
        }

        // Escucha para el botón de Color Naranja
        view?.findViewById<Button>(R.id.btnColorNaranja)?.setOnClickListener {
            canvas!!.colorDelPincel(Color.YELLOW)
        }

        // Escucha para el botón de Color Verde
        view?.findViewById<Button>(R.id.btnColorVerde)?.setOnClickListener {
            canvas!!.colorDelPincel(Color.GREEN)
        }

        // Escucha para el botón de Color Purpura
        view?.findViewById<Button>(R.id.btnColorPurpura)?.setOnClickListener {
            canvas!!.colorDelPincel(Color.MAGENTA)
        }

        // Escucha para el botón de Color Rojo
        view?.findViewById<Button>(R.id.btnColorRojo)?.setOnClickListener {
            canvas!!.colorDelPincel(Color.RED)
        }


        spnFiltrosMientrasDibuja.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

                if (parent!!.id == spnFiltrosMientrasDibuja.id)
                {
                    aplicarFiltroParaDoltonismo(0)
                }
            }


        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException(context.toString() + " must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
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
         * @return A new instance of fragment frgAreaDibujo.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                frgAreaDibujo().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }


                }
    }

    /*
    Efectos gráficos
     */

    fun aplicarFiltroParaDoltonismo(tipoDaltonismo : Int)
    {

         let { cnvEspacioDibujoFlitro.mBitmap =  applyHueFilter(cnvEspacioDibujoFlitro.mBitmap!!, 3) }

        cnvEspacioDibujoFlitro.mCanvas!!.drawBitmap(applyHueFilter(cnvEspacioDibujoFlitro.mBitmap!!, 3),0.0F,0.0F,cnvEspacioDibujoFlitro.mPaint)
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

        for(y  in  0 .. height-1)
        {
            for( x in 0 .. width-1)
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
}


