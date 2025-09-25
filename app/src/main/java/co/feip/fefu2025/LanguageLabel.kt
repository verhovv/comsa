package co.feip.fefu2025

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.LinearLayout
import co.feip.fefu2025.databinding.LanguageLabelBinding

class LanguageTag @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0, defStyleRes: Int = 0
) : LinearLayout(context, attrs, defStyleAttr, defStyleRes) {

    private val viewBinding: LanguageLabelBinding =
        LanguageLabelBinding.inflate(LayoutInflater.from(context), this, true)

    private var _languageName: String = ""
    var languageName: String
        get() = viewBinding.name.text.toString()
        set(value) {
            _languageName = value
            viewBinding.name.text = _languageName
        }

    private var _tagColor: Int = 0
    var tagColor: Int
        get() = _tagColor
        set(value) {
            _tagColor = value
            viewBinding.circle.drawable.mutate().setTint(_tagColor)
        }

    private var _usagePercentage: Float = 0.0f
    var usagePercentage: Float
        get() = _usagePercentage
        set(value) {
            _usagePercentage = value
            updatePercentageText()
        }

    init {
        initializeAttributes(attrs, defStyleAttr, defStyleRes)
    }

    private fun initializeAttributes(attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) {
        val attributes = context.theme.obtainStyledAttributes(
            attrs, R.styleable.LanguageLabel, defStyleAttr, defStyleRes
        )
        try {
            languageName = attributes.getString(R.styleable.LanguageLabel_name) ?: ""
            usagePercentage = attributes.getFloat(R.styleable.LanguageLabel_percentage, 0.0f)
            tagColor = attributes.getColor(R.styleable.LanguageLabel_color, 0)
        } finally {
            attributes.recycle()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePercentageText() {
        viewBinding.percentage.text = "${"%.1f".format(_usagePercentage)}%"
    }
}