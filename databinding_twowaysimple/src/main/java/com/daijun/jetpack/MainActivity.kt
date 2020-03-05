package com.daijun.jetpack

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.edit
import androidx.databinding.DataBindingUtil
import androidx.databinding.Observable
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModelProvider
import com.daijun.jetpack.data.IntervalTimerViewModel
import com.daijun.jetpack.data.IntervalTimerViewModelFactory
import com.daijun.jetpack.databinding.twowaysimple.BR
import com.daijun.jetpack.databinding.twowaysimple.R
import com.daijun.jetpack.databinding.twowaysimple.databinding.ActivityMainBinding

private const val SHARED_PREFS_KEY = "timer"

/**
 * copy from https://github.com/android/databinding-samples
 */
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding =
            DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)
        val viewmodel = ViewModelProvider(
            this,
            IntervalTimerViewModelFactory()
        ).get(IntervalTimerViewModel::class.java)
        binding.viewmodel = viewmodel
        observeAndSaveTimePerSet(viewmodel.timePerWorkSet, R.string.prefs_timePerWorkSet)
        observeAndSaveTimePerSet(viewmodel.timePerRestSet, R.string.prefs_timePerRestSet)

        // Number of sets needs a different
        observeAndSaveNumberOfSets(viewmodel)

        if (savedInstanceState == null) {
            // If this is the first run, restore shared settings
            restorePreferences(viewmodel)
            observeAndSaveNumberOfSets(viewmodel)
        }
    }

    private fun restorePreferences(viewmodel: IntervalTimerViewModel) {
        val sharedPref =
            getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE) ?: return
        val timePerWorkSetKey = getString(R.string.prefs_timePerWorkSet)
        var wasAnythingRestored = false
        if (sharedPref.contains(timePerWorkSetKey)) {
            viewmodel.timePerWorkSet.set(sharedPref.getInt(timePerWorkSetKey, 100))
            wasAnythingRestored = true
        }
        val timePerRestSetKey = getString(R.string.prefs_timePerRestSet)
        if (sharedPref.contains(timePerRestSetKey)) {
            viewmodel.timePerRestSet.set(sharedPref.getInt(timePerRestSetKey, 50))
            wasAnythingRestored = true
        }
        val numberOfSetsKey = getString(R.string.prefs_numberOfSets)
        if (sharedPref.contains(numberOfSetsKey)) {
            viewmodel.numberOfSets = arrayOf(0, sharedPref.getInt(numberOfSetsKey, 5))
            wasAnythingRestored = true
        }
        if (wasAnythingRestored) {
            println("Preferences restored")
        }
        viewmodel.stopButtonClicked()
    }

    private fun observeAndSaveNumberOfSets(viewmodel: IntervalTimerViewModel) {
        viewmodel.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                if (propertyId == BR.numberOfSets) {
                    val sharedPref =
                        getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE) ?: return
                    sharedPref.edit().apply {
                        putInt(getString(R.string.prefs_numberOfSets), viewmodel.numberOfSets[1])
                        apply()
                    }
                }
            }
        })
    }

    private fun observeAndSaveTimePerSet(timePerSet: ObservableInt, prefsKey: Int) {
        timePerSet.addOnPropertyChangedCallback(object : Observable.OnPropertyChangedCallback() {
            override fun onPropertyChanged(sender: Observable?, propertyId: Int) {
                val sharedPref =
                    getSharedPreferences(SHARED_PREFS_KEY, Context.MODE_PRIVATE) ?: return
                sharedPref.edit().apply {
                    putInt(getString(prefsKey), (sender as ObservableInt).get())
                    apply()
                }
            }
        })
    }
}
