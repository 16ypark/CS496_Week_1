package com.pinkal.gallery.adapter

import android.app.Activity
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.pinkal.gallery.model.Memo
import com.pinkal.gallery.R

import io.realm.Realm
import io.realm.RealmResults


class RcvAdapter(private val activity: Activity, private val dataList: MutableList<Memo>) : RecyclerView.Adapter<RcvAdapter.ViewHolder>() {
    private var realm: Realm? = null

    override fun getItemCount(): Int {
        return dataList.size
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView

        init {
            tvName = itemView.findViewById(R.id.mContextTextView) as TextView

            itemView.setOnLongClickListener {
                removeMemo(dataList[adapterPosition].text)
                removeItemView(adapterPosition)
                false
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = dataList[position]
        holder.tvName.text = data.text
    }

    private fun removeItemView(position: Int) {
        dataList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, dataList.size)
    }

    // 데이터 삭제
    private fun removeMemo(text: String?) {
        realm = Realm.getDefaultInstance()
        val results = realm!!.where<Memo>(Memo::class.java).equalTo("text", text).findAll()

        realm!!.executeTransaction { results.deleteFromRealm(0) }
    }
}