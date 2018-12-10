/*
 * Copyright 2018 Srdan Srepfler
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
import fastparse.parse
import net.sigmalab.scala.uri.URI.emptyURI

import scala.util.Failure

object URI {

  import fastparse._, NoWhitespace._
  import pprint._

  def mkScheme(scheme: String)       = ???
  def mkUsername                     = ???
  def mkPassword(password: String)   = Password(_)
  def mkHost                         = ???
  def mkPathPiece(pathPiece: String) = PathPiece(_)
  def mkQueryKey                     = ???
  def mkQueryValue                   = ???
  def mkFragment                     = ???

  def scheme[_: P]: P[Option[String]] = P(CharsWhile(_ != ':').!).?.log

  def username[_: P]: P[String] = P(CharsWhile(_ != ':')).!.log

  def password[_: P]: P[String] = P(CharsWhile(_ != '@')).!.log

  def userinfo[_: P]: P[String] = P(username ~ password).!.log

  def host[_: P]: P[String] = P(CharsWhile(_ != ':')).!.log

  def port[_: P]: P[String] = P(":" ~ CharsWhile(_ != '/')).!.log

  def authority[_: P]: P[Option[(String, String, String)]] = P(userinfo ~ host ~ port).?.log

  //      *( "/" segment )
  def `path-abempty`[_: P] = P("")

  //      "/" [ segment-nz *( "/" segment ) ]
  def `path-absolute`[_: P] = P("")

  //      segment-nz-nc *( "/" segment )
  def `path-noscheme`[_: P] = P("")

  //      segment-nz *( "/" segment )
  def `path-rootless`[_: P] = P("")

  //      0<pchar>
  def `path-empty`[_: P] = P("")

//  def path[_: P]: P[String] = P(CharsWhile(_ != '?')).!.log
  def path[_: P]: P[String] = P(`path-abempty` | `path-absolute` | `path-noscheme` | `path-rootless` | `path-empty`).!.log

  def query[_: P]: P[Option[String]] = P("?" ~ AnyChar.rep.! ~ End).?.log

  def fragment[_: P]: P[String] = P(AnyChar.rep ~ End).!.log

  def `hier-part`[_: P]: P[(Option[(String, String, String)], String, Option[String], String)] = P(authority ~ path ~ query ~ fragment).log

  def uri[_: P]: P[
    (Option[String],
     (Option[(String, String, String)], String, Option[String], String),
     Option[String])
  ] = P(scheme ~ ":" ~ `hier-part` ~ query).log

  def pass[_: P]: P[String] = P(CharsWhile(_ != ':')).!.log

  def authUserInfo[_: P]: P[String] = P(CharsWhile(_ != '@')).!.log

  val emptyURI = URI(None, None, "", "", None)

  def apply(input: String): Either[Error, URI] = {
    println(s"Parsing: $input")
    val result = parse(input, URI.uri(_))
//    println(result)
    result.fold(
      (_, _, _) => Left(new Error(s"Cannot parse $input as an URI")),
      (v, _) => {
        println(s"Parsed: $v")
        Right(new URI(v._1, None, "", "", None))
      }
    )
  }
}


//  def this(value: String): URI = {
//    println(s"Parsing: $value")
//
//    val result = parse(value, URI.uri(_))
//    //    val parsed = uri.parse(value)
//
//    println(result)
//    //    pprintln(s"scheme: $scheme")
//    //    pprintln(s"username: $username")
//    //    pprintln(s"password: $password")
//    //    pprintln(s"userinfo: $userinfo")
//    //    pprintln(s"host: $host")
//    //    pprintln(s"port: $port")
//    //    pprintln(s"authority: $authority")
//    //    pprintln(s"path: $path")
//    //    pprintln(s"query: $query")
//    //    pprintln(s"fragment: $fragment")
//    //    pprintln(s"hierpart: $`hier-part`")
//    //    pprintln(s"uri: $uri")
//    //    pprintln(s"pass: $pass")
//    //    pprintln(s"authUserInfo: $authUserInfo")
//    //    pprintln(s"auth: $auth")
//
//    //    pprintln(parsed)
////    result.fold[URI](
////      (_, _, _) => emptyURI,
////      (one, two) => {
////        URI(one._1, None, "path", one._2._3.get, None)
////      }
////    )
//    emptyURI
//  }

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
