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
package com.tensor.example.ui.sample

import android.view.View
import androidx.activity.viewModels
import com.tensor.example.R
import com.tensor.example.ui.base.BaseAppCompatActivity
import com.tensor.example.databinding.ActivitySampleBinding
import com.tensor.example.utils.RecyclerPaginationListener
import com.tensor.example.utils.extension.observeEvent
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SampleActivity : BaseAppCompatActivity<ActivitySampleBinding, SampleViewModel>(), View.OnClickListener {
    override val viewModel: SampleViewModel by viewModels()

    override fun getLayoutResId(): Int = R.layout.activity_sample

    private val userAdapter = UserAdapter()
    private val paginationListener = RecyclerPaginationListener {
        viewModel.loadMoreUsers()
    }

    override fun initialize() {
        super.initialize()
        binding.apply {
            clickHandler = this@SampleActivity
            rvUsers.adapter = userAdapter
            rvUsers.addOnScrollListener(paginationListener)
        }
    }

    override fun setupViewModel() {
        super.setupViewModel()

        binding.shimmer.startShimmer()

        viewModel.showShimmer.observe(this) {
            if (it) {
                binding.shimmer.startShimmer()
            } else {
                binding.shimmer.stopShimmer()
            }
        }

        viewModel.isLoadingPage.observe(this) {
            if (it) {
                userAdapter.addLoader()
            } else {
                userAdapter.removeLoader()
            }
        }

        viewModel.onNewUserList.observeEvent(this) {
            userAdapter.addAllItem(ArrayList(it))
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_back -> {
                onBackPressed()
            }
        }
    }
}
