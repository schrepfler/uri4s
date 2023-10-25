addSbtPlugin("com.dwijnand"      % "sbt-travisci"    % "1.2.0")
addSbtPlugin("com.lucidchart"    % "sbt-scalafmt"    % "1.16")
addSbtPlugin("com.github.sbt"  % "sbt-git"         % "2.0.1")
addSbtPlugin("de.heikoseeberger" % "sbt-header"      % "5.10.0")
addSbtPlugin("com.github.sbt" % "sbt-eclipse" % "6.0.0")
addSbtPlugin("com.timushev.sbt" % "sbt-updates" % "0.6.4")

libraryDependencies += "org.slf4j" % "slf4j-nop" % "2.0.9" // Needed by sbt-git
