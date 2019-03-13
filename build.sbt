name := "ScalaSolutions"

version := "0.1"

scalaVersion := "2.12.0"


lazy val root = (project in file("."))
  .settings(
    name := "ScalaSolutions",
    libraryDependencies ++= List(
      "com.typesafe.slick" %% "slick" % "3.3.0",
      "com.h2database" % "h2" % "1.4.187",
      "org.slf4j" % "slf4j-nop" % "1.7.10",
      "org.scalatest" %% "scalatest" % "3.0.5"

    )
  )
updateOptions := updateOptions.value.withCachedResolution(true)
