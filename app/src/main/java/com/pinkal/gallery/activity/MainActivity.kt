package com.pinkal.gallery.activity

import android.content.ComponentName
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.Window
import android.view.WindowManager
import com.pinkal.gallery.R
import com.pinkal.gallery.fragment.CameraRollFragment
import com.pinkal.gallery.fragment.FirstFragment
import com.pinkal.gallery.fragment.ThirdFragment
import com.pinkal.gallery.utils.Permissions
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.alert
import org.jetbrains.anko.toast


class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private val mActivity = this@MainActivity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_main)

        toolbar.setNavigationIcon(R.mipmap.ic_launcher_icon)
        setSupportActionBar(toolbar)

        if (Permissions.instance.isReadStoragePermissionGranted(this)) {
            initialize()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            initialize()
        } else {
            alert("Please allow permission to Gallery to load images and video.", "Allow Permission!") {
                positiveButton("ALLOW", {
                    Permissions.instance.isReadStoragePermissionGranted(this@MainActivity)
                })
            }.show().setCancelable(false)
        }
    }

    private fun initialize() {

        setupViewPager(viewpager)
        tabs.setupWithViewPager(viewpager)
        viewpager.currentItem = 0
    }

    private fun setupViewPager(viewPager: ViewPager) {
        val adapter = ViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(FirstFragment(), "Contacts")
        adapter.addFragment(CameraRollFragment(), "Camera Roll")
        adapter.addFragment(ThirdFragment(), "Memo")
        viewPager.adapter = adapter
        viewPager.offscreenPageLimit = 2
    }

    internal inner class ViewPagerAdapter(manager: FragmentManager) : FragmentStatePagerAdapter(manager) {
        private val mFragmentList = ArrayList<Fragment>()
        private val mFragmentTitleList = ArrayList<String>()

        override fun getItem(position: Int): Fragment {
            return mFragmentList[position]
        }

        override fun getCount(): Int {
            return mFragmentList.size
        }

        fun addFragment(fragment: Fragment, title: String) {
            mFragmentList.add(fragment)
            mFragmentTitleList.add(title)
        }

        override fun getPageTitle(position: Int): CharSequence {
            return mFragmentTitleList[position]
        }
    }


    override fun onBackPressed() {

        super.onBackPressed()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {
            R.id.action_settings -> {
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}
