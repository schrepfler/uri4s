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

object URI {

  import fastparse.all._
  import pprint._

  def mkScheme(scheme: String)       = ???
  def mkUsername                     = ???
  def mkPassword(password: String)   = Password(_)
  def mkHost                         = ???
  def mkPathPiece(pathPiece: String) = PathPiece(_)
  def mkQueryKey                     = ???
  def mkQueryValue                   = ???
  def mkFragment                     = ???

  val scheme: Parser[String] = P(CharsWhile(_ != ':')).!.log()

  val username = P(CharsWhile(_ != ':')).!.log()

  val password = P(CharsWhile(_ != ':')).!.log()

  val userinfo = P(username ~ password ~ "@").!.log()

  val host = P(CharsWhile(_ != ':')).!.log()

  val port = P(CharsWhile(_ != ':')).!.log()

  val authority = P("//" ~ userinfo ~ host ~ port).log()

  val path = P(CharsWhile(_ != '?')).!.log()

  val query = P(CharsWhile(_ != End)).!.log()

  val fragment = P(CharsWhile(_ != End)).!.log()

  val hierpart = P(":" ~ authority ~ path ~ query ~ fragment).log()

  val uri = P(scheme ~ hierpart).log()

  val pass = ???

  val authUserInfo = UserInfo("srdan", Some(pass))

  val auth = Authority(Some(authUserInfo), "blog.sigmalab.net", None)

  def apply(value: String): URI = {
    println(s"Parsing: $value")

    val parsed = uri.parse(value)

    pprintln(s"scheme: $scheme")
    pprintln(s"username: $username")
    pprintln(s"password: $password")
    pprintln(s"userinfo: $userinfo")
    pprintln(s"host: $host")
    pprintln(s"port: $port")
    pprintln(s"authority: $authority")
    pprintln(s"path: $path")
    pprintln(s"query: $query")
    pprintln(s"fragment: $fragment")
    pprintln(s"hierpart: $hierpart")
    pprintln(s"uri: $uri")
    pprintln(s"pass: $pass")
    pprintln(s"authUserInfo: $authUserInfo")
    pprintln(s"auth: $auth")

    pprintln(parsed)

    URI(Some("http://"), Some(auth), "pages/1", "hideComments=true", None)
  }
}

/**
  * Uniform resource identifier (URI) reference.
  */
case class URI(uriScheme: Option[String],
               uriAuthority: Option[Authority],
               uriPath: String,
               uriQuery: String,
               uriFragment: Option[String])

case class Authority(authUserInfo: Option[UserInfo], authHost: String, authPort: Option[String])

case class Password(password: String)

case class UserInfo(uiUsername: String, uiPassword: Option[Password])

case class PathPiece(pathPiece: String)

case class QueryParam()

case class QueryKey()
