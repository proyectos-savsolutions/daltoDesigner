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
    var mCanvasNatural : Canvas? = null
    var mPath : Path = Path()
    var mPaint : Paint = Paint()
    var mPaintNatural : Paint = Paint()
    var tramos = HashMap<Int, tramo>()
    var mX : Float  = 0.0F
    var mY : Float = 0.0F
     internal val TOLERANCE : Int = 5

    internal var contexto:Context? = null

    fun CanvasView (context: Context?)
    {
        contexto = context


    }


    init {
        //super (context, attrs)
       // contexto = context

        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4F


        mPaintNatural.color = Color.BLUE
        mPaintNatural.style = Paint.Style.STROKE
        mPaintNatural.strokeJoin = Paint.Join.ROUND
        mPaintNatural.strokeWidth = 4F

        val nuevoTramo:tramo = tramo()
        nuevoTramo.mPaint = mPaint;
        tramos.set( indiceTramos,nuevoTramo)

    }

     override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

         mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888 )
         mCanvas = Canvas(mBitmap!!)

         mBitmapNatural = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888 )
         mCanvasNatural = Canvas(mBitmapNatural!!)

    }

    public fun ajustarPincel()
    {
        mPaint.color = Color.RED
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4F
    }

    public fun colorDelPincel(colorPincel:Int)
    {
        //mPaint.color = colorPincel
        //mPath.close()
        //tramos.get(indiceTramos)!!.mPath.close()


        val nuevoTramo:tramo = tramo()
        nuevoTramo.mPaint = Paint()
        nuevoTramo.mPaint.color = colorPincel
        nuevoTramo.mPaint.style = Paint.Style.STROKE
        nuevoTramo.mPaint.strokeJoin = Paint.Join.ROUND
        nuevoTramo.mPaint.strokeWidth = 1F

        nuevoTramo.mPath = Path()

        indiceTramos = indiceTramos + 1
        tramos.set( indiceTramos,nuevoTramo)

    }

    public fun colorDelPincelNatural(colorPincel:Int)
    {
        mPaintNatural.color = colorPincel
        val nuevoTramo:tramo = tramo()
        nuevoTramo.mPaint = mPaintNatural;
        indiceTramos = indiceTramos + 1
        tramos.set( indiceTramos,nuevoTramo)

    }

    fun startTouch(x:Float, y:Float)
    {
        tramos.get(indiceTramos)!!.mPath.moveTo(x, y)
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
            mX = x
            mY = y
        }
    }

    fun clearCanvas()
    {
        tramos.get(indiceTramos)!!.mPath.reset()
        invalidate()
    }

    fun upTouch()
    {
        tramos.get(indiceTramos)!!.mPath.lineTo(mX, mY)
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

        // Dibujar todos los tramos que se han creado hasta el momento
        for (i in 0 .. indiceTramos ) {
            canvas?.drawPath(tramos.get(i)!!.mPath, tramos.get(i)!!.mPaint)
        }

    }


}