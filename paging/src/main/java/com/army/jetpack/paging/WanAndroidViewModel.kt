package com.army.jetpack.paging

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PageKeyedDataSource
import androidx.paging.PagedList
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author daijun
 * @date 2020/2/24
 * @description
 */
class WanAndroidViewModel : ViewModel() {
    private val wanAndroidApi: WanAndroidApi by lazy {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor("HttpRequest"))
            .build()
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://www.wanandroid.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        retrofit.create(WanAndroidApi::class.java)
    }

    lateinit var dataSource: ArticleDataSource

    private val mFactory =
        object : DataSource.Factory<Int, WanAndroidBean.DataBean.DatasBean>() {
            override fun create(): DataSource<Int, WanAndroidBean.DataBean.DatasBean> {
                if (!::dataSource.isInitialized || dataSource.isInvalid) {
                    dataSource = ArticleDataSource()
                }
                return dataSource
            }
        }

    val boundaryPageData = MutableLiveData<Boolean>()

    private val boundaryCallback =
        object : PagedList.BoundaryCallback<WanAndroidBean.DataBean.DatasBean>() {
            override fun onZeroItemsLoaded() {
                super.onZeroItemsLoaded()
                // 初始化数据
                boundaryPageData.postValue(false)
            }

            override fun onItemAtEndLoaded(itemAtEnd: WanAndroidBean.DataBean.DatasBean) {
                super.onItemAtEndLoaded(itemAtEnd)
                // 没有数据加载了
                boundaryPageData.postValue(false)
            }

            override fun onItemAtFrontLoaded(itemAtFront: WanAndroidBean.DataBean.DatasBean) {
                super.onItemAtFrontLoaded(itemAtFront)
                // 正在添加数据
                boundaryPageData.postValue(true)
            }
        }

    val articleRes: LiveData<PagedList<WanAndroidBean.DataBean.DatasBean>> by lazy {
        val config = PagedList.Config.Builder()
            .setPageSize(20)
            .setInitialLoadSizeHint(20)
            .build()
        LivePagedListBuilder<Int, WanAndroidBean.DataBean.DatasBean>(mFactory, config)
            .setBoundaryCallback(boundaryCallback)
            .build()
    }

    fun getArticles(
        page: Int,
        initialCallback: PageKeyedDataSource.LoadInitialCallback<Int, WanAndroidBean.DataBean.DatasBean>?,
        callback: PageKeyedDataSource.LoadCallback<Int, WanAndroidBean.DataBean.DatasBean>?
    ) {
        viewModelScope.launch {
            try {
                // 这里必须要捕获异常，因为假如请求失败的话，是会抛异常的
                val wanAndroidBean = wanAndroidApi.getArticles(page)
                initialCallback?.onResult(wanAndroidBean.data.datas, -1, 0)
                callback?.onResult(wanAndroidBean.data.datas, page)
                boundaryPageData.value = wanAndroidBean.data.datas.isEmpty()
            } catch (e: Exception) {
                // e is HttpException
                e.printStackTrace()
            }
        }
    }

    inner class ArticleDataSource : PageKeyedDataSource<Int, WanAndroidBean.DataBean.DatasBean>() {
        override fun loadInitial(
            params: LoadInitialParams<Int>,
            callback: LoadInitialCallback<Int, WanAndroidBean.DataBean.DatasBean>
        ) {
            // 开始加载数据
            getArticles(0, callback, null)
        }

        override fun loadAfter(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, WanAndroidBean.DataBean.DatasBean>
        ) {
            // 往后加载数据
            getArticles(params.key + 1, null, callback)
        }

        override fun loadBefore(
            params: LoadParams<Int>,
            callback: LoadCallback<Int, WanAndroidBean.DataBean.DatasBean>
        ) {
            // 往前加载数据
        }

    }
}