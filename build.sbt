name := "Mashov-Notificator"

version := "1.0"

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

resolvers += "Sonatype Nexus Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

scalaVersion := "2.10.3"

retrieveManaged := false

initialCommands in console := "import scalaz._, Scalaz._, syntax.all._"

scalacOptions ++= Seq("-feature", "-deprecation")

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.3"

libraryDependencies += "io.spray" % "spray-client" % "1.2.0"

libraryDependencies += "io.spray" %% "spray-json" % "1.2.5"

libraryDependencies += "net.virtual-void" %% "json-lenses" % "0.5.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0" % "test"

libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.0-M4-SNAPSHOT"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.5"

libraryDependencies += "org.scalaz" %% "scalaz-typelevel" % "7.0.5"

//libraryDependencies += "org.scalaz" %% "scalaz-effect" % "7.0.5"

//libraryDependencies += "org.scalaz" %% "scalaz-concurrent" % "7.0.5"

fork := true
