// NaviActivity.kt
package com.example.projectandroid.view

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.projectandroid.R
import com.example.projectandroid.databinding.ActivityNaviBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser

private const val TAG_CALENDAR = "calendar_fragment"
private const val TAG_HOME = "home_fragment"
private const val TAG_MY_PAGE = "my_page_fragment"


class NaviActivity : AppCompatActivity() {

    //

    private lateinit var binding: ActivityNaviBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNaviBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        //파이어 베이스 사용부분

        // 사용자가 로그인되어 있는지 확인
        val currentUser = firebaseAuth.currentUser
        if (currentUser == null) {
            // 로그인되어 있지 않다면 로그인 페이지로 이동
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        } else {
            // 로그인되어 있다면 사용자 정보를 업데이트 => 여기서 로그인 된 사용자 정보 프레그먼트에 뿌려줌
            handleGoogleSignInSuccess(currentUser)
        }

        // Fragment 설정
        setFragment(TAG_HOME, HomeFragment())

        binding.navigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.calenderFragment -> setFragment(TAG_CALENDAR, CalenderFragment())
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

        val calendar = manager.findFragmentByTag(TAG_CALENDAR)
        val home = manager.findFragmentByTag(TAG_HOME)
        val myPage = manager.findFragmentByTag(TAG_MY_PAGE)

        if (calendar != null) {
            fragTransaction.hide(calendar)
        }

        if (home != null) {
            fragTransaction.hide(home)
        }

        if (myPage != null) {
            fragTransaction.hide(myPage)
        }

        if (tag == TAG_CALENDAR) {
            if (calendar != null) {
                fragTransaction.show(calendar)
            }
        } else if (tag == TAG_HOME) {
            if (home != null) {
                fragTransaction.show(home)
            }
        } else if (tag == TAG_MY_PAGE) {
            if (myPage != null) {
                fragTransaction.show(myPage)
            }
        }

        fragTransaction.commitAllowingStateLoss()
    }

    // 사용자가 로그인한 경우 사용자 정보를 업데이트
    private fun handleGoogleSignInSuccess(user: FirebaseUser) {
        Log.d(">>",  "사용자 정보 업데이트!!")
/*        val name = user.displayName ?: ""
        val email = user.email ?: ""
        Log.d(">>","$name / $email")
        // 사용자 이름과 이메일을 업데이트
       // binding.= "User Name: $name"
       binding.userEmail.text = "Email: $email"*/
    }
}
