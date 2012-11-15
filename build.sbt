name := "scalam"

organization := "com.github.jodersky"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.0-RC2"

libraryDependencies += "com.github.scala-incubator.io" % "scala-io-core_2.10.0-RC1" % "0.4.1"

libraryDependencies += "com.github.scala-incubator.io" % "scala-io-file_2.10.0-RC1" % "0.4.1"

// dependency currently refers to a local build (until breeze-math provides a scala 2.10 build)
libraryDependencies += "org.scalanlp" %% "breeze-math" % "0.2-SNAPSHOT"

scalacOptions ++= Seq("-deprecation","-feature")

scalacOptions in Compile in doc ++= Seq("-diagrams", "-implicits", "-doc-title", "scalam")

scalacOptions in Compile in doc <++= baseDirectory.map {
  (bd: File) => Seq(
    "-sourcepath",
    bd.getAbsolutePath,
    "-doc-source-url",
    "https://github.com/jodersky/scalam/tree/master€{FILE_PATH}.scala"
  )
}
