addSbtPlugin("com.dwijnand"      % "sbt-travisci"    % "1.1.1")
addSbtPlugin("com.lucidchart"    % "sbt-scalafmt"    % "1.14")
addSbtPlugin("com.typesafe.sbt"  % "sbt-git"         % "0.9.3")
addSbtPlugin("de.heikoseeberger" % "sbt-header"      % "3.0.2")
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "5.2.3")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.3.3")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "1.7.25" // Needed by sbt-git
