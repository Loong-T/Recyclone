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

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * @author Xuqiang ZHENG on 18/4/20.
 */
class EndlessScrollListener(
    private val layoutManager: RecyclerView.LayoutManager,
    private val loadMoreSubject: LoadMoreSubject
) : RecyclerView.OnScrollListener() {

    var visibleThreshold = 6

    private val lastVisibleItemPosition: Int
        get() {
            var lastVisibleItemPosition = 0
            if (layoutManager is StaggeredGridLayoutManager) {
                val lastVisibleItemPositions = layoutManager
                    .findLastVisibleItemPositions(null)
                lastVisibleItemPosition = getLastVisibleItem(lastVisibleItemPositions)
            } else if (layoutManager is GridLayoutManager) {
                lastVisibleItemPosition = layoutManager
                    .findLastVisibleItemPosition()
            } else if (layoutManager is LinearLayoutManager) {
                lastVisibleItemPosition = layoutManager
                    .findLastVisibleItemPosition()
            }
            return lastVisibleItemPosition
        }

    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (dy < 0 || loadMoreSubject.isLoading)
            return

        val totalItemCount = layoutManager.itemCount
        val lastVisibleItemPosition = lastVisibleItemPosition

        if (lastVisibleItemPosition + visibleThreshold >= totalItemCount - 1) {
            loadMoreSubject.loadMore()
        }
    }

    fun attachTo(recyclerView: RecyclerView) {
        recyclerView.addOnScrollListener(this)
    }

    private fun getLastVisibleItem(lastVisibleItemPositions: IntArray): Int {
        var max = lastVisibleItemPositions[0]
        for (position in lastVisibleItemPositions) {
            if (position > max) {
                max = position
            }
        }
        return max
    }

    interface LoadMoreSubject {
        val isLoading: Boolean
        fun loadMore()
    }
}
