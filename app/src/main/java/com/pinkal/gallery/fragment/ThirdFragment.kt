package com.pinkal.gallery.fragment

import android.app.Activity
import android.database.Cursor
import android.os.Bundle
import android.util.Log
import android.widget.ListView
import java.util.ArrayList
import com.pinkal.gallery.adapter.CustomAdapter
import com.pinkal.gallery.model.ContactModel
import com.pinkal.gallery.R
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.content.Intent
import android.support.design.widget.FloatingActionButton
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.TextView
import android.widget.Toast
import com.pinkal.gallery.activity.AddActivity
import com.pinkal.gallery.adapter.RcvAdapter
import com.pinkal.gallery.model.Memo

import io.realm.Realm
import io.realm.RealmResults


class ThirdFragment : Fragment() {

    private var textView: TextView? = null
    private var realm: Realm? = null
    private var rcv: RecyclerView? = null
    private var rcvAdapter: RcvAdapter? = null
    private var memo_Main: Memo? = null
    var list: MutableList<Memo> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_third2, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        textView = getView()!!.findViewById(R.id.listView) as? TextView
        rcv = getView()!!.findViewById(R.id.rcvMain) as RecyclerView
        val linearLayoutManager = LinearLayoutManager(activity)
        rcv!!.addItemDecoration(DividerItemDecoration(activity, linearLayoutManager.orientation))
        rcv!!.layoutManager = linearLayoutManager

        Realm.init(activity)
        realm = Realm.getDefaultInstance()

        val realmResults = realm!!.where<Memo>(Memo::class.java)
                .findAllAsync()

        for (memo in realmResults) {
            list.add(Memo(memo.text!!))
            rcvAdapter = RcvAdapter(activity, list)
            rcv!!.adapter = rcvAdapter
        }

        val button = getView()?.findViewById(R.id.floating)
        button?.setOnClickListener {
            val intent = Intent(activity, AddActivity::class.java)
            startActivityForResult(intent, 1)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {

            val title = data.getStringExtra("title")
            val time = data.getStringExtra("time")
            Toast.makeText(activity, "$title,$time", Toast.LENGTH_SHORT).show()

            realm!!.beginTransaction()
            memo_Main = realm!!.createObject(Memo::class.java)
            memo_Main!!.text = title

            realm!!.commitTransaction()

            val realmResults = realm!!.where<Memo>(Memo::class.java)
                    .equalTo("text", title)
                    .findAllAsync()

            list.add(Memo(title))
            rcvAdapter = RcvAdapter(activity, list)
            rcv!!.adapter = rcvAdapter

        }
    }
}
