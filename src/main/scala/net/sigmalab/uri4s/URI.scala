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

package net.sigmalab.uri4s

object URI {

  import fastparse._, NoWhitespace._

  def mkScheme(scheme: String)       = ???
  def mkUsername                     = ???
  def mkPassword(password: String)   = Password(_)
  def mkHost                         = ???
  def mkPathPiece(pathPiece: String) = PathPiece(_)
  def mkQueryKey                     = ???
  def mkQueryValue                   = ???
  def mkFragment                     = ???

  def scheme[_: P]: P[String] = P(CharsWhile(_ != ':').!).log

  def username[_: P]: P[String] = P(CharsWhile(_ != ':')).!.log

  def password[_: P]: P[String] = P(CharsWhile(_ != '@')).!.log

  def userinfo[_: P]: P[(String, String)] = P(username.! ~ ":" ~ password.!).log

  def host[_: P]: P[String] = P(`IP-literal` | `IPv4address` | `reg-name`).!.log

  def `IP-literal`[_: P]: P[String] = P("[" ~ (IPv6address | IPvFuture) ~ "]")

  def IPv4address[_: P]: P[String] =
    P(`dec-octet` ~ "." ~ `dec-octet` ~ "." ~ `dec-octet` ~ "." ~ `dec-octet`).!.log

  def IPvFuture[_: P]: P[String] =
    P(
      "v" ~ HEXDIG ~ "." ~ (unreserved | `sub-delims` | ":")
    ).!.log

  def HEXDIG[_: P]: P[String] = P(CharIn("0-9a-fA-F")).!

  def `dec-octet`[_: P]: P[String] =
    P(
      DIGIT |
      ONE_TO_NINE ~ DIGIT |
      "1" ~ DIGIT ~ DIGIT |
      "2" ~ ZERO_TO_FOUR ~ DIGIT |
      "25" ~ ZERO_TO_FIVE
    ).!.log

  def IPv6address[_: P]: P[String] = P("X").! // TODO

  def `reg-name`[_: P]: P[String] =
    P(
      unreserved | `pct-encoded` | `sub-delims`
    ).rep.!.log

  def port[_: P]: P[String] = P(CharsWhile(_ != '/')).!.log

  def authority[_: P]: P[Option[(Option[(String, String)], String, Option[String])]] =
    P((userinfo ~ "@").? ~ host ~ (":" ~ port).?).?.log

  def ALPHA[_: P]: P[String] = P(CharIn("a-zA-Z")).!.log

  def DIGIT[_: P]: P[String] = P(CharIn("0-9")).!.log

  def ONE_TO_NINE[_: P]: P[String] = P(CharIn("1-9")).!.log

  def ZERO_TO_FOUR[_: P]: P[String] = P(CharIn("0-4")).!.log

  def ZERO_TO_FIVE[_: P]: P[String] = P(CharIn("0-5")).!.log

  def unreserved[_: P]: P[String] = P(ALPHA | DIGIT | "-" | "." | "_" | "~").!.log

  def reserved[_: P]: P[String] = P(`gen-delims` | `sub-delims`).!.log

  def `gen-delims`[_: P]: P[String] = P(":" | "/" | "?" | "#" | "[" | "]" | "@").!.log

  def `sub-delims`[_: P]: P[String] =
    P("!" | "$" | "&" | "'" | "(" | ")" | "*" | "+" | "," | ";" | "=").!.log

  def `pct-encoded`[_: P]: P[String] = P("%" ~ HEXDIG ~ HEXDIG).!.log

  //      *( "/" segment )
  def `path-abempty`[_: P]: P[String] = P("/" ~ segment).rep.!.log

  def segment[_: P]: P[String] = P(pchar).rep.!.log

  def pchar[_: P]: P[String] =
    P(
      unreserved |
      `pct-encoded` |
      `sub-delims` |
      ":" |
      "@"
    ).!.log

  //      "/" [ segment-nz *( "/" segment ) ]
  def `path-absolute`[_: P]: P[String] =
    P("/" ~ (CharIn("a-zA-Z").rep(1).? ~ ("/" ~ CharIn("a-zA-Z")).rep)).!.log

  //      segment-nz-nc *( "/" segment )
  def `path-noscheme`[_: P]: P[String] = P("").!.log

  //      segment-nz *( "/" segment )
  def `path-rootless`[_: P]: P[String] = P("").!.log

  //      0<pchar>
  def `path-empty`[_: P]: P[String] = P("").!.log

//  def path[_: P]: P[String] = P(CharsWhile(_ != '?')).!.log
  def path[_: P]: P[String] =
    P(`path-abempty` | `path-absolute` | `path-noscheme` | `path-rootless` | `path-empty`).!.log

  def query[_: P]: P[String] =
    P(
      pchar | "/" | "?"
    ).rep.!.log

  def fragment[_: P]: P[String] =
    P(
      pchar | "/" | "?"
    ).rep.!.log

  def `hier-part`[_: P]: P[Any] =
    P(
      ("//" ~ authority ~ `path-abempty`) |
      `path-absolute` |
      `path-rootless` |
      `path-empty`
    ).log

  def URI[_: P]: P[(String, Any, Option[String], Option[String])] =
    P(scheme ~ ":" ~ `hier-part` ~ ("?" ~ query).? ~ ("#" ~ fragment).?).log

  def pass[_: P]: P[String] = P(CharsWhile(_ != ':')).!.log

  def authUserInfo[_: P]: P[String] = P(CharsWhile(_ != '@')).!.log

  def `relative-part`[_: P]
    : P[Either[(Option[(Option[(String, String)], String, Option[String])], String), String]] =
    P(("//" ~ authority ~ `path-abempty`).map(Left.apply) | path.map(Right.apply))

  def `relative-ref`[_: P]: P[
    (Either[(Option[(Option[(String, String)], String, Option[String])], String), String],
     Option[String])
  ] = P(`relative-part` ~ query.?)

  def `absolute-URI`[_: P]: P[(String, Any, Option[String])] =
    P(scheme ~ ":" ~ `hier-part` ~ ("?" ~ query).?).log

//  val emptyURI = URI(None, None, None, None)

  def apply(input: String): Either[Error, URI] = {
    println(s"Parsing: $input")
    val result = parse(input, URI(_))

    result.fold(
      (_, _, _) => Left(new Error(s"Cannot parse $input as an URI")),
      (v, _) => {
        println(s"Parsed: $v")
        Right(new URI(v._1, None, "", v._3, v._4))
      }
    )
  }
}

/**
  * Uniform resource identifier (URI) reference.
  */
case class URI(scheme: String,
               authority: Option[Authority],
               path: String,
               query: Option[String],
               fragment: Option[String])

case class Authority(userinfo: Option[UserInfo], host: String, port: Option[String])

case class Password(password: String)

case class UserInfo(uiUsername: String, uiPassword: Option[Password])

case class PathPiece(pathPiece: String)

case class QueryParam()

case class QueryKey()
