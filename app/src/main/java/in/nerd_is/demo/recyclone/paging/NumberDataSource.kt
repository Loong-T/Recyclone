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
 *
 */

package `in`.nerd_is.demo.recyclone.paging

import android.util.Log
import androidx.paging.PageKeyedDataSource

class NumberDataSource : PageKeyedDataSource<Int, String?>() {
  override fun loadInitial(
    params: LoadInitialParams<Int>,
    callback: LoadInitialCallback<Int, String?>
  ) {
    Log.d("NumberDataSource", "load page 0 from loadInitial with size ${params.requestedLoadSize}")
    val list = (0..params.requestedLoadSize).map { it.toString() }
    callback.onResult(list, -1, 1)
  }

  override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, String?>) {
    Log.d("NumberDataSource", "load page ${params.key} from loadAfter")
    val start = params.requestedLoadSize * params.key
    val end = start + params.requestedLoadSize
    val list = (start..end).map { if ((it % 13) == 0) null else it.toString() }
    callback.onResult(list, params.key + 1)
  }

  override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, String?>) {
    Log.d("NumberDataSource", "load page ${params.key} from loadBefore")
    val end = params.requestedLoadSize * (params.key + 1) -1
    val start = end - params.requestedLoadSize
    val list = (start..end).map { it.toString() }
    callback.onResult(list, params.key - 1)
  }
}