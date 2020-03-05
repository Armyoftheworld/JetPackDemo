package com.army.jetpack.workmanager

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeout

/**
 * @author daijun
 * @date 2020/2/28
 * @description
 */
class TitleRepository(private val network: MainNetwork, private val titleDao: TitleDao) {

    val title: LiveData<String?> = titleDao.titleLiveData.map { it?.title }

    suspend fun refreshTitle() {
        try {
            val result = withTimeout(5_000L) {
                network.fetchNextTitle()
            }
            titleDao.insertTitle(Title(result))
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * This API is exposed for callers from the Java Programming language.
     * The request will run unstructured, which means it won't be able to be cancelled.
     */
    fun refreshTitleinterop(titleRefreshCallback: TitleRefreshCallback) {
        val scope = CoroutineScope(Dispatchers.Default)
        scope.launch {
            try {
                refreshTitle()
                titleRefreshCallback.onCompleted()
            } catch (e: Exception) {
                titleRefreshCallback.onError(e)
            }
        }
    }
}

interface TitleRefreshCallback {
    fun onCompleted()
    fun onError(cause: Throwable)
}