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

import `in`.nerd_is.demo.recyclone.entity.Person
import com.github.javafaker.Faker

/**
 * @author Xuqiang ZHENG on 19/12/20.
 */
object DataUtils {
  private val faker = Faker()

  fun generatePerson(): Person {
    return Person(
      faker.name().fullName(),
      faker.internet().avatar(),
      faker.dog().gender(),
      faker.university().name()
    )
  }

  fun generatePersonList(count: Int = 20): List<Person> {
    return (0 until count).map { generatePerson() }
  }

  fun generateNameList(count: Int = 10): List<String> {
    return (0 until count).map { faker.name().nameWithMiddle() }
  }
}