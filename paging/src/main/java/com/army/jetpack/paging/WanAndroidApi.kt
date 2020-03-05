package com.army.jetpack.paging

import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @author daijun
 * @date 2020/2/24
 * @description
 */
interface WanAndroidApi {

    @GET("article/list/{currentPage}/json")
    suspend fun getArticles(@Path("currentPage") page: Int): WanAndroidBean
}