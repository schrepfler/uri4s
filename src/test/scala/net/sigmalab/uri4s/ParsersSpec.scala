/*
 * Copyright 2017 Srdan Srepfler
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

package net.sigmalab.uri4s

import fastparse._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ParsersSpec extends AnyWordSpec with Matchers {

  "scheme parser" should {

    def scheme[_: P]: P[String] = P(CharsWhile(_ != "://").!)

    "handle simple scheme" in {

      val schemeValue: String = "http://srdan:srepfler@aaaaa:8080?queryKey=queryValue"

//      val parseResult = scheme.parse(schemeValue)
//
//      System.err.println(parseResult)
//      System.err.println(parseResult.get)
      // val uri = URI("https://auser:apass@github.com/schrepfler/scala-uri?queryKey=queryValue")

      //    val password = Password("password")
      //
      //    val authUserInfo = UserInfo("srdan", Some(password))
      //
      //    val authority = Authority(Some(authUserInfo), "blog.sigmalab.net", None)
      //    val uri = URI(Some("http://"), Some(authority), "pages/1", "hideComments=true", None)

    }

  }

}
