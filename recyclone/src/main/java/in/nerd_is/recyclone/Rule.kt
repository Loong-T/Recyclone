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

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

interface Rule<T, VH : RecyclerView.ViewHolder> {

  /**
   * Called when RecyclerView needs a new {@link ViewHolder} of the given type to represent
   * an item.
   *
   * @see RecyclerView.Adapter.onCreateViewHolder
   */
  fun onCreateHolder(inflater: LayoutInflater, parent: ViewGroup): VH

  /**
   * Called by RecyclerView to display the data at the specified position.
   * This method should update the contents of the RecyclerView.
   * ViewHolder.itemView to reflect the item at the given position.
   *
   * @see RecyclerView.Adapter.onBindViewHolder
   */
  fun onBindHolder(holder: VH, item: T)

  /**
   * Called by RecyclerView to display the data at the specified position.
   * This method should update the contents of the RecyclerView.
   * ViewHolder.itemView to reflect the item at the given position.
   *
   * @see RecyclerView.Adapter.onBindViewHolder
   */
  fun onBindHolder(holder: VH, item: T, payloads: List<Any>) {
    onBindHolder(holder, item)
  }

  /**
   * Called when a view created by this adapter has been recycled.
   *
   * @see RecyclerView.Adapter.onViewRecycled
   */
  fun onViewRecycled(holder: VH) {

  }

  /**
   * Called by the RecyclerView if a ViewHolder created by this Adapter cannot be recycled
   * due to its transient state. Upon receiving this callback, Adapter can clear the
   * animation(s) that effect the View's transient state and return <code>true</code> so that
   * the View can be recycled. Keep in mind that the View in question is already removed from
   * the RecyclerView.
   *
   * @see RecyclerView.Adapter.onFailedToRecycleView
   */
  fun onFailedToRecycleView(holder: VH): Boolean {
    return false
  }

  /**
   * Called when a view created by this adapter has been attached to a window.
   *
   * @see RecyclerView.Adapter.onViewAttachedToWindow
   */
  fun onViewAttachedToWindow(holder: VH) {

  }

  /**
   * Called when a view created by this adapter has been detached from its window.
   *
   * @see RecyclerView.Adapter.onViewDetachedFromWindow
   */
  fun onViewDetachedFromWindow(holder: VH) {

  }
}