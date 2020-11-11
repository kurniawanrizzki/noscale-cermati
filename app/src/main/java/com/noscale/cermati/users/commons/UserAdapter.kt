package com.noscale.cermati.users.commons

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.recyclerview.widget.RecyclerView
import com.noscale.cermati.R
import com.noscale.cermati.data.User
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

/**
 * TODO: Add class header description
 * Created by kurniawanrizzki on 11/11/20.
 */
class UserAdapter(val layout: Int, val data: MutableList<User>, val searchListener: UserAdapter.SearchListener): RecyclerView.Adapter<UserAdapter.UserViewHolder>(), Filterable {
    var filterUsers = ArrayList<User>()

    init {
        filterUsers = data as ArrayList<User>
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val root = inflater.inflate(layout, parent, false)

        return UserViewHolder(root)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = filterUsers.get(position)

        val tvTitle = holder.itemView.findViewById<TextView>(R.id.tv_item_title)
        val ivTitle = holder.itemView.findViewById<AppCompatImageView>(R.id.iv_item_thumbnail);

        Picasso.get()
            .load(user.avatar)
            .placeholder(holder.itemView.resources.getDrawable(R.drawable.ic_no_image))
            .into(ivTitle)

        tvTitle.text = user.name
    }

    override fun getItemCount() = filterUsers.size

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    override fun getFilter(): Filter {
        return object: Filter() {
            override fun performFiltering(p0: CharSequence?): FilterResults {
                val keyword = p0.toString()

                if (keyword.isEmpty()) {
                    filterUsers = data as ArrayList<User>
                } else {
                    val resultList = ArrayList<User>()

                    for (row in data) {
                        if (row.name?.toLowerCase(Locale.ROOT)?.contains(keyword.toLowerCase(Locale.ROOT))!!) {
                            resultList.add(row)
                        }
                    }

                    filterUsers = resultList
                }

                val filterResults = FilterResults()
                filterResults.values = filterUsers

                return filterResults
            }

            override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
                val result: List<User> = p1?.values as List<User>

                if (result.isEmpty()) {
                    searchListener.onEmptyResult()
                    return
                }

                searchListener.onResult()
                notifyDataSetChanged()
            }

        }
    }

    interface SearchListener {
        fun onEmptyResult ()
        fun onResult ()
    }
}