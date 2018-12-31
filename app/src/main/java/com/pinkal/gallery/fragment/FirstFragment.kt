package com.pinkal.gallery.fragment

import android.database.Cursor
import android.provider.ContactsContract
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


class FirstFragment : Fragment() {

    private var listView: ListView? = null
    private var customAdapter: CustomAdapter? = null
    private var contactModelArrayList: ArrayList<ContactModel>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater!!.inflate(R.layout.fragment_first, container, false)

        return view
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        listView = getView()?.findViewById(R.id.listView) as? ListView

        contactModelArrayList = ArrayList()

        val phones = activity.contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC")
        while (phones!!.moveToNext()) {
            val name = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))

            val contactModel = ContactModel()
            contactModel.setNames(name)
            contactModel.setNumbers(phoneNumber)
            contactModelArrayList!!.add(contactModel)
            Log.d("name>>", name + "  " + phoneNumber)
        }
        phones.close()

        customAdapter = CustomAdapter(activity, contactModelArrayList!!)
        listView?.adapter = customAdapter

    }

}