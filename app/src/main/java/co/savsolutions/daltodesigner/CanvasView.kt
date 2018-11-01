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
    var ancho : Int = 0
    var alto : Int = 0

    var mBitmap : Bitmap? = null
    var mCanvas : Canvas? = null
    var mPath : Path = Path()
    var mPaint : Paint = Paint()
    var mX : Float  = 0.0F
    var mY : Float = 0.0F
     internal val TOLERANCE : Int = 5

    internal var contexto:Context? = null

    fun CanvasView (context: Context?)
    {
        contexto = context


    }

/*
    fun CanvasView @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
)
        //super (context, attrs)
        contexto = context

        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4F


    }
*/
     override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

         mBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888 )
         mCanvas = Canvas(mBitmap!!)

    }

    public fun ajustarPincel()
    {
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeJoin = Paint.Join.ROUND
        mPaint.strokeWidth = 4F
    }

    fun startTouch(x:Float, y:Float)
    {
        mPath.moveTo(x, y)
        mX = x
        mY = y
    }


    fun moveTouch(x:Float, y:Float)
    {
        var dx = Math.abs(x - mX)
        var dy = Math.abs(y - mY)

        if (dx >= TOLERANCE || dy >= TOLERANCE)
        {
            mPath.quadTo(mX, mY,  (x+ mX) /2, (y + mY) /2 )
            mX = x
            mY = y
        }
    }

    fun clearCanvas()
    {
        mPath.reset()
        invalidate()
    }

    fun upTouch()
    {
        mPath.lineTo(mX, mY)
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

        canvas?.drawPath(mPath, mPaint)
    }


}