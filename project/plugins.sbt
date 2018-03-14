//resolvers += Resolver.sonatypeRepo("releases")

addSbtPlugin("org.typelevel" % "sbt-catalysts" % "0.8.4")

// override for now until sbt-catalysts is updated
//addSbtPlugin("org.scala-js"        %  "sbt-scalajs"            % "0.6.19")
//addSbtPlugin("org.scoverage"       %  "sbt-scoverage"          % "1.5.0")
addSbtPlugin("org.tpolecat"        %  "tut-plugin"             % "0.6.3")
