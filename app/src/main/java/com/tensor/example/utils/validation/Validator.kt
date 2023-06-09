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
package com.tensor.example.utils.validation

import android.util.Patterns

/**
 * Validators.
 */
object Validator {
    object PhoneNumber {
        const val MAX_LENGTH = 10
        const val MIN_LENGTH = 4
    }

    object CountryZipCode {
        const val MAX_LENGTH = 4 // Including '+' symbol
        const val MIN_LENGTH = 2 // Including '+' symbol
    }

    object Password {
        const val MIN_LENGTH = 8
        const val MAX_LENGTH = 12
    }

    /**
     * Thus fun is used to check email is valid or not.
     * @receiver String
     * @return Boolean
     */
    fun String.isValidEmail(): Boolean = isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(this).matches()

    /**
     * Is password valid.
     */
    fun String.isValidPassword(): Boolean = this.isNotBlank() && this.length in Password.MIN_LENGTH..Password.MAX_LENGTH

    /**
     * Is phone number and password valid.
     */
    /*fun isValid(countryZipCode: String, phoneNumber: String, password: String): Boolean =
        isValidCountryZipCode(countryZipCode) &&
            isValidPhoneNumber(phoneNumber) &&
            isValidPassword(password)*/

    /**
     * Is phone number valid.
     */
    fun isValidPhoneNumber(phoneNumber: String): Boolean =
        phoneNumber.isNotBlank() &&
            phoneNumber.length in PhoneNumber.MIN_LENGTH..PhoneNumber.MAX_LENGTH

    /**
     * Is password valid.
     */
   /* fun isValidPassword(password: String): Boolean =
        password.isNotBlank() &&
            password.length in Password.MIN_LENGTH..Password.MAX_LENGTH*/

    /**
     * Is country zip code valid.
     */
    fun isValidCountryZipCode(countryZipCode: String): Boolean =
        countryZipCode.isNotBlank() &&
            countryZipCode[0] == '+' &&
            countryZipCode.length in CountryZipCode.MIN_LENGTH..CountryZipCode.MAX_LENGTH &&
            countryZipCode.slice(1 until countryZipCode.length).matches("\\d+".toRegex()) // Exclude first character and check else are digits only

    /**
     * Is valid password and confirm password.
     */
    fun isValidPasswordAndConfirmPassword(password: String, confirmPassword: String): Boolean =
        (password.isValidPassword() && confirmPassword.isValidPassword()) &&
            password == confirmPassword
}
