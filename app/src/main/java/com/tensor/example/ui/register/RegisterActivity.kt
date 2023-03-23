/*
* Copyright 2023 TensorIotExample
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
*     http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package com.tensor.example.ui.register

import INTENT_USER
import android.view.View
import androidx.activity.viewModels
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.tensor.example.R
import com.tensor.example.databinding.ActivityRegisterBinding
import com.tensor.example.ui.base.BaseAppCompatActivity
import com.tensor.example.ui.profile.ProfileActivity
import com.tensor.example.utils.extension.hideKeyBoard
import com.tensor.example.utils.extension.launchActivity
import com.tensor.example.utils.extension.launchActivityAndFinishAll
import com.tensor.example.utils.extension.showError
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class RegisterActivity : BaseAppCompatActivity<ActivityRegisterBinding, RegisterViewModel>(),
    View.OnClickListener {

    private lateinit var auth: FirebaseAuth

    override fun initialize() {
        super.initialize()
        binding.clickHandler = this
        auth = Firebase.auth
    }

    override val viewModel: RegisterViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_register

    override fun onClick(v: View) {
        when (v.id) {
            R.id.btnSignup -> {
                viewModel.isFormValid()
            }
        }
    }

    override fun setupViewModel() {
        viewModel.formValidationLiveData.observe(this) { message ->
            message.run {
                when {
                    isEmailError -> {
                        showError(email)
                        binding.edtEmail.requestFocus()
                    }
                    isPasswordError -> {
                        showError(password)
                        binding.edtPassword.requestFocus()
                    }
                    isConfPasswordError -> {
                        showError(confPassword)
                        binding.edtConfPassword.requestFocus()
                    }
                    isUserNameError -> {
                        showError(username)
                        binding.edtUsername.requestFocus()
                    }
                    formIsValid != 0 -> {
                        this@RegisterActivity.hideKeyBoard()
                        onSignupClick()
                    }
                    else -> {
                        this@RegisterActivity.hideKeyBoard()
                    }
                }
            }
        }
    }

    private fun onSignupClick() {
        auth.createUserWithEmailAndPassword(viewModel.email.value!!, viewModel.password.value!!)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Timber.d("SIngup", "createUserWithEmail:success")
                    val user = auth.currentUser
                    Timber.d("User: $user");
                    launchActivityAndFinishAll<ProfileActivity> {
                        putExtra(INTENT_USER, user)
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    Timber.e("createUserWithEmail:failure", task.exception)
                    showError(task.exception?.message.toString())
                }
            }
    }

}
