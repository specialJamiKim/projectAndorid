package com.example.projectandroid.view

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.projectandroid.R
import com.example.projectandroid.databinding.ActivityLoginBinding
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    /* private lateinit var binding: ActivityLoginBinding
     private lateinit var firebaseAuth: FirebaseAuth

     override fun onCreate(savedInstanceState: Bundle?) {
         super.onCreate(savedInstanceState)
         binding = ActivityLoginBinding.inflate(layoutInflater)
         setContentView(binding.root)

         firebaseAuth = FirebaseAuth.getInstance()

         binding.run {
             signInBtn.setOnClickListener {
                 CoroutineScope(Dispatchers.IO).launch {
                     val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                         .requestIdToken(getString(R.string.google_login_client_id))
                         .requestEmail()
                         .build()
                     val googleSignInClient = GoogleSignIn.getClient(this@LoginActivity, gso)
                     val signInIntent = googleSignInClient.signInIntent
                     startActivityForResult(signInIntent, RC_SIGN_IN)
                 }
             }
         }
     }

     override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
         super.onActivityResult(requestCode, resultCode, data)

         if (requestCode == RC_SIGN_IN) {
             val task = GoogleSignIn.getSignedInAccountFromIntent(data)
             try {
                 val account = task.getResult(ApiException::class.java)
                 account?.let {
                     firebaseAuthWithGoogle(it.idToken!!)
                 }
             } catch (e: ApiException) {
                 // Google Sign In failed
                 e.printStackTrace()
             }
         }
     }

     private fun firebaseAuthWithGoogle(idToken: String) {
         val credential = GoogleAuthProvider.getCredential(idToken, null)
         firebaseAuth.signInWithCredential(credential)
             .addOnCompleteListener(this) { task ->
                 if (task.isSuccessful) {
                     // Sign in success, update UI with the signed-in user's information
                     val user = firebaseAuth.currentUser
                     user?.let {
                         // Show toast message
                         Toast.makeText(this, "반갑습니다! ${user.displayName}님", Toast.LENGTH_SHORT).show()
                         // Go back to MyPageFragment
                         val intent = Intent(this, NaviActivity::class.java)
                         startActivity(intent)
                         finish()
                     }
                 } else {
                     // If sign in fails, display a message to the user.
                     // ...
                 }
             }
     }

     companion object {
         private const val RC_SIGN_IN = 9001
     }*/

    lateinit var mGoogleSingInClient: GoogleSignInClient
    lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onStart() {
        super.onStart()
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let {
            Toast.makeText(this, "Logged In", Toast.LENGTH_SHORT).show()
        } ?: Toast.makeText(this, "Not Yet", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)

        setResultSignUp()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail().requestProfile().build()

        mGoogleSingInClient = GoogleSignIn.getClient(this, gso)

        with(binding) {
            btnSignIn.setOnClickListener { signIn() }
            btnSignOut.setOnClickListener { signOut() }
            btnGetProfile.setOnClickListener { GetCurrentUserProfile() }
        }

        setContentView(binding.root)
    }

    private fun setResultSignUp() {
        resultLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                //정상적으로 결과가 받아와 진다면 조건문 실행
                if (result.resultCode == Activity.RESULT_OK) {
                    val task: Task<GoogleSignInAccount> =
                        GoogleSignIn.getSignedInAccountFromIntent(result.data)
                    handleSignInResult(task)
                }
            }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            val email = account?.email.toString()
            val familyName = account?.familyName.toString()
            val givenName = account?.givenName.toString()
            val displayName = account?.displayName.toString()
            val photoUrl = account?.photoUrl.toString() // 프로필사진 가져오기

            Log.d("로그인한 유저의 이메일", email)
            Log.d("로그인한 유저의 성", familyName)
            Log.d("로그인한 유저의 이름", givenName)
            Log.d("로그인한 유저의 전체이름", displayName)
            Log.d("로그인한 유저의 프로필 사진 주소", photoUrl)
        } catch (e: ApiException) {
            Log.w("failed", "signInResult:failed code = " + e.statusCode)
        }
    }

    //로그인
    private fun signIn() {
        val signInIntent: Intent = mGoogleSingInClient.getSignInIntent()
        resultLauncher.launch(signInIntent)
    }

    //로그아웃
    private fun signOut() {
        mGoogleSingInClient.signOut().addOnCompleteListener(this) {
            //처리내용
        }
    }

    //로그인 세션 만료
    private fun revokeAccess() {
        mGoogleSingInClient.revokeAccess().addOnCompleteListener(this)
        {
            //완료처리
        }
    }

    private fun GetCurrentUserProfile() {
        val curUser = GoogleSignIn.getLastSignedInAccount(this)
        curUser?.let {
            val email = curUser?.email.toString()
            val familyName = curUser?.familyName.toString()
            val givenName = curUser?.givenName.toString()
            val displayName = curUser?.displayName.toString()
            val photoUrl = curUser?.photoUrl.toString() // 프로필사진 가져오기

            Log.d("로그인한 유저의 이메일", email)
            Log.d("로그인한 유저의 성", familyName)
            Log.d("로그인한 유저의 이름", givenName)
            Log.d("로그인한 유저의 전체이름", displayName)
            Log.d("로그인한 유저의 프로필 사진 주소", photoUrl)
        }
    }

}
