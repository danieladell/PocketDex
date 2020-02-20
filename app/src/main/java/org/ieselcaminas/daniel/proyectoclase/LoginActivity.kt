package org.ieselcaminas.daniel.proyectoclase

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.firebase.auth.FirebaseAuth
import org.ieselcaminas.daniel.proyectoclase.databinding.ActivityLoginBinding
import org.ieselcaminas.daniel.proyectoclase.firebase.FirestoreData


private const val RC_SIGN_IN = 1001

class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    private lateinit var firebaseData: FirestoreData

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding: ActivityLoginBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_login)

        mAuth = FirebaseAuth.getInstance()

        binding.Login.setOnClickListener {
            // Choose authentication providers
            val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build()
            )

// Create and launch sign-in intent
            startActivityForResult(
                AuthUI.getInstance()
                    .createSignInIntentBuilder()
                    .setAvailableProviders(providers).setTheme(R.style.MyTheme)
                    .build(),
                RC_SIGN_IN
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

            if (resultCode == Activity.RESULT_OK) {
                // Successfully signed in
                val user = FirebaseAuth.getInstance().currentUser

                firebaseData = FirestoreData()

                firebaseData.createUser()

                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)

            } else {
                // Sign in failed. If response is null the user canceled the
                // sign-in flow using the back button. Otherwise check
                // response.getError().getErrorCode() and handle the error.
                // ...
                response?.error?.errorCode
            }
        }
    }
}

