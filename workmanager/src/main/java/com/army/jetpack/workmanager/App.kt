package com.army.jetpack.workmanager

import android.app.Application
import androidx.work.*
import java.util.concurrent.TimeUnit

/**
 * @author daijun
 * @date 2020/2/27
 * @description
 */
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        setupWorkManagerJob()
    }

    private fun setupWorkManagerJob() {
        val configuration = Configuration.Builder()
            .setWorkerFactory(RefreshMainDataWork.Factory())
            .build()
        WorkManager.initialize(this, configuration)

        // 只在充电和wifi下才执行work
        val constraints = Constraints.Builder()
            .setRequiresCharging(true)
            .setRequiredNetworkType(NetworkType.UNMETERED)
            .build()

        // 尝试每天执行一次
        val work =
            PeriodicWorkRequestBuilder<RefreshMainDataWork>(1, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        // 把任务加到WorkManager中，并保持之前同样的任务
        WorkManager.getInstance(this)
            .enqueueUniquePeriodicWork(
                RefreshMainDataWork::class.java.name,
                ExistingPeriodicWorkPolicy.KEEP,
                work
            )
    }

}