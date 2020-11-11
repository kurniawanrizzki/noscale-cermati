package com.noscale.cermati.data.source

import com.noscale.cermati.data.User

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
interface UserDataSource {
    interface LoadUsersCallback {
        fun onUsersLoaded (users: List<User>?)
        fun onDataNotAvailable ()
    }

    interface LoadUserCallback {
        fun onUsersLoaded (user: User?)
        fun onDataNotAvailable ()
    }

    fun getUsers (lastUserId: Int?, callback: LoadUsersCallback)

    fun findUserByName (keyword: String?, callback: LoadUserCallback)
}