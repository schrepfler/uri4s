package net.sigmalab.scala.uri

object URI {

  def mkScheme(scheme: String)       = ???
  def mkUsername                     = ???
  def mkPassword(password: String)   = Password(_)
  def mkHost                         = ???
  def mkPathPiece(pathPiece: String) = PathPiece(_)
  def mkQueryKey                     = ???
  def mkQueryValue                   = ???
  def mkFragment                     = ???

  def apply(value: String): URI = {
    import fastparse.all._

    val scheme = P(CharsWhile(_ != "://"))

    val username = P(CharsWhile(_ != ':'))

    val password = P(CharsWhile(_ != ':'))

    val userinfo = P(username ~ password ~ "@")

    val host = P(CharsWhile(_ != ':'))

    val port = P(CharsWhile(_ != ':'))

    val authority = P(userinfo ~ host ~ port)

    val path = P(CharsWhile(_ != '?'))

    val query = P(CharsWhile(_ != End))

//    val fragment = P(CharsWhile(_ != End))

    val uri = P(scheme ~ authority ~ path ~ query)

    val parsed = uri.parse(value)


        val pass = Password("password")

        val authUserInfo = UserInfo("srdan", Some(pass))

        val auth = Authority(Some(authUserInfo), "blog.sigmalab.net", None)

    URI(Some("http://"), Some(auth), "pages/1", "hideComments=true", None)
//    URI(scheme, userinfo, path, query, fragment)
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