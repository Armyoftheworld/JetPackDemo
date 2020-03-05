package com.daijun.jetpack.data

import android.icu.text.UFormat
import androidx.databinding.Bindable
import androidx.databinding.ObservableInt
import com.daijun.jetpack.databinding.twowaysimple.BR
import com.daijun.jetpack.util.ObservableViewModel
import com.daijun.jetpack.util.Timer
import com.daijun.jetpack.util.cleanSecondsString
import java.util.*
import kotlin.math.round

/**
 * @author Army
 * @version V_1.0.0
 * @date 2020/3/2
 * @description
 */
const val INITIAL_SECONDS_PER_WORK_SET = 5
const val INITIAL_SECONDS_PER_REST_SET = 2
const val INITIAL_NUMBER_OF_SETS = 5

class IntervalTimerViewModel(val timer: Timer) : ObservableViewModel() {
    val timePerWorkSet = ObservableInt(INITIAL_SECONDS_PER_WORK_SET * 10)
    val timePerRestSet = ObservableInt(INITIAL_SECONDS_PER_REST_SET * 10)
    val workTimeLeft = ObservableInt(timePerWorkSet.get())
    val restTimeLeft = ObservableInt(timePerRestSet.get())

    private var state = TimerStates.STOPPED
    private var stage = StartedStages.WORKING

    var timerRunning: Boolean
        @Bindable get() = stage == TimerStates.STARTED
        set(value) {
            if (value) startButtonClick() else pauseButtonClicked()
        }

    private var numberOfSetsTotal = INITIAL_NUMBER_OF_SETS
    private var numberOfSetsElapsed = 0

    var numberOfSets = emptyArray<Int>()
        @Bindable get() = arrayOf(numberOfSetsElapsed, numberOfSetsTotal)
        set(value) {
            // Only the second Int is being set
            val newTotal = value[1]
            if (newTotal == numberOfSets[1]) {
                return
            }
            // Only update if it doesn't affect the current exercise
            if (newTotal != 0 && newTotal > numberOfSetsElapsed) {
                field = value
                numberOfSetsTotal = newTotal
            }
            // Even if the input is empty, force a refresh of the view
            notifyFieldChanged(BR.numberOfSets)
        }

    /**
     * Used to control some animations.
     */
    val inWorkingStage: Boolean
        @Bindable get() = stage == StartedStages.WORKING

    fun restTimeIncrease() = timePerSetIncrease(timePerRestSet, 1)

    fun restTimeDecrease() = timePerSetIncrease(timePerRestSet, -1)

    fun workTimeIncrease() = timePerSetIncrease(timePerWorkSet, 1)

    fun workTimeDecrease() = timePerSetIncrease(timePerWorkSet, -1, 10)

    fun setsIncrease() {
        numberOfSetsTotal += 1
        notifyFieldChanged(BR.numberOfSets)
    }

    fun setsDecrease() {
        if (numberOfSetsTotal > numberOfSetsElapsed + 1) {
            numberOfSetsTotal -= 1
            notifyFieldChanged(BR.numberOfSets)
        }
    }

    /**
     * Resets timers and state. Called from the UI.
     */
    fun stopButtonClicked() {
        resetTimers()
        numberOfSetsElapsed = 0
        state = TimerStates.STOPPED
        stage = StartedStages.WORKING
        timer.reset()

        notifyFieldChanged(BR.timerRunning)
        notifyFieldChanged(BR.inWorkingStage)
        notifyFieldChanged(BR.numberOfSets)
    }

    fun startButtonClicked() {
        when (state) {
            TimerStates.PAUSED -> pausedToStarted()
            TimerStates.STOPPED -> stoppedToStarted()
            TimerStates.STARTED -> {
            }
        }
        timer.start(object : TimerTask() {
            override fun run() {
                if (state == TimerStates.STARTED) {
                    updateCountdowns()
                }
            }
        })
    }




    /**
     * Increases or decreases the work or rest time by a set value, depending on the sign of
     * the parameter.
     * @param timePerSet the value holder to be updated
     * @param sign 1 to increase, -1 to decrease.
     */
    private fun timePerSetIncrease(timePerSet: ObservableInt, sign: Int = 1, min: Int = 0) {
        if (timePerSet.get() < 10 && sign < 0) {
            return
        }
        // Increase the time in chunks
        roundTimeIncrease(timePerSet, sign, min)
        if (state == TimerStates.STOPPED) {
            // If stopped, update the timers right away
            workTimeLeft.set(timePerWorkSet.get())
            restTimeLeft.set(timePerRestSet.get())
        } else {
            // If running or paused, the timers need to be calculated
            updateCountdowns()
        }
    }

    private fun updateCountdowns() {
        if (state == TimerStates.STOPPED) {
            resetTimers()
            return
        }
        val elapsed = if (state == TimerStates.PAUSED) {
            timer.getPausedTime()
        } else {
            timer.getElapsedTime()
        }
        if (stage == StartedStages.RESTING) {
            updateRestCountdowns(elapsed)
        } else {
            updateWorkCountdowns(elapsed)
        }
    }

