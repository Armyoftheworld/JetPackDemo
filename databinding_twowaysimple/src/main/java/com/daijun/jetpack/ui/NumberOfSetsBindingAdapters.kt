package com.daijun.jetpack.ui

import android.content.Context
import android.widget.EditText
import android.widget.TextView
import androidx.databinding.*
import com.daijun.jetpack.databinding.twowaysimple.R

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/5
 * @description
 */
object NumberOfSetsBindingAdapters {

    /**
     * Needs to be used with [NumberOfSetsConverters.setArrayToString].
     */
    @[BindingAdapter("numberOfSets") JvmStatic]
    fun setNumberOfSets(view: EditText, value: String) {
        view.setText(value)
    }

    /**
     * Called when the [InverseBindingListener] of the `numberOfSetsAttrChanged` binding adapter
     * is notified of a change.
     *
     * Used with the inverse method of [NumberOfSetsConverters.setArrayToString], which is
     * [NumberOfSetsConverters.stringToSetArray].
     */
    @[InverseBindingAdapter(attribute = "numberOfSets") JvmStatic]
    fun getNumberOfSets(view: EditText) = view.text.toString()

    /**
     * That this Binding Adapter is not defined in the XML. "AttrChanged" is a special
     * suffix that lets you manage changes in the value, using two-way Data Binding.
     *
     * Note that setting a [View.OnFocusChangeListener] overrides other listeners that might be set
     * with `android:onFocusChangeListener`. Consider supporting both in the same binding adapter
     * with `requireAll = false`. See [androidx.databinding.adapters.CompoundButtonBindingAdapter]
     * for an example.
     */
    @[JvmStatic BindingAdapter("numberOfSetsAttrChanged")]
    fun setListener(view: EditText, listener: InverseBindingListener?) {
        view.setOnFocusChangeListener { v, hasFocus ->
            val textView = v as TextView
            if (hasFocus) {
                textView.text = ""
            } else {
                listener?.onChange()
            }
        }
    }

    /**
     * This sample showcases the NumberOfSetsConverters below, but note that they could be used also like:
     */
    @[JvmStatic BindingAdapter("numberOfSets_alternative")]
    fun setNumberOfSetsAlternative(view: EditText, value: Array<Int>) {
        view.setText(view.resources.getString(R.string.sets_format, value[0] + 1, value[1]))
    }

    @[JvmStatic InverseBindingAdapter(attribute = "numberOfSets_alternative")]
    fun getNumberOfSetsAlternative(view: EditText): Array<Int> {
        if (view.text.isEmpty()) {
            return arrayOf(0, 0)
        }
        return try {
            arrayOf(0, view.text.toString().toInt())
        } catch (e: Exception) {
            arrayOf(0, 0)
        }
    }
}

object NumberOfSetsConverters {

    /**
     * Used with `numberOfSets` to convert from array to String.
     */
    @[InverseMethod("stringToSetArray") JvmStatic]
    fun setArrayToString(context: Context, value: Array<Int>): String {
        return context.getString(R.string.sets_format, value[0] + 1, value[1])
    }

    /**
     * This is the Inverse Method used in `numberOfSets`, to convert from String to array.
     *
     * Note that Context is passed
     */
    @JvmStatic
    fun stringToSetArray(unused: Context, value: String): Array<Int> {
        if (value.isEmpty()) {
            return arrayOf(0, 0)
        }
        return try {
            arrayOf(0, value.toInt())
        } catch (e: Exception) {
            arrayOf(0, 0)
        }
    }
}