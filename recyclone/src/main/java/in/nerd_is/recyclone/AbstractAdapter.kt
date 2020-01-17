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
 */

package `in`.nerd_is.recyclone

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * @author Xuqiang ZHENG on 2016/11/23.
 */
abstract class AbstractAdapter :
  RecyclerView.Adapter<RecyclerView.ViewHolder>(), DataOwner {

  protected val ruleManager: RuleManager = RuleManager()

  @Suppress("LeakingThis")
  private val delegate: AdapterDelegate = AdapterDelegate(ruleManager, this)

  override fun getItemCount(): Int {
    return getSize()
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
    holder: RecyclerView.ViewHolder, position: Int,
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
}