    private fun updateWorkCountdowns(elapsed: Long) {
        stage = StartedStages.WORKING
        val newTimeLeft = timePerWorkSet.get() - (elapsed / 100).toInt()
        if (newTimeLeft <= 0) {
            workoutFinished()
        }
        workTimeLeft.set(newTimeLeft.coerceAtLeast(0))
    }

    /**
     * WORKING -> RESTING
     */
    private fun workoutFinished() {
        timer.resetStartTime()
        stage = StartedStages.RESTING
        notifyFieldChanged(BR.inWorkingStage)
    }

    private fun updateRestCountdowns(elapsed: Long) {
        // Calculate the countdown time with the start time
        val newRestTimeLeft = timePerRestSet.get() - (elapsed / 100).toInt()
        restTimeLeft.set(newRestTimeLeft.coerceAtLeast(0))
        if (newRestTimeLeft <= 0) {
            numberOfSetsElapsed += 1
            resetTimers()
            if (numberOfSetsElapsed >= numberOfSetsTotal) {
                timerFinished()
            } else {
                setFinished()
            }
        }
    }

    /**
     * RESTING -> STOPPED
     */
    private fun timerFinished() {
        state = TimerStates.STOPPED
        stage = StartedStages.WORKING
        timer.reset()
        notifyFieldChanged(BR.timerRunning)
        numberOfSetsElapsed = 0

        notifyFieldChanged(BR.inWorkingStage)
        notifyFieldChanged(BR.numberOfSets)
    }

    /**
     * RESTING -> WORKING
     */
    private fun setFinished() {
        timer.resetStartTime()
        stage = StartedStages.WORKING

        notifyFieldChanged(BR.inWorkingStage)
        notifyFieldChanged(BR.numberOfSets)
    }

    private fun resetTimers() {
        // Reset counters
        workTimeLeft.set(timePerWorkSet.get())
        // Set the start time
        restTimeLeft.set(timePerRestSet.get())
    }

    /**
     * Make increasing and decreasing times a bit nicer by adding chunks.
     */
    private fun roundTimeIncrease(timePerSet: ObservableInt, sign: Int, min: Int) {
        val currentValue = timePerSet.get()
        val newValue = when {
            // 小于10s，每一秒增长
            currentValue < 100 -> currentValue + sign * 10
            // 小于60s，每五秒增长
            currentValue < 600 -> (round(currentValue / 50.0) * 50 + 50 * sign).toInt()
            // 大于60s，每十秒增长
            else -> (round(currentValue / 100.0) * 100 + 100 * sign).toInt()
        }
        timePerSet.set(newValue.coerceAtLeast(min))
    }

    /**
     * Called from the UI, parses a new user-entered value.
     */
    fun timePerRestSetChanged(newValue: CharSequence) {
        try {
            timePerRestSet.set(cleanSecondsString(newValue.toString()))
        } catch (e: Exception) {
            return
        }
        if (!isRestTimeAndRunning()) {
            restTimeLeft.set(timePerRestSet.get())
        }
    }

    fun timePerWorkSetChanged(newValue: CharSequence) {
        try {
            timePerWorkSet.set(cleanSecondsString(newValue.toString()))
        } catch (e: Exception) {
            return
        }
        if (!timerRunning) {
            workTimeLeft.set(timePerWorkSet.get())
        }
    }

    /**
     * True if the work time has passed and we're currently resting.
     */
    private fun isRestTimeAndRunning() =
        (state == TimerStates.PAUSED || state == TimerStates.STARTED)
                && workTimeLeft.get() == 0

    private fun startButtonClick() {
        when (state) {
            TimerStates.PAUSED -> pausedToStarted()
            TimerStates.STOPPED -> stoppedToStarted()
            TimerStates.STARTED -> {
            }
        }
    }

    private fun pauseButtonClicked() {
        if (state == TimerStates.STARTED) {
            startedToPaused()
        }
        notifyFieldChanged(BR.timerRunning)
    }

    private fun startedToPaused() {
        state = TimerStates.PAUSED
        timer.resetPauseTime()
    }

    private fun pausedToStarted() {
        timer.updatePausedTime()
        state = TimerStates.STARTED
        notifyFieldChanged(BR.inWorkingStage)
    }

    private fun stoppedToStarted() {
        timer.resetStartTime()
        state = TimerStates.STARTED
        stage = StartedStages.WORKING

        notifyFieldChanged(BR.inWorkingStage)
        notifyFieldChanged(BR.timerRunning)
    }
}

/**
 * Extensions to allow += and -= on an ObservableInt.
 */
private operator fun ObservableInt.plusAssign(value: Int) {
    set(get() + value)
}

private operator fun ObservableInt.minusAssign(value: Int) {
    plusAssign(-value)
}

enum class TimerStates { STOPPED, STARTED, PAUSED }
enum class StartedStages { WORKING, RESTING }