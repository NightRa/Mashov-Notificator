name := "Mashov-Notificator"

version := "1.0"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

resolvers += "tpolecat" at "http://dl.bintray.com/tpolecat/maven"

scalaVersion := "2.10.3"

retrieveManaged := false

initialCommands in console := "import scalaz._, Scalaz._, syntax.all._"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.3"

libraryDependencies += "io.spray" % "spray-client" % "1.2.0"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0" % "test"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.0-M4-SNAPSHOT"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.6"

libraryDependencies += "org.scalaz" %% "scalaz-typelevel" % "7.0.6"

libraryDependencies += "org.scalaz" %% "scalaz-effect" % "7.0.6"

libraryDependencies += "org.tpolecat" %% "atto" % "0.1"

libraryDependencies += "io.argonaut" %% "argonaut" % "6.0.3"

//libraryDependencies += "org.scalaz" %% "scalaz-concurrent" % "7.0.6"

fork := true
