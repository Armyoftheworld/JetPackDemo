package com.army.jetpack.workmanager

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val dataBase = getDatabase(this)
        val titleRepository = TitleRepository(getNetworkService(), dataBase.titleDao)
        val viewModel =
            ViewModelProvider(this, MainViewModel.FACTORY(titleRepository))
                .get(MainViewModel::class.java)

        tv_title.setOnClickListener {
            viewModel.onMainViewClicked()
        }

        viewModel.title.observe(this, Observer { value ->
            value?.let {
                tv_title.text = it
            }
        })

        viewModel.taps.observe(this, Observer {
            tv_taps.text = it
        })

        viewModel.spinner.observe(this, Observer {
            pb_spinner.visibility = if (it) View.VISIBLE else View.GONE
        })

        viewModel.snackBar.observe(this, Observer { text ->
            text?.let {
                Snackbar.make(constraint, it, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })
    }
}
