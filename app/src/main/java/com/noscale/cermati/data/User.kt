package com.noscale.cermati.data

import com.google.gson.annotations.SerializedName

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
data class User (
        @SerializedName("id")
        var id: Int?,
        @SerializedName("login")
        var name: String? = "",
        @SerializedName("avatar_url")
        var avatar: String? = ""
) {
        override fun equals(other: Any?): Boolean {
                if (other is User) {
                        val user: User = other
                        return id == user.id
                }
                return false
        }
}