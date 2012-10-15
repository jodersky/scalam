name := "scalam"

version := "1.0"

scalaVersion := "2.9.2"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-core" % "0.4.1-seq"

libraryDependencies += "com.github.scala-incubator.io" %% "scala-io-file" % "0.4.1-seq"

libraryDependencies += "org.scalanlp" %% "breeze-math" % "0.1"

//onejar
seq(com.github.retronym.SbtOneJar.oneJarSettings: _*)

libraryDependencies += "commons-lang" % "commons-lang" % "2.6"

//libraryDependencies += "org.scala-lang" % "scala-swing" % "2.9.2"


