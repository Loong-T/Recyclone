/*
 * Copyright 2019 Xuqiang ZHENG
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
 *
 */

package `in`.nerd_is.demo.recyclone

import `in`.nerd_is.demo.recyclone.databinding.ActivityLoadMoreBinding
import `in`.nerd_is.demo.recyclone.entity.Person
import `in`.nerd_is.recyclone.EndlessScrollListener
import `in`.nerd_is.recyclone.RecyclerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import java.util.concurrent.Executors

class LoadMoreActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoadMoreBinding
  private val bgExecutors = Executors.newSingleThreadExecutor()
  private lateinit var adapter: RecyclerAdapter

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoadMoreBinding.inflate(layoutInflater)
    setContentView(binding.root)

    with(binding) {
      recyclerView.layoutManager = LinearLayoutManager(this@LoadMoreActivity)
      recyclerView.addOnScrollListener(EndlessScrollListener(loadMoreSubject))

      adapter = RecyclerAdapter()
      adapter.addRule(Person::class.java, PersonRule)
      recyclerView.adapter = adapter
    }

    loadMoreSubject.loadMore()
  }

  private val loadMoreSubject = object : EndlessScrollListener.LoadMoreSubject {
    override val isLoading: Boolean
      get() = binding.progressBar.visibility == View.VISIBLE

    override fun loadMore() {
      binding.progressBar.visibility = View.VISIBLE
      bgExecutors.submit {
        Thread.sleep(1500)
        val list = DataUtils.generatePersonList(12)
        runOnUiThread {
          adapter.append(list)
          binding.progressBar.visibility = View.GONE
        }
      }
    }
  }
}
