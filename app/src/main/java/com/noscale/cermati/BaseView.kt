package com.noscale.cermati

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
interface BaseView<T : BasePresenter> {
    var presenter: T
}