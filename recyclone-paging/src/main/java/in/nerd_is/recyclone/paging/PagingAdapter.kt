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

package `in`.nerd_is.recyclone.paging

import `in`.nerd_is.recyclone.*
import android.view.ViewGroup
import androidx.paging.PagedList
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

class PagingAdapter(diffCallback: DiffUtil.ItemCallback<Any?>) : PagedListAdapter<Any?, RecyclerView.ViewHolder>(diffCallback), DataOwner {

  private val ruleManager = RuleManager(this)
  private val delegate = AdapterDelegate(ruleManager, this)

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
    payloads: List<Any>
  ) {
    delegate.onBindViewHolder(holder, position, payloads)
  }

  override fun onViewRecycled(holder: RecyclerView.ViewHolder) {
    delegate.onViewRecycled(holder)
  }

  override fun onFailedToRecycleView(holder: RecyclerView.ViewHolder): Boolean {
    return delegate.onFailedToRecycleView(holder)
  }

  override fun onViewAttachedToWindow(holder: RecyclerView.ViewHolder) {
    delegate.onViewAttachedToWindow(holder)
  }

  override fun onViewDetachedFromWindow(holder: RecyclerView.ViewHolder) {
    delegate.onViewDetachedFromWindow(holder)
  }

  fun <T> addRule(clazz: Class<T>, rule: Rule<T, *>) {
    ruleManager.add(TypeRule(clazz, rule))
  }

  fun setNullRule(rule: Rule<RuleManager.NullType, *>) {
    ruleManager.setNullTypeRule(TypeRule(RuleManager.NullType::class.java, rule))
  }

  /**
   * @see submitList
   */
  fun submitTypedList(pagedList: PagedList<out Any?>?) {
    @Suppress("UNCHECKED_CAST")
    submitList(pagedList as PagedList<Any?>?)
  }

  /**
   * @see submitList
   */
  fun submitTypedList(pagedList: PagedList<out Any?>?, commitCallback: Runnable?) {
    @Suppress("UNCHECKED_CAST")
    submitList(pagedList as PagedList<Any?>?, commitCallback)
  }

  /**
   * @see submitList
   */
  fun submitTypedList(pagedList: PagedList<out Any?>?, commitCallback: (() -> Unit)?) {
    @Suppress("UNCHECKED_CAST")
    submitList(pagedList as PagedList<Any?>?, commitCallback)
  }
}