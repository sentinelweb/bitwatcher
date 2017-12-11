package uk.co.sentinelweb.bitwatcher.activity.main.pages.calculator

import android.graphics.Point
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import uk.co.sentinelweb.bitwatcher.common.ui.AndroidUtils
import java.math.BigDecimal
import java.math.RoundingMode

/**
 * Modifies value in edit text by sliding
 * TODO fit to a better scale (get scale of original then slide by 1/100th of that)
 * TODO call function on up
 * Created by robert on 11/12/2017.
 */
class EditTextValueSliderTouchListener(val editText: EditText, val scale:Int = 5) : View.OnTouchListener {
    var down: Point = Point()
    var originalValue: BigDecimal? = null
    var scaleValue: BigDecimal? = null


    override fun onTouch(view: View?, ev: MotionEvent): Boolean {
        when (ev.action) {
            MotionEvent.ACTION_DOWN -> {
                down = Point(ev.getX().toInt(), ev.getY().toInt())
                originalValue = BigDecimal(editText.text.toString())
                scaleValue = originalValue?.divide(BigDecimal(10000).setScale(6))
            }
            MotionEvent.ACTION_MOVE -> {
                editText.setText(originalValue?.add(BigDecimal.valueOf((down.y - ev.getY().toDouble()  )).multiply(scaleValue).setScale(scale, RoundingMode.HALF_EVEN)).toString())
            }
            MotionEvent.ACTION_UP -> {
                if (ev.getY() - down.y < 5 && ev.getX() - down.x < 5) {
                    view?.performClick()
                    AndroidUtils.showSoftKeyboard(editText)
                }
            }
        }
        return false
    }
}
