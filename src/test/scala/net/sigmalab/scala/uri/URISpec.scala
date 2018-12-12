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

package net.sigmalab.scala.uri

import org.scalatest.{ Matchers, WordSpec }
import net.sigmalab.scala.uri._

class URISpec extends WordSpec with Matchers {

  "apply" should {
    "handle simple URL" in {
//      def strUri: String = "http://www.example.com"

//      val uri = URI(strUri)

      val uri = URI("https://auser:apass@github.com:80/schrepfler/scala-uri?queryKey=queryValue#id")

//    val password = Password("password")
//
//    val authUserInfo = UserInfo("srdan", Some(password))
//
//    val authority = Authority(Some(authUserInfo), "blog.sigmalab.net", None)
      println("------")
      println(uri)
      // println(uri)

//    val uri = URI(Some("http://"), Some(authority), "pages/1", "hideComments=true", None)

    }

  }

}
