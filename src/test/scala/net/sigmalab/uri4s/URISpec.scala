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

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class URISpec extends AnyWordSpec with Matchers {

  "apply" should {
    "handle simple URL" in {
      val uri = URI("https://auser:apass@github.com:80/schrepfler/scala-uri?queryKey=queryValue#id")
    }

    "handle ftp URI" in {
      val uri = URI("ftp://ftp.is.co.za/rfc/rfc1808.txt")
    }

    "handle http URI" in {
      val uri = URI("http://www.ietf.org/rfc/rfc2396.txt")
    }

    "handle ldap URI" in {
      val uri = URI("ldap://[2001:db8::7]/c=GB?objectClass?one")
    }

    "handle mailto URI" in {
      val uri = URI("mailto:John.Doe@example.com")
    }

    "handle nttp URI" in {
      val uri = URI("news:comp.infosystems.www.servers.unix")
    }

    "handle tel URI" in {
      val uri = URI("tel:+1-816-555-1212")
    }

    "handle telnet URI" in {
      val uri = URI("telnet://192.0.2.16:80/")
    }

    "handle urn URI" in {
      val uri = URI("urn:oasis:names:specification:docbook:dtd:xml:4.1.2")
    }

  }

}
