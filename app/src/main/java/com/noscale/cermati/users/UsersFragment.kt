package com.noscale.cermati.users

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.airbnb.lottie.LottieAnimationView
import com.noscale.cermati.R
import com.noscale.cermati.users.commons.UserAdapter
import java.util.*

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
class UsersFragment : Fragment(), UsersContract.View, SwipeRefreshLayout.OnRefreshListener {
    override lateinit var presenter: UsersContract.Presenter

    private var mAdapter: UserAdapter? = null

    private var rvUser: RecyclerView? = null

    private var etSearchUser: EditText? = null

    private var srlContainer: SwipeRefreshLayout? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_users, container, false)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        rvUser = getView()?.findViewById(R.id.rv_user_list)
        etSearchUser = getView()?.findViewById(R.id.et_user_filter)
        srlContainer = getView()?.findViewById(R.id.srl_user_container)

        mAdapter = UserAdapter(R.layout.item_user, presenter.data,getOnSearchListener())
        rvUser?.adapter = mAdapter

        rvUser?.addOnScrollListener(getOnScrollListener())
        etSearchUser?.addTextChangedListener(getOnTextChangedListener())
        srlContainer?.setOnRefreshListener(this)
    }

    override fun onResume() {
        super.onResume()
        presenter.start()
    }

    override fun onRefresh() {
        srlContainer?.isRefreshing = false
        presenter.reset()
    }

    override fun showProgress(isShowing: Boolean) {
        etSearchUser?.isEnabled = !isShowing

        val progressView: LottieAnimationView? = view?.findViewById(R.id.lav_user_progress)
        val containerView: View? = view?.findViewById(R.id.srl_user_container)

        if (isShowing) {
            progressView?.visibility = View.VISIBLE
            containerView?.visibility = View.GONE
            return
        }

        progressView?.visibility = View.GONE
        containerView?.visibility = View.VISIBLE
    }

    override fun showEmptyPage(isEmptyPage: Boolean) {
        val notFoundView: LottieAnimationView? = view?.findViewById(R.id.lav_user_not_found)

        if (isEmptyPage) {
            notFoundView?.visibility = View.VISIBLE
            rvUser?.visibility = View.GONE
            return
        }

        notFoundView?.visibility = View.GONE
        rvUser?.visibility = View.VISIBLE
    }

    override fun showSearchResult() {
        val keyword: String = etSearchUser?.text.toString()

        mAdapter?.filter?.filter(keyword)
        etSearchUser?.isEnabled = true
    }

    override fun updateView(insertedAt: Int, size: Int) {
        mAdapter?.notifyItemRangeInserted(insertedAt, size)
    }

    override fun updateView() {
        mAdapter?.notifyDataSetChanged()
    }

    private fun getOnScrollListener(): RecyclerView.OnScrollListener {
        return object : RecyclerView.OnScrollListener() {
            private var mPreviousTotal = 0
            private var mLoading = true
            private val visibleThreshold = 10

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val visibleItemCount = recyclerView.childCount
                val totalItemCount = recyclerView.layoutManager!!.itemCount

                val firstVisibleItem = (recyclerView.layoutManager as LinearLayoutManager?)!!.findFirstVisibleItemPosition()
                if (mLoading) {
                    if (totalItemCount > mPreviousTotal) {
                        mLoading = false
                        mPreviousTotal = totalItemCount
                    }
                }
                if (!mLoading && totalItemCount - visibleItemCount <= firstVisibleItem + visibleThreshold) {
                    presenter.loadUsers()
                    mLoading = true
                }
            }
        }
    }

    private fun getOnTextChangedListener(): TextWatcher {
        return object : TextWatcher {
            private var timer: Timer? = null

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                timer?.cancel()
            }

            override fun afterTextChanged(p0: Editable?) {
                timer = Timer()
                timer?.schedule(object: TimerTask() {
                    override fun run() {
                        activity?.runOnUiThread {
                            var keyword = ""
                            if (p0?.length!! >= 3) keyword = p0.toString()

                            mAdapter?.filter?.filter(keyword)
                        }
                    }

                }, 1000)
            }

        }
    }

    private fun getOnSearchListener(): UserAdapter.SearchListener {
        return object : UserAdapter.SearchListener {
            override fun onEmptyResult() {
                val keyword = etSearchUser?.text.toString()

                showProgress(true)
                etSearchUser?.isEnabled = false

                presenter.findUserByName(keyword)
            }

            override fun onResult() {
                showEmptyPage(false)
            }
        }
    }

    companion object {
        fun newInstance() = UsersFragment()
    }
}