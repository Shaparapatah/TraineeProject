package com.example.traineeproject

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import java.lang.Math.*
import java.util.*

class ClockView(context: Context, attrs: AttributeSet) : View(context, attrs) {

    companion object {
        private const val DEFAULT_WIDTH = 200
    }

    private lateinit var mBlackPaint: Paint
    private lateinit var mRedPaint: Paint
    private lateinit var mCBlackPaintTwo: Paint
    private lateinit var mTextPaint: Paint
    private var hour: Int? = null
    private var minute: Int? = null
    private var second: Int? = null
    private val textArray = arrayOf("12", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11")


    init {
        initPaints()
    }

    private fun initPaints() {
        mBlackPaint = Paint()
        with(mBlackPaint) {
            color = Color.BLACK
            strokeWidth = 5f
            isAntiAlias = true
            style = Paint.Style.STROKE
        }
        mCBlackPaintTwo = Paint()
        with(mCBlackPaintTwo) {
            color = Color.BLACK
            isAntiAlias = true
            style = Paint.Style.FILL
        }
        mRedPaint = Paint()
        with(mRedPaint) {
            color = Color.RED
            strokeWidth = 5f
            isAntiAlias = true
        }

        mTextPaint = Paint()
        with(mTextPaint) {
            color = Color.BLACK
            textSize = 30f
            isAntiAlias = true
        }
    }


    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        getCurrentTime()

        drawOuterCircle(canvas)

        drawScale(canvas)

        drawTimeText(canvas)

        drawHand(canvas)

        drawCenter(canvas)
    }

    private fun getCurrentTime() {
        val calendar = Calendar.getInstance()
        hour = calendar.get(Calendar.HOUR)
        minute = calendar.get(Calendar.MINUTE)
        second = calendar.get(Calendar.SECOND)
        invalidate()


    }

    private fun drawOuterCircle(canvas: Canvas?) {
        mBlackPaint.strokeWidth = 5f
        canvas?.drawCircle(
            measuredWidth / 2.toFloat(),
            measuredHeight / 2.toFloat(),
            (measuredWidth / 2 - 5).toFloat(),
            mBlackPaint
        )
    }

    private fun drawCenter(canvas: Canvas?) {
        canvas?.drawCircle(
            measuredWidth / 2.toFloat(),
            measuredHeight / 2.toFloat(),
            20f,
            mCBlackPaintTwo
        )
    }

    private fun drawHand(canvas: Canvas?) {
        drawSecond(canvas, mRedPaint)
        mBlackPaint.strokeWidth = 10f
        drawMinute(canvas, mBlackPaint)
        mBlackPaint.strokeWidth = 15f
        drawHour(canvas, mBlackPaint)
    }

    private fun drawTimeText(canvas: Canvas?) {
        val textR = (measuredWidth / 2 - 50)
        for (i in 0..11) {
            val startX =
                (measuredWidth / 2 + textR * kotlin.math.sin(PI / 6 * i) - mTextPaint.measureText(
                    textArray[i]
                ) / 2).toFloat()
            val startY =
                (measuredHeight / 2 - textR * kotlin.math.cos(PI / 6 * i) + mTextPaint.measureText(
                    textArray[i]
                ) / 2).toFloat()
            canvas?.drawText(textArray[i], startX, startY, mTextPaint)
        }
    }

    private fun drawScale(canvas: Canvas?) {
        var scaleLength: Float?
        canvas?.save()
        for (i in 0..59) {
            if (i % 5 == 0) {
                mBlackPaint.strokeWidth = 5f
                scaleLength = 20f
            } else {
                mBlackPaint.strokeWidth = 3f
                scaleLength = 10f
            }
            canvas?.drawLine(
                measuredWidth / 2.toFloat(),
                5f,
                measuredWidth / 2.toFloat(),
                (5 + scaleLength),
                mBlackPaint
            )
            canvas?.rotate(
                360 / 60.toFloat(),
                measuredWidth / 2.toFloat(),
                measuredHeight / 2.toFloat()
            )
        }
        canvas?.restore()
    }

    private fun drawSecond(canvas: Canvas?, paint: Paint) {
        val longR = measuredWidth / 2 - 60
        val shortR = 60
        val startX =
            (measuredWidth / 2 - shortR * sin(second!!.times(PI / 30))).toFloat()
        val startY =
            (measuredWidth / 2 + shortR * kotlin.math.cos(second!!.times(PI / 30))).toFloat()
        val endX = (measuredWidth / 2 + longR * sin(second!!.times(PI / 30))).toFloat()
        val endY = (measuredWidth / 2 - longR * cos(second!!.times(PI / 30))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint)
    }

    private fun drawMinute(canvas: Canvas?, paint: Paint?) {
        val longR = measuredWidth / 2 - 90
        val shortR = 50
        val startX =
            (measuredWidth / 2 - shortR * sin(minute!!.times(PI / 30))).toFloat()
        val startY =
            (measuredWidth / 2 + shortR * cos(minute!!.times(PI / 30))).toFloat()
        val endX = (measuredWidth / 2 + longR * sin(minute!!.times(PI / 30))).toFloat()
        val endY = (measuredWidth / 2 - longR * cos(minute!!.times(PI / 30))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint!!)
    }

    private fun drawHour(canvas: Canvas?, paint: Paint?) {
        val longR = measuredWidth / 2 - 120
        val shortR = 40
        val startX = (measuredWidth / 2 - shortR * sin(hour!!.times(PI / 6))).toFloat()
        val startY = (measuredWidth / 2 + shortR * cos(hour!!.times(PI / 6))).toFloat()
        val endX = (measuredWidth / 2 + longR * sin(hour!!.times(PI / 6))).toFloat()
        val endY = (measuredWidth / 2 - longR * cos(hour!!.times(PI / 6))).toFloat()
        canvas?.drawLine(startX, startY, endX, endY, paint!!)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthSpecMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSpecSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSpecMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSpecSize = MeasureSpec.getSize(heightMeasureSpec)
        val result =
            if (widthSpecMode == MeasureSpec.AT_MOST && heightSpecMode == MeasureSpec.AT_MOST) {
                DEFAULT_WIDTH
            } else {
                kotlin.math.min(widthSpecSize, heightSpecSize)
            }
        setMeasuredDimension(result, result)
    }
}