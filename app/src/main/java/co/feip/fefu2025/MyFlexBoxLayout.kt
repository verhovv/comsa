package co.feip.fefu2025


import android.content.Context
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.isGone
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import kotlin.math.max

class CustomFlexLayout @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ViewGroup(context, attrs, defStyleAttr) {
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthLimit = MeasureSpec.getSize(widthMeasureSpec)
        var currentX = marginLeft + paddingLeft
        var currentY = marginTop + paddingTop
        var maxX = 0
        var maxChildHeight = 0
        var combinedChildState = 0

        for (index in 0 until childCount) {
            val child = getChildAt(index)


            if (child.isGone) continue

            measureChild(child, widthMeasureSpec, heightMeasureSpec)

            if (currentX + child.measuredWidth > widthLimit - marginLeft - marginRight - paddingLeft - paddingRight) {
                maxX = max(maxX, currentX)
                currentX = marginLeft + paddingLeft
                currentY += maxChildHeight
                maxChildHeight = 0
            }


            currentX += child.measuredWidth
            maxChildHeight = max(maxChildHeight, child.measuredHeight)
            combinedChildState = combineMeasuredStates(combinedChildState, child.measuredState)
        }

        maxX = max(maxX, currentX)
        currentY += maxChildHeight + marginBottom + paddingBottom
        maxX = max(maxX, suggestedMinimumWidth)

        currentY = max(currentY, suggestedMinimumHeight)


        setMeasuredDimension(
            resolveSizeAndState(maxX, widthMeasureSpec, combinedChildState),
            resolveSizeAndState(
                currentY,
                heightMeasureSpec,
                combinedChildState shl MEASURED_HEIGHT_STATE_SHIFT
            )
        )
    }


    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        var currentX = marginLeft + paddingLeft
        var currentY = marginTop + paddingTop
        var maxChildHeight = 0

        for (index in 0 until childCount) {
            val child = getChildAt(index)

            if (child.isGone) continue


            if (currentX + child.measuredWidth > measuredWidth - marginLeft - marginRight - paddingLeft - paddingRight) {
                currentX = marginLeft + paddingLeft
                currentY += maxChildHeight
                maxChildHeight = 0


            }


            child.layout(
                currentX, currentY, currentX + child.measuredWidth, currentY + child.measuredHeight
            )
            currentX += child.measuredWidth
            maxChildHeight = max(maxChildHeight, child.measuredHeight)
        }
    }
}