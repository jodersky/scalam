name := "scalam"

organization := "com.github.jodersky"

version := "1.0-SNAPSHOT"

scalaVersion := "2.10.0-RC1"

// dependency is temporatily explicit (until scala-io provide a scala 2.10 build)
libraryDependencies += "com.jsuereth" % "scala-arm_2.10.0-RC1" %  "1.2"

// dependencies currently removed (until scala-io and breeze-math provide a scala 2.10 build), the libraries are currently unmanaged and contained in lib
//libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.1-seq"

//libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.1-seq"

//libraryDependencies += "org.scalanlp" %% "breeze-math" % "0.1"

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
