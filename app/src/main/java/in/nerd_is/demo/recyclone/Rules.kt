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

package `in`.nerd_is.demo.recyclone

import `in`.nerd_is.demo.recyclone.databinding.ItemPersonBinding
import `in`.nerd_is.demo.recyclone.entity.Person
import `in`.nerd_is.recyclone.Rule
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import coil.api.load

/**
 * @author Xuqiang ZHENG on 19/12/20.
 */
class TitleHolder(view: View) : RecyclerView.ViewHolder(view) {
  private val textView = view as TextView

  fun bindTo(data: String) {
    textView.text = data
  }
}

object StringRule : Rule<String, TitleHolder> {
  override fun onCreateHolder(inflater: LayoutInflater, parent: ViewGroup): TitleHolder {
    return TitleHolder(inflater.inflate(R.layout.item_title, parent, false))
  }

  override fun onBindHolder(holder: TitleHolder, item: String) {
    holder.bindTo(item)
  }
}

class PersonHolder(private val binding: ItemPersonBinding) : RecyclerView.ViewHolder(binding.root) {
  fun bindTo(data: Person) {
    with(binding) {
      ivAvatar.load(data.avatar)
      tvName.text = data.name
      tvUniversity.text = data.university
      tvGender.text = data.gender
    }
  }
}

object PersonRule : Rule<Person, PersonHolder> {
  override fun onCreateHolder(inflater: LayoutInflater, parent: ViewGroup): PersonHolder {
    return PersonHolder(ItemPersonBinding.inflate(inflater, parent, false))
  }

  override fun onBindHolder(holder: PersonHolder, item: Person) {
    holder.bindTo(item)
  }
}