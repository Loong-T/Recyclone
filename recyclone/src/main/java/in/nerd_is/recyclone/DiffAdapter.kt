/*
 * Copyright 2020 Xuqiang ZHENG
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

package `in`.nerd_is.recyclone

import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil

open class DiffAdapter(callback: DiffUtil.ItemCallback<Any?>) : AbstractAdapter() {

  @Suppress("LeakingThis")
  val differ = AsyncListDiffer(this, callback)

  override fun get(position: Int): Any? {
    return differ.currentList[position]
  }

  override fun getSize(): Int {
    return differ.currentList.size
  }

  fun submitList(list: List<Any?>) {
    differ.submitList(list)
  }
}