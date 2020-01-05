package com.nonofce.android.mlbteams.ui.component

import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.databinding.BindingAdapter
import com.nonofce.android.mlbteams.R
import kotlinx.android.synthetic.main.error_screen.view.*

class ErrorScreen @JvmOverloads constructor(
    context: Context,
    attr: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayout(context, attr, defStyleAttr) {

    init {

        inflate(context, R.layout.error_screen, this)
        val attributes = context.obtainStyledAttributes(attr, R.styleable.ErrorScreen)
        error_text.text = attributes.getString(R.styleable.ErrorScreen_errorText)
        attributes.recycle()
    }
}

@BindingAdapter("retryAction")
fun ErrorScreen.retryAction(listener: () -> Unit) {
    retry_action.setOnClickListener {
        listener()
    }
}

@BindingAdapter("errorString")
fun ErrorScreen.errorText(stringId: Int) {
    error_text.text = if (stringId != 0) context.resources.getString(stringId) else ""
}


