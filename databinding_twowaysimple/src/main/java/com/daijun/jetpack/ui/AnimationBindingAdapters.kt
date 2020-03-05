package com.daijun.jetpack.ui

import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.DecelerateInterpolator
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.daijun.jetpack.databinding.twowaysimple.R

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/3
 * @description
 */
object AnimationBindingAdapters {

    private const val VERTICAL_BIAS_ANIMATION_DURATION = 900L
    private const val BG_COLOR_ANIMATION_DURATION = 500L

    /**
     * Controls a background color animation.
     *
     * @param view one of the timers (rest/work)
     * @param timerRunning whether the app timer is running
     * @param activeStage whether the particular timer (rest/work) is active
     */
    @BindingAdapter(value = ["animateBackground", "animateBackgroundStage"], requireAll = true)
    fun animateBackground(view: View, timerRunning: Boolean, activeStage: Boolean) {
        // if the timer is not running, don't animate and set the default color
        if (!timerRunning) {
            view.setBackgroundColor(
                ContextCompat.getColor(
                    view.context,
                    R.color.disabledInputColor
                )
            )
            // this tag prevents a glitch going from reset to started
            view.setTag(R.id.hasBeenAnimated, false)
            return
        }
        // activeStage controls whether this particular timer (work or rest) is active
        if (activeStage) {
            // start Animation
            animateBgColor(view, true)
            // This tag prevents a glitch going from paused to started.
            view.setTag(R.id.hasBeenAnimated, true)
        } else {
            // prevent "end" animation if animation never started
            val hasBeenAnimated = view.getTag(R.id.hasBeenAnimated) as Boolean?
            if (hasBeenAnimated == true) {
                // end animation
                animateBgColor(view, false)
                view.setTag(R.id.hasBeenAnimated, false)
            }
        }
    }

    /**
     * Controls an animation that moves a view up and down
     *
     * @param view one of the timers (rest/work)
     * @param timerRunning whether the app timer is running
     * @param activeStage whether the particular timer (rest/work) is active
     */
    @BindingAdapter(value = ["animateVerticalBias", "animateVerticalBiasStage"])
    fun animateVerticalBias(view: View, timerRunning: Boolean, activeStage: Boolean) {
        // change the vertical bias of the view depending on the current state
        when {
            timerRunning && activeStage -> animateVerticalBias(view, 0.6f)
            timerRunning && !activeStage -> animateVerticalBias(view, 0.4f)
            else -> animateVerticalBias(view, 0.5f)
        }
    }

    private fun animateVerticalBias(view: View, position: Float) {
        val layoutParams = view.layoutParams as ConstraintLayout.LayoutParams
        val animator = ValueAnimator.ofFloat(layoutParams.verticalBias, position)
        animator.addUpdateListener {
            layoutParams.verticalBias = it.animatedValue as Float
            view.requestLayout()
        }
        animator.interpolator = DecelerateInterpolator()
        animator.duration = VERTICAL_BIAS_ANIMATION_DURATION
        animator.start()
    }

    private fun animateBgColor(view: View, tint: Boolean) {
        val colorRes = ContextCompat.getColor(view.context, R.color.colorPrimaryLight)
        val color2Res = ContextCompat.getColor(view.context, R.color.disabledInputColor)
        val animation = if (tint) {
            ObjectAnimator.ofObject(
                view,
                "backgroundColor",
                ArgbEvaluator(),
                color2Res,
                colorRes
            )
        } else {
            ObjectAnimator.ofObject(
                view,
                "backgroundColor",
                ArgbEvaluator(),
                colorRes,
                color2Res
            )
        }
        animation.duration = BG_COLOR_ANIMATION_DURATION
        animation.start()
    }
}