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
import androidx.recyclerview.widget.RecyclerView.ViewHolder

/**
 * @author Xuqiang ZHENG on 2017/2/25.
 */
@Suppress("UNCHECKED_CAST")
class RuleManager(private val adapter: RecyclerView.Adapter<ViewHolder>) {

  private val ruleSet = RuleSet()
  private var nullTypeRule: TypeRule<NullType>? = null

  fun setNullTypeRule(typeRule: TypeRule<NullType>) {
    (typeRule.rule as Rule<NullType, ViewHolder>).adapter = adapter
    nullTypeRule = typeRule
  }

  fun <T> add(typeRule: TypeRule<T>) {
    (typeRule.rule as Rule<T, ViewHolder>).adapter = adapter
    ruleSet.add(typeRule)
  }

  fun getType(item: Any?): Int {
    if (item == null) {
      return NULL_TYPE
    }
    return ruleSet.getType(item::class.java)
  }

  fun createViewHolder(parent: ViewGroup, type: Int): ViewHolder {
    return getNullableRule(type).onCreateHolder(LayoutInflater.from(parent.context), parent)
  }

  fun bindViewHolder(viewHolder: ViewHolder, item: Any?) {
    getNullableRule(getType(item)).onBindHolder(viewHolder, replaceNullWithNullType(item))
  }

  fun bindViewHolder(viewHolder: ViewHolder, item: Any?, payloads: List<Any>) {
    getNullableRule(getType(item)).onBindHolder(viewHolder, replaceNullWithNullType(item), payloads)
  }

  fun onViewRecycled(holder: ViewHolder) {
    getNullableRule(holder.itemViewType).onViewRecycled(holder)
  }

  fun onFailedToRecycleView(holder: ViewHolder): Boolean {
    return getNullableRule(holder.itemViewType).onFailedToRecycleView(holder)
  }

  fun onViewAttachedToWindow(holder: ViewHolder) {
    getNullableRule(holder.itemViewType).onViewAttachedToWindow(holder)
  }

  fun onViewDetachedFromWindow(holder: ViewHolder) {
    getNullableRule(holder.itemViewType).onViewDetachedFromWindow(holder)
  }

  private fun checkNullTypeRule() {
    if (nullTypeRule == null) {
      throw IllegalStateException("no TypeRule set for null item, consider invoke setNullTypeRule()")
    }
  }

  private fun getNullableRule(type: Int): Rule<Any, ViewHolder> {
    return if (type == NULL_TYPE) {
      checkNullTypeRule()
      nullTypeRule!!.rule as Rule<Any, ViewHolder>
    } else {
      ruleSet[type].rule as Rule<Any, ViewHolder>
    }
  }

  private fun replaceNullWithNullType(item: Any?): Any {
    return item ?: NullType
  }

  companion object {
    const val NULL_TYPE = -2
  }

  object NullType
}