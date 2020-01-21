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

import java.util.*

/**
 * @author Xuqiang ZHENG on 18/4/19.
 */
@Suppress("unused", "MemberVisibilityCanBePrivate")
open class RecyclerAdapter : AbstractAdapter() {

  private var _data: MutableList<Any?> = ArrayList()

  override fun get(position: Int): Any? {
    return _data[position]
  }

  override fun getSize(): Int {
    return _data.size
  }

  private val isDataEmpty: Boolean
    get() = _data.isEmpty()

  fun swapData(list: List<Any?>) {
    _data = ArrayList(list)
    notifyDataSetChanged()
  }

  fun insert(position: Int, item: Any) {
    if (isDataEmpty) {
      _data = ArrayList()
      _data.add(item)
      notifyItemInserted(position)
    } else {
      _data.add(position, item)
      notifyItemInserted(position)
    }
  }

  fun insert(position: Int, list: List<*>) {
    if (isDataEmpty) {
      _data = ArrayList(list)
      notifyItemRangeInserted(0, list.size)
    } else {
      _data.addAll(position, list)
      notifyItemRangeInserted(position, list.size)
    }
  }

  fun append(item: Any) {
    insert(_data.size, item)
  }

  fun append(list: List<*>) {
    insert(_data.size, list)
  }

  fun appendToHead(item: Any) {
    insert(0, item)
  }

  fun appendToHead(list: List<*>) {
    insert(0, list)
  }

  fun update(position: Int, item: Any) {
    _data[position] = item
    notifyItemChanged(position)
  }

  fun update(position: Int, list: List<*>) {
    for (i in list.indices) {
      _data[position + i] = list[i]
    }
    notifyItemRangeChanged(position, list.size)
  }

  fun remove(position: Int) {
    _data.removeAt(position)
    notifyItemRemoved(position)
  }

  fun remove(position: Int, itemCount: Int) {
    val list = ArrayList<Any?>(_data.size - itemCount)
    list.addAll(_data.subList(0, position))
    list.addAll(_data.subList(position + itemCount, _data.size))
    _data = list
    notifyItemRangeRemoved(position, itemCount)
  }
}
