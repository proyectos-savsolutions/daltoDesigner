package co.savsolutions.daltodesigner


import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

class CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr)
{
    class tramo
    {
        var mPath : Path = Path()
        var mPaint : Paint = Paint()

        fun tramo()
        {
            mPaint.color = Color.BLACK
            mPaint.style = Paint.Style.STROKE
            mPaint.strokeJoin = Paint.Join.ROUND
            mPaint.strokeWidth = 4F
        }
    }

    var indiceTramos : Int = 0
    var ancho : Int = 0
    var alto : Int = 0

    var mBitmap : Bitmap? = null
    var mBitmapNatural : Bitmap? = null
    var mCanvas : Canvas? = null
    var mPath : Path = Path()
    var mPaint : Paint = Paint()
    var tramos = HashMap<Int, tramo>()
    var tramosNaturales = HashMap<Int, tramo>()
    var mX : Float  = 0.0F
    var mY : Float = 0.0F
     internal val TOLERANCE : Int = 5

    internal var contexto:Context? = null

    var conjuntoColoresUtilizar : Int = 0

    fun CanvasView (context: Context?)
    {
        contexto = context


    }


    init {
        //super (context, attrs)
       // contexto = context


        val nuevoTramo:tramo = tramo()
        nuevoTramo.mPaint.color = Color.BLACK
        nuevoTramo.mPaint.style = Paint.Style.STROKE
        nuevoTramo.mPaint.strokeJoin = Paint.Join.ROUND
        nuevoTramo.mPaint.strokeWidth = 4F
        tramos.set( indiceTramos,nuevoTramo)


        val nuevoTramoNatural:tramo = tramo()
        nuevoTramoNatural.mPaint.color = Color.BLACK
        nuevoTramoNatural.mPaint.style = Paint.Style.STROKE
        nuevoTramoNatural.mPaint.strokeJoin = Paint.Join.ROUND
        nuevoTramoNatural.mPaint.strokeWidth = 4F
        tramosNaturales.set( indiceTramos,nuevoTramoNatural)
    }

     override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

         mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888 )
         mCanvas = Canvas(mBitmap!!)

         mBitmapNatural = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888 )

    }

    public fun ajustarPincel()
    {
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4F
    }

    public fun colorDelPincel(colorPincel:Int,  colorComunPincel: Int)
    {
        //mPaint.color = colorPincel
        //mPath.close()
        //tramos.get(indiceTramos)!!.mPath.close()


        indiceTramos = indiceTramos + 1

        val nuevoTramo:tramo = tramo()
        nuevoTramo.mPaint = Paint()
        nuevoTramo.mPaint.color = colorPincel
        nuevoTramo.mPaint.style = Paint.Style.STROKE
        nuevoTramo.mPaint.strokeJoin = Paint.Join.ROUND
        nuevoTramo.mPaint.strokeWidth = 4F

        nuevoTramo.mPath = Path()

        tramos.set( indiceTramos,nuevoTramo)

        val nuevoTramoNatural:tramo = tramo()
        nuevoTramoNatural.mPaint = Paint()
        nuevoTramoNatural.mPaint.color = colorComunPincel
        nuevoTramoNatural.mPaint.style = Paint.Style.STROKE
        nuevoTramoNatural.mPaint.strokeJoin = Paint.Join.ROUND
        nuevoTramoNatural.mPaint.strokeWidth = 2F

        nuevoTramoNatural.mPath = Path()

        tramosNaturales.set( indiceTramos,nuevoTramoNatural)

    }



    fun startTouch(x:Float, y:Float)
    {
        tramos.get(indiceTramos)!!.mPath.moveTo(x, y)
        tramosNaturales.get(indiceTramos)!!.mPath.moveTo(x, y)
        mX = x
        mY = y
    }


    fun moveTouch(x:Float, y:Float)
    {
        var dx = Math.abs(x - mX)
        var dy = Math.abs(y - mY)

        if (dx >= TOLERANCE || dy >= TOLERANCE)
        {
            tramos.get(indiceTramos)!!.mPath.quadTo(mX, mY,  (x+ mX) /2, (y + mY) /2 )
            tramosNaturales.get(indiceTramos)!!.mPath.quadTo(mX, mY,  (x+ mX) /2, (y + mY) /2 )
            mX = x
            mY = y
        }
    }

    fun clearCanvas()
    {
        tramos.get(indiceTramos)!!.mPath.reset()
        tramosNaturales.get(indiceTramos)!!.mPath.reset()
        invalidate()
    }

    fun upTouch()
    {
        tramos.get(indiceTramos)!!.mPath.lineTo(mX, mY)
        tramosNaturales.get(indiceTramos)!!.mPath.lineTo(mX, mY)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {

        var x : Float  = (if (event != null) event.getX() else 0.0F)
        var y : Float  = (if (event != null) event.getY() else 0.0F)

        when (event?.action)
        {
            MotionEvent.ACTION_DOWN ->
            {
                startTouch(x,y) // Inicia registro del movimiento
                invalidate()
            }
            MotionEvent.ACTION_MOVE ->
            {
                moveTouch(x,y) // Registrar el movimiento del dedo
                invalidate()
            }
            MotionEvent.ACTION_UP ->
            {
                upTouch()  // Realiza el dibujo según la especificación
                invalidate()
            }

        }

        return true; //super.onTouchEvent(event)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        // canvas?.drawColor(mPaint.color)
        when (conjuntoColoresUtilizar )
        {
            0 ->
            // Dibujar todos los tramos que se han creado hasta el momento
            for (i in 0 .. indiceTramos ) {
                canvas?.drawPath(tramos.get(i)!!.mPath, tramos.get(i)!!.mPaint)
            }
            1 ->
                // Dibujar todos los tramos que se han creado hasta el momento usando los colores considerados comunes
                for (i in 0 .. indiceTramos ) {
                    canvas?.drawPath(tramosNaturales.get(i)!!.mPath, tramosNaturales.get(i)!!.mPaint)
                }
        }


    }


}