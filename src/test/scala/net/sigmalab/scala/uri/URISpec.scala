package net.sigmalab.scala.uri

import org.scalatest.{FlatSpec, Matchers}

class URISpec extends FlatSpec with Matchers {

  "A URI" should "" in {

    val uri = URI("https://auser:apass@github.com/schrepfler/scala-uri?queryKey=queryValue")

//    val password = Password("password")
//
//    val authUserInfo = UserInfo("srdan", Some(password))
//
//    val authority = Authority(Some(authUserInfo), "blog.sigmalab.net", None)

    println(uri)

//    val uri = URI(Some("http://"), Some(authority), "pages/1", "hideComments=true", None)


  }

}
