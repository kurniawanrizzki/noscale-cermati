package com.noscale.cermati.data.source.remote.user

import com.noscale.cermati.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
interface UserAPI {
    @GET("/users")
    fun fetchUsers (@Query("since") lastUserId: Int?, @Query("per_page") pageSize: Int): Call<List<User>>

    @GET("/users/{keyword}")
    fun findUserByName (@Path("keyword") keyword: String?): Call<User>
}