name := "Mashov-Notificator"

version := "1.0"

scalaVersion := "2.10.3"

initialCommands in console := "import scalaz._, Scalaz._, syntax.all._"

scalacOptions += "-feature"

retrieveManaged := false

resolvers += "spray repo" at "http://repo.spray.io"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.2.3"

libraryDependencies += "io.spray" % "spray-client" % "1.2.0"

libraryDependencies += "io.spray" %% "spray-json" % "1.2.5"

libraryDependencies += "net.virtual-void" %% "json-lenses" % "0.5.4"

libraryDependencies += "org.scalatest" %% "scalatest" % "2.0" % "test"

libraryDependencies += "org.scalafx" %% "scalafx" % "1.0.0-M7"

unmanagedJars in Compile += Attributed.blank(file(System.getenv("JAVA_HOME") + "/jre/lib/jfxrt.jar"))

libraryDependencies += "net.databinder.dispatch" %% "dispatch-core" % "0.11.0"

libraryDependencies += "org.scalaz" %% "scalaz-core" % "7.0.5"

