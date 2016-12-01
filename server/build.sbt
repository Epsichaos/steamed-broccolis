name := "play-todos"

version := "1.0"

lazy val `play-todos` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq( jdbc ,
  cache ,
  ws   ,
  specs2 % Test ,
  "org.jongo" % "jongo" % "1.3.0",
  "org.mongodb" % "mongo-java-driver" % "3.2.2")


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  