package com.noscale.cermati.users

import com.noscale.cermati.BasePresenter
import com.noscale.cermati.BaseView
import com.noscale.cermati.data.User

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
interface UsersContract {

    interface View: BaseView<Presenter> {
        fun showProgress (isShowing: Boolean)
        fun showEmptyPage (isEmptyPage: Boolean)
        fun showSearchResult ()
        fun updateView (insertedAt: Int, size: Int)
        fun updateView ();
    }

    interface Presenter: BasePresenter {
        var isDataMissing: Boolean
        var data: MutableList<User>
        var lastUserId: Int

        fun loadUsers ()
        fun reset ()
        fun findUserByName (keyword: String?);
    }
}