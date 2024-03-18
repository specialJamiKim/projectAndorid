/*
package com.example.projectandroid

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import com.example.projectandroid.databinding.FragmentSignBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.AuthCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SignFragment : Fragment() {

    private lateinit var binding: FragmentSignBinding
    private val TAG = this.javaClass.simpleName

    private lateinit var firebaseAuth: FirebaseAuth

    private var email: String = ""
    private var tokenId: String? = null


    //실제 로그인 수행로직
    private val launcher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        Log.e(TAG, "resultCode : ${result.resultCode}")
        Log.e(TAG, "result : $result")
        if (result.resultCode == Activity.RESULT_OK) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            try {
                task.getResult(ApiException::class.java)?.let { account ->
                    tokenId = account.idToken
                    if (tokenId != null && tokenId != "") {
                        val credential: AuthCredential = GoogleAuthProvider.getCredential(account.idToken, null)
                        firebaseAuth.signInWithCredential(credential)
                            .addOnCompleteListener(requireActivity()) { signInTask ->
                                if (signInTask.isSuccessful) {
                                    val user: FirebaseUser? = firebaseAuth.currentUser
                                    if (user != null) {
                                        email = user.email.toString()
                                        Log.e(TAG, "email : $email")
                                        val googleSignInToken = account.idToken ?: ""
                                        if (googleSignInToken.isNotEmpty()) {
                                            Log.e(TAG, "googleSignInToken : $googleSignInToken")
                                        } else {
                                            Log.e(TAG, "googleSignInToken이 null")
                                        }
                                    }
                                } else {
                                    Log.e(TAG, "Google sign-in failed: ${signInTask.exception}")
                                }
                            }
                    }
                } ?: throw Exception()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (result.resultCode == Activity.RESULT_CANCELED) {
            // 사용자가 Google 로그인 프로세스를 취소한 경우 처리
            Log.e(TAG, "Google sign-in cancelled by user.")
        } else {
            Log.e(TAG, "Google sign-in failed: resultCode is not RESULT_OK")
        }
    }

    //뷰에서 구글 클릭 작동
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSignBinding.inflate(inflater, container, false)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.run {
            signInBtn.setOnClickListener {
                CoroutineScope(Dispatchers.IO).launch {
                    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                        .requestIdToken(getString(R.string.google_login_client_id))
                        .requestEmail()
                        .build()
                    val googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
                    val signInIntent: Intent = googleSignInClient.signInIntent
                    launcher.launch(signInIntent)
                }
            }
        }

        return binding.root
    }

    // 구글 로그인이 성공한 경우 호출되는 메소드
    private fun handleGoogleSignInSuccess(account: GoogleSignInAccount) {
        val userId = account.id ?: ""
        val userName = account.displayName ?: ""

        var name = findViewById<TextView>(R.id.userName)
        var userid = findViewById<TextView>(R.id.userId)

        // 사용자 이름과 이메일을 텍스트뷰에 설정
        name.text = "User Name: $userName"
        userid.text = "User Id:  ID: $userId"
    }
}
*/
