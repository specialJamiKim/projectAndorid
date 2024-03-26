package com.example.projectandroid.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.projectandroid.R
import com.example.projectandroid.databinding.ActivityNaviBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNaviBinding

    private companion object {
        const val TAG_HOME = "home_fragment"
        const val TAG_MY_PAGE = "my_page_fragment"
        const val TAG_MAP = "map_fragment"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Fragment 설정
        setFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.mapFragment -> setFragment(TAG_MAP, MapFragment())
                R.id.homeFragment -> setFragment(TAG_HOME, HomeFragment())
                R.id.myPageFragment -> setFragment(TAG_MY_PAGE, MyPageFragment())
            }
            true
        }
    }

    private fun setFragment(tag: String, fragment: Fragment) {
        val manager: FragmentManager = supportFragmentManager
        val fragTransaction = manager.beginTransaction()

        if (manager.findFragmentByTag(tag) == null) {
            fragTransaction.add(R.id.mainFrameLayout, fragment, tag)
        }

        val home = manager.findFragmentByTag(TAG_HOME)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)
        val mapPage = manager.findFragmentByTag(TAG_MAP)

        if (home != null) {
            fragTransaction.hide(home)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (mapPage != null) {
            fragTransaction.hide(mapPage)
        }

        when (tag) {
            TAG_HOME -> if (home != null) fragTransaction.show(home)
            TAG_MY_PAGE -> if (myPage != null) fragTransaction.show(myPage)
            TAG_MAP -> if (mapPage != null) fragTransaction.show(mapPage)
        }

        fragTransaction.commitAllowingStateLoss()
    }
}
