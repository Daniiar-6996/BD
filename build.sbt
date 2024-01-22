name := """play-scala-slick-postgres-example"""

version := "2.6.x"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.12.4"

resolvers += "Atlassian 3rd-Party" at "https://maven.atlassian.com/3rdparty/"

libraryDependencies += guice
libraryDependencies += "com.typesafe.play" %% "play-slick" %  "3.0.2"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "3.0.2"

libraryDependencies += "postgresql" % "postgresql" % "9.4.1208-jdbc42-atlassian-hosted" //"9.1-901-1.jdbc4"

libraryDependencies += specs2 % Test
  

