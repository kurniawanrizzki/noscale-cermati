package com.noscale.cermati.data.source.remote

import com.noscale.cermati.data.source.remote.user.UserAPI
import com.revinate.guava.util.concurrent.RateLimiter
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
object APIService {

    const val BASE_URL = "https://api.github.com"
    const val REQUEST_CALL_LIMIT: Double = 10.0
    const val REQUEST_INTERVAL_CALL_LIMIT = 1

    var mUserApi: UserAPI

    init {
        var client = OkHttpClient.Builder().addInterceptor(object: Interceptor {
            private val limiter: RateLimiter = RateLimiter.create(REQUEST_CALL_LIMIT)

            override fun intercept(chain: Interceptor.Chain): Response {
                limiter.acquire(REQUEST_INTERVAL_CALL_LIMIT)

                val builder = chain.request().newBuilder()
                builder.header("Authorization", "token bc873339c660eeafcd6beff927056e762e486729")

                return chain.proceed(builder.build())
            }

        }).build()

        var api: Retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()

        mUserApi = api.create(UserAPI::class.java)
    }
}