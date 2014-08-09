name := "Mashov-Notificator"

version := "1.0"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "tpolecat" at "http://dl.bintray.com/tpolecat/maven"

scalaVersion := "2.10.4"

retrieveManaged := false

initialCommands in console := "import scalaz._, Scalaz._, syntax.all._"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.3.0"

libraryDependencies += "io.spray" % "spray-client" % "1.3.1"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0" % "test"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.0-R4"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.6"

libraryDependencies += "org.scalaz" %% "scalaz-typelevel" % "7.0.6"

libraryDependencies += "org.scalaz" %% "scalaz-effect" % "7.0.6"

libraryDependencies += "org.scalaz" %% "scalaz-concurrent" % "7.0.6"

libraryDependencies += "org.tpolecat" %% "atto-core" % "0.2.1"

libraryDependencies += "io.argonaut" %% "argonaut" % "6.0.4"

incOptions := incOptions.value.withNameHashing(true)

fork := true
