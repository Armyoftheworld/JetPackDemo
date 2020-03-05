package com.daijun.jetpack.ui

import android.content.Context
import android.inputmethodservice.InputMethodService
import android.os.Build
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.daijun.jetpack.databinding.twowaysimple.R

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/5
 * @description
 */
/**
 * A collection of [BindingAdapter]s for different UI-related tasks.
 *
 * In Kotlin you can write the Binding Adapters in the traditional way:
 *
 * ```
 * @BindingAdapter("property")
 * @JvmStatic fun propertyMethod(view: ViewClass, parameter1: Param1, parameter2: Param2...)
 * ```
 *
 * Or using extension functions:
 *
 * ```
 * @BindingAdapter("property")
 * @JvmStatic fun ViewClass.propertyMethod(parameter1: Param1, parameter2: Param2...)
 * ```
 *
 * See [EditText.clearTextOnFocus].
 *
 * Also, keep in mind that @JvmStatic is only necessary if you define the methods inside a class or
 * object. Consider moving the Binding Adapters to the top level of the file.
 */
object BindingAdapters {

    @[JvmStatic BindingAdapter("clearOnFocusAndDispatch")]
    fun clearOnFocusAndDispatch(view: EditText, listener: View.OnFocusChangeListener?) {
        view.onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            val textView = v as TextView
            if (hasFocus) {
                textView.setTag(R.id.previous_value, textView.text)
                textView.text = ""
            } else {
                if (textView.text.isEmpty()) {
                    val preValue: CharSequence =
                        textView.getTag(R.id.previous_value)?.toString() ?: ""
                    textView.text = preValue
                }
                listener?.onFocusChange(v, hasFocus)
            }
        }
    }

    @[JvmStatic BindingAdapter("clearTextOnFocus")]
    fun EditText.clearTextOnFocus(enable: Boolean) {
        if (enable) {
            clearOnFocusAndDispatch(this, null)
        } else {
            this.onFocusChangeListener = null
        }
    }

    @[JvmStatic BindingAdapter("hideKeyboardOnInputDone")]
    fun hideKeyboardOnInputDone(view: EditText, enable: Boolean) {
        if (!enable) {
            return
        }
        view.setOnEditorActionListener { v, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                view.clearFocus()
                val imm =
                    view.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(v.windowToken, 0)

            }
            false
        }
    }

    /**
     * Makes the View [View.INVISIBLE] unless the condition is met.
     */
    @[JvmStatic BindingAdapter("invisibleUnless") Suppress("unused")]
    fun invisibleUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }

    /**
     * Makes the View [View.GONE] unless the condition is met.
     */
    @[JvmStatic BindingAdapter("goneUnless") Suppress("unused")]
    fun goneUnless(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    /**
     * In [ProgressBar], [ProgressBar.setMax] must be called before [ProgressBar.setProgress].
     * By grouping both attributes in a BindingAdapter we can make sure the order is met.
     *
     * Also, this showcases how to deal with multiple API levels.
     */
    @[JvmStatic BindingAdapter(value = ["android:max", "android:progress"], requireAll = true)]
    fun updateProgress(progressBar: ProgressBar, max: Int, progress: Int) {
        progressBar.max = max
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressBar.setProgress(progress, false)
        } else {
            progressBar.progress = progress
        }
    }

    @[JvmStatic BindingAdapter("loseFocusWhen")]
    fun loseFocusWhen(view: EditText, condition: Boolean) {
        if (condition) {
            view.clearFocus()
        }
    }

}