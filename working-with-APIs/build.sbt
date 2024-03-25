name := "WebCrawler"

version := "1.9.7"

scalaVersion := "2.13.12"

Compile / doc / scalacOptions ++= Seq("-Vimplicits", "-deprecation", "-Ywarn-dead-code", "-Ywarn-value-discard", "-Ywarn-unused")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.9" % "test"
libraryDependencies += "com.lihaoyi" %% "requests" % "0.8.0"

val sprayGroup = "io.spray"
val sprayJsonVersion = "1.3.5"
libraryDependencies += sprayGroup %% "spray-json" % sprayJsonVersion

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.5.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.5.0"
libraryDependencies += "junit" % "junit" % "4.12" % Test

libraryDependencies += "io.circe" %% "circe-core" % "0.14.1"
libraryDependencies += "io.circe" %% "circe-generic" % "0.14.1"
libraryDependencies += "io.circe" %% "circe-parser" % "0.14.1"


libraryDependencies += "org.ccil.cowan.tagsoup" % "tagsoup" % "1.2.1"
libraryDependencies += "io.circe" %% "circe-generic-extras" % "0.14.1"
libraryDependencies += "org.scalaj" %% "scalaj-http" % "2.4.2"
libraryDependencies += "com.lihaoyi" %% "requests" % "0.6.6"


//libraryDependencies += "org.scala-lang.modules" %% "scala-sys-process" % "1.0.0"








