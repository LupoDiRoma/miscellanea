lazy val commonSettings = Seq(
  organization := "com.mariotti.urlshortener",
  version := "0.1.0",
  scalaVersion := "2.11.8"
)

lazy val root = (project in file(".")).
  settings(commonSettings: _*).
  settings(
    name := "urlshortener",
    version := "1.0",
    resolvers += "Typesafe Repository" at " http://repo.akka.io/releases/",
    libraryDependencies += "com.typesafe.akka" %% "akka-actor" % "2.4.12",
    libraryDependencies += "com.typesafe.akka" %% "akka-remote" % "2.4.12",
    libraryDependencies += "com.typesafe.akka" %% "akka-http-core" % "2.4.11",
    libraryDependencies += "com.typesafe.akka" %% "akka-http-experimental" % "2.4.11", 
    libraryDependencies += "com.typesafe.akka" %% "akka-http-spray-json-experimental" % "2.4.7",
    libraryDependencies += "org.json4s" %% "json4s-native" % "3.5.0"
)
