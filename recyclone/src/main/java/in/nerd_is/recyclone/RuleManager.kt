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

/**
 * @author Xuqiang ZHENG on 2017/2/25.
 */
class RuleManager {

  private val ruleSet = RuleSet()
  private var nullTypeRule: TypeRule<Any?>? = null

  fun setNullTypeRule(rule: TypeRule<Any?>) {
    nullTypeRule = rule
  }

  fun <T> add(type: TypeRule<T>) {
    ruleSet.add(type)
  }

  fun getType(item: Any?): Int {
    if (item == null) {
      return NULL_TYPE
    }
    return ruleSet.getType(item::class.java)
  }

  fun createViewHolder(parent: ViewGroup, type: Int): RecyclerView.ViewHolder {
    if (type == NULL_TYPE) {
      if (nullTypeRule == null) {
        throw IllegalStateException("no TypeRule set for null item, consider invoke setNullTypeRule()")
      }
      return nullTypeRule!!.rule.onCreateHolder(LayoutInflater.from(parent.context), parent)
    }

    return ruleSet[type].rule.onCreateHolder(LayoutInflater.from(parent.context), parent)
  }

  fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any?) {
    @Suppress("UNCHECKED_CAST")
    val rule = ruleSet[getType(item)].rule as Rule<Any?, RecyclerView.ViewHolder>
    rule.onBindHolder(viewHolder, item)
  }

  fun bindViewHolder(viewHolder: RecyclerView.ViewHolder, item: Any?, payloads: List<Any>) {
    bindViewHolder(viewHolder, item)
  }

  companion object {
    const val NULL_TYPE = -2
  }
}