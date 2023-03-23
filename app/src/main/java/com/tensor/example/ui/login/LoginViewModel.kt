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
package com.tensor.example.ui.login

import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.tensor.example.R
import com.tensor.example.ui.base.BaseViewModel
import com.tensor.example.utils.ResourceHelper
import com.tensor.example.utils.validation.Validator
import com.tensor.example.utils.validation.Validator.isValidEmail
import com.tensor.example.utils.validation.Validator.isValidPassword
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val resourceHelper: ResourceHelper) :
    BaseViewModel() {

    var email = MutableLiveData<String>()
    var password = MutableLiveData<String>()

    fun isFormValid(): Boolean {
        when {
            email.value?.trim().isNullOrEmpty() -> formValidationLiveData.value =
                FormMessage(
                    email = resourceHelper.getString(R.string.error_email_address),
                    isEmailError = true
                )
            email.value?.isValidEmail() != true -> formValidationLiveData.value =
                FormMessage(
                    email = resourceHelper.getString(R.string.error_invalid_email),
                    isEmailError = true
                )
            password.value?.trim().isNullOrEmpty() -> formValidationLiveData.value =
                FormMessage(
                    password = resourceHelper.getString(R.string.error_password),
                    isPasswordError = true
                )
            else -> {
                formValidationLiveData.postValue(FormMessage(formIsValid = R.string.form_is_valid))
                return true
            }
        }
        return false
    }

    data class FormMessage(
        var email: String = "",
        var password: String = "",
        var isEmailError: Boolean = false,
        var isPasswordError: Boolean = false,
        @StringRes val formIsValid: Int = 0
    )

    val formValidationLiveData = MutableLiveData<FormMessage>()
}
