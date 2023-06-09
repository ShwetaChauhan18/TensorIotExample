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
package com.tensor.example

import android.app.Application
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.util.Log
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import com.tensor.example.utils.NetworkUtil
import com.tensor.example.utils.ProductFlavor
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class TensorIotExampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        initTimber()
        observeNetwork()
    }

    private fun isFlavorProductionOrQA(): Boolean = ProductFlavor.CURRENT == ProductFlavor.Flavor.QA ||
            ProductFlavor.CURRENT == ProductFlavor.Flavor.PRODUCTION

    private fun initTimber() {
        if (BuildConfig.DEBUG) {
            // Show logs only when on debug
            Timber.plant(Timber.DebugTree())
        }
    }

    /**
    * Observe network state.
    */
    private fun observeNetwork() {
        val cm: ConnectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkRequest = NetworkRequest.Builder().build()

        cm.registerNetworkCallback(
            networkRequest,
            object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    NetworkUtil.isNetworkConnected = true
                }

                override fun onLost(network: Network) {
                    NetworkUtil.isNetworkConnected = false
                }
            })
    }
}
