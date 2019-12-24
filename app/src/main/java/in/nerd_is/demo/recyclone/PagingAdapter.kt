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

import `in`.nerd_is.recyclone.AdapterDelegate
import `in`.nerd_is.recyclone.DataOwner
import `in`.nerd_is.recyclone.RuleManager
import `in`.nerd_is.recyclone.TypeRule
import android.annotation.SuppressLint
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

object DIFF_CALLBACK : DiffUtil.ItemCallback<Any>() {
  override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
    return oldItem === newItem
  }

  @SuppressLint("DiffUtilEquals")
  override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
    return oldItem.toString() == newItem.toString()
  }
}

class PagingAdapter : PagedListAdapter<Any, RecyclerView.ViewHolder>(DIFF_CALLBACK), DataOwner {

  private val ruleManager = RuleManager()
  private val delegate = AdapterDelegate(ruleManager, this)

  init {
    ruleManager.add(TypeRule(String::class.java, StringRule))
  }

  override fun get(position: Int): Any? {
    return getItem(position)
  }

  override fun getSize(): Int {
    return itemCount
  }

  override fun getItemViewType(position: Int): Int {
    return delegate.getItemViewType(position)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return delegate.onCreateViewHolder(parent, viewType)
  }

  override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    delegate.onBindViewHolder(holder, position)
  }

  override fun onBindViewHolder(
    holder: RecyclerView.ViewHolder,
    position: Int,
    payloads: MutableList<Any>
  ) {
    delegate.onBindViewHolder(holder, position, payloads)
  }
}