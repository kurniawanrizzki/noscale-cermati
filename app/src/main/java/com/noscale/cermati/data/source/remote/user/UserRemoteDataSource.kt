package com.noscale.cermati.data.source.remote.user

import com.noscale.cermati.data.User
import com.noscale.cermati.data.source.UserDataSource
import com.noscale.cermati.data.source.remote.APIService
import retrofit2.Call
import retrofit2.Callback;
import retrofit2.Response

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
object UserRemoteDataSource: UserDataSource {

    const val PAGE_SIZE = 10

    private var mApi: UserAPI = APIService.mUserApi

    override fun getUsers(lastUserId: Int?, callback: UserDataSource.LoadUsersCallback) {

        mApi.fetchUsers(lastUserId, PAGE_SIZE).enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                var statusCode = response.code()

                if (statusCode == 200) {
                    var users = response.body()
                    callback.onUsersLoaded(users)
                    return
                }

                callback.onDataNotAvailable()
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                callback.onDataNotAvailable()
            }

        })
    }

    override fun findUserByName(keyword: String?, callback: UserDataSource.LoadUserCallback) {
        mApi.findUserByName(keyword).enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                var statusCode = response.code()

                if (statusCode == 200) {
                    var user = response.body()
                    callback.onUsersLoaded(user)
                    return
                }

                callback.onDataNotAvailable()
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                callback.onDataNotAvailable()
            }

        })
    }
}