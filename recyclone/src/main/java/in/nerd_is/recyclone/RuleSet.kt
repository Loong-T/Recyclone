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

internal class RuleSet {
  private val rules = ArrayList<TypeRule<*>>()

  fun <T> add(type: TypeRule<T>) {
    rules.add(type)
  }

  fun <T> getType(clazz: Class<T>): Int {
    var idx = rules.indexOfFirst { it.clazz == clazz }
    if (idx == -1) {
      idx = rules.indexOfFirst { it.clazz.isAssignableFrom(clazz) }
    }
    if (idx == -1) {
      throw IllegalStateException("class ${clazz.name} is not bond to a TypeRule, do you forget to add one?")
    }
    return idx
  }

  operator fun get(type: Int): TypeRule<*> {
    return rules[type]
  }
}