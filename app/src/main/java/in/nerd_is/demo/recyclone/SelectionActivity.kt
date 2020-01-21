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

package `in`.nerd_is.demo.recyclone

import `in`.nerd_is.demo.recyclone.databinding.ActivityLoadMoreBinding
import `in`.nerd_is.demo.recyclone.entity.Image
import `in`.nerd_is.recyclone.AbstractAdapter
import `in`.nerd_is.recyclone.EndlessScrollListener
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.collection.LongSparseArray
import androidx.collection.SparseArrayCompat
import androidx.recyclerview.selection.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class SelectionActivity : AppCompatActivity() {

  private lateinit var binding: ActivityLoadMoreBinding

  private val keyProvider = KeyProvider()
  private lateinit var adapter: SelectionAdapter

  private val loadMoreSubject = object : EndlessScrollListener.LoadMoreSubject {
    override val isLoading: Boolean
      get() = binding.progressBar.visibility == View.VISIBLE

    override fun loadMore() {
      addData()
    }
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityLoadMoreBinding.inflate(layoutInflater)
    setContentView(binding.root)

    with(binding) {
      adapter = SelectionAdapter().apply {
        setHasStableIds(true)
        addRule(Image::class.java, ImageRule)
      }
      recyclerView.layoutManager = GridLayoutManager(this@SelectionActivity, 3)
      recyclerView.setHasFixedSize(true)
      recyclerView.adapter = adapter
      recyclerView.addOnScrollListener(EndlessScrollListener(loadMoreSubject))

      val tracker = SelectionTracker.Builder<Long>(
        "my-selection",
        binding.recyclerView,
        keyProvider,
        DetailsLookup(recyclerView),
        StorageStrategy.createLongStorage()
      )
        .withSelectionPredicate(SelectionPredicates.createSelectAnything())
        .build()

      adapter.tracker = tracker
    }

    addData()
  }

  private fun addData() {
    val data = DataUtils.generateImageList()

    keyProvider.addData(data)
    adapter.addData(data)
  }
}

class SelectionAdapter : AbstractAdapter() {

  lateinit var tracker: SelectionTracker<Long>

  private val data = ArrayList<Image>()

  override fun get(position: Int): Any? {
    return data[position]
  }

  override fun getSize(): Int {
    return data.size
  }

  override fun getItemId(position: Int): Long {
    return data[position].id
  }

  fun addData(list: List<Image>) {
    val oldSize = data.size
    data.addAll(list)
    notifyItemRangeInserted(oldSize, list.size)
  }
}

class KeyProvider : ItemKeyProvider<Long>(SCOPE_MAPPED) {

  private val positionToKey = SparseArrayCompat<Long>()
  private val keyToPosition = LongSparseArray<Int>()

  override fun getKey(position: Int): Long? {
    return positionToKey[position]
  }

  override fun getPosition(key: Long): Int {
    return keyToPosition[key]!!
  }

  fun addData(list: List<Image>) {
    for (item in list) {
      val position = positionToKey.size()
      val id = item.id
      positionToKey.put(position, id)
      keyToPosition.put(id, position)
    }
  }
}

class DetailsLookup(private val recyclerView: RecyclerView) : ItemDetailsLookup<Long>() {
  override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
    val view = recyclerView.findChildViewUnder(e.x, e.y) ?: return null
    val holder = recyclerView.getChildViewHolder(view) as ImageHolder
    return holder.itemDetails
  }
}