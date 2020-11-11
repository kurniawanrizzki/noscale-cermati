package com.noscale.cermati.users

import com.noscale.cermati.data.User
import com.noscale.cermati.data.source.UserDataSource
import com.noscale.cermati.data.source.remote.user.UserRemoteDataSource

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
class UsersPresenter (val view: UsersContract.View, val repository: UserRemoteDataSource, override var isDataMissing: Boolean)
    : UsersContract.Presenter {

    override var data: MutableList<User> = ArrayList()

    override var lastUserId: Int = 1

    init {
        view.presenter = this
    }

    override fun loadUsers() {
        repository.getUsers(lastUserId, object: UserDataSource.LoadUsersCallback{
            override fun onUsersLoaded(users: List<User>?) {
                val previousLastIndex = data.size

                for (user in users!!) {
                    if (!data.contains(user)) data.add(user)
                }

                lastUserId = users.get(UserRemoteDataSource.PAGE_SIZE - 1).id!!

                view.updateView(previousLastIndex, users.size)

                view.showProgress(false)
                view.showEmptyPage(data.isEmpty())
            }

            override fun onDataNotAvailable() {
                view.showEmptyPage(true)
                view.showProgress(false)
            }
        })
    }

    override fun reset() {
        view.showProgress(true)

        lastUserId = 1
        loadUsers()
    }

    override fun findUserByName(keyword: String?) {
        repository.findUserByName(keyword, object: UserDataSource.LoadUserCallback {
            override fun onUsersLoaded(user: User?) {
                view.showProgress(false)

                val isResultNotFound = null == user

                if (isResultNotFound) {
                    view.showEmptyPage(isResultNotFound)
                    return
                }

                if (!data.contains(user)) data.add(user!!)

                view.updateView()
                view.showSearchResult()
            }

            override fun onDataNotAvailable() {
                view.showEmptyPage(true)
                view.showProgress(false)
            }

        })
    }

    override fun start() {
        if (!isDataMissing) return
        reset()
    }
}