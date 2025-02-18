package com.example.travel_scheduler.view.register

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import com.example.travel_scheduler.R
import com.example.travel_scheduler.firebase.AuthProviders
import com.example.travel_scheduler.firebase.GoogleSignUp
import kotlinx.android.synthetic.main.activity_main.*

class RegisterActivity : AppCompatActivity() {

    lateinit var googleSignInBtn: Button
    lateinit var userName: TextView
    lateinit var emailField: TextView
    lateinit var pwField: TextView
    private lateinit var confirmPwField: TextView
    private lateinit var signUpButton : Button

    private val authProviders = AuthProviders()
    private val googleSignIn : GoogleSignUp by lazy{
        GoogleSignUp(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
        supportActionBar?.hide()

        userName = findViewById(R.id.registerName)
        emailField = findViewById(R.id.setEmailField)
        pwField = findViewById(R.id.setPasswordField)
        confirmPwField = findViewById(R.id.setConfirmPassword)
        signUpButton = findViewById(R.id.signUpBtn)

        googleSignInBtn = findViewById(R.id.googleSignUp)

        googleSignInBtn.setOnClickListener{
            showLoading()
            googleSignIn.signIn()
        }

        signUpButton.setOnClickListener{
            showLoading()
            authProviders.registerUser(this,baseContext,userName.text.toString(), emailField.text.toString(),
                         pwField.text.toString(),confirmPwField.text.toString())
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        googleSignIn.onActivityResult(requestCode, resultCode, data)
    }

    private fun showLoading(){
        loadingLayout.visibility = View.VISIBLE
    }
}