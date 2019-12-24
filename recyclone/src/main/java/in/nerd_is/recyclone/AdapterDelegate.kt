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
 * @author Xuqiang ZHENG on 18/4/21.
 */
class AdapterDelegate(
  private val manager: RuleManager,
  private val dataOwner: DataOwner
) {
  fun getItemViewType(position: Int): Int {
    return manager.getType(dataOwner[position])
  }

  fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
    return manager.createViewHolder(parent, viewType)
  }

  fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
    manager.bindViewHolder(holder, dataOwner[position])
  }

  fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int, payloads: List<Any>) {
    manager.bindViewHolder(holder, dataOwner[position], payloads)
  }
}
