name := "Spark Assignment 1"

version := "1.0"

scalaVersion := "2.13.12"

Compile / doc / scalacOptions ++= Seq("-Vimplicits", "-deprecation", "-Ywarn-dead-code", "-Ywarn-value-discard", "-Ywarn-unused")

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.2" % "test"
libraryDependencies += "com.lihaoyi" %% "requests" % "0.8.0"


val sprayGroup = "io.spray"
val sprayJsonVersion = "1.3.5"
libraryDependencies ++= List("spray-json") map { c => sprayGroup %% c % sprayJsonVersion }

libraryDependencies +="org.apache.spark" %% "spark-core" % "3.5.0"
libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.5.0"









/*
//name := "Spark Assignment 1"

//version := "1.0"

//scalaVersion := "2.13.4"
ThisBuild / version := "0.1.0-SNAPSHOT"
ThisBuild / scalaVersion := "2.13.12"
lazy val root = (project in file("."))
  .settings(
    name := "Ass1",
    idePackagePrefix := Some("Spark Assignment 1")
  )

Compile / doc / scalacOptions ++= Seq("-Vimplicits", "-deprecation", "-Ywarn-dead-code", "-Ywarn-value-discard", "-Ywarn-unused")

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.5.0"
libraryDependencies += "org.apache.spark" %% "sparl-core" % "3.5.0"


val sprayGroup = "io.spray"
val sprayJsonVersion = "1.3.5"
libraryDependencies ++= List("spray-json") map { c => sprayGroup %% c % sprayJsonVersion }

*/
/*
ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.12"

lazy val root = (project in file("."))
  .settings(
    name := "Ass1",
    idePackagePrefix := Some("Spark Assignment 1")
  )
 */