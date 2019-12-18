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

    private lateinit var ruleManager: RuleManager
    private lateinit var delegate: AdapterDelegate

    fun setRuleManager(ruleManager: RuleManager) {
        this.ruleManager = ruleManager
        delegate = AdapterDelegate(ruleManager, this)
    }

    override fun getItemCount(): Int {
        return data.size
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
}