name := "play-todos"

version := "1.0"

lazy val `play-todos` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc ,
  cache ,
  javaWs
)

// jongo dependency
libraryDependencies += "org.jongo" % "jongo" % "1.3.0"

// mongo dependency
libraryDependencies += "org.mongodb" % "mongo-java-driver" % "3.4.0"

// Json jackson
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.5.0"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-databind" % "2.5.0"

libraryDependencies += "com.fasterxml.jackson.core" % "jackson-annotations" % "2.5.0"

// escape strings
libraryDependencies += "org.apache.commons" % "commons-lang3" % "3.1"

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"  