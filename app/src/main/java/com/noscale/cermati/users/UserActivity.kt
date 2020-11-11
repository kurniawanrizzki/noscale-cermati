package com.noscale.cermati.users

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.noscale.cermati.R
import com.noscale.cermati.data.source.remote.user.UserRemoteDataSource

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
class UserActivity : AppCompatActivity() {

    private lateinit var mPresenter: UsersPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_users)

        setSupportActionBar(findViewById(R.id.tb_user_toolbar))

        val fragment =
            supportFragmentManager.findFragmentById(R.id.fr_users_container) as UsersFragment?
                ?: UsersFragment.newInstance().also {
                    supportFragmentManager.beginTransaction().apply {
                        replace(R.id.fr_users_container, it)
                    }.commit()
                }

        val shouldLoadDataFromRepoKey = savedInstanceState?.getBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY) ?: true
        mPresenter = UsersPresenter(fragment, UserRemoteDataSource , shouldLoadDataFromRepoKey)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState.apply {
            putBoolean(SHOULD_LOAD_DATA_FROM_REPO_KEY, mPresenter.isDataMissing)
        })
    }

    companion object {
        const val SHOULD_LOAD_DATA_FROM_REPO_KEY = "SHOULD_LOAD_DATA_FROM_REPO_KEY"
    }
}