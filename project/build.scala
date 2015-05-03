import sbt._
import Keys._

object ComponentsBuild extends Build {

  lazy val _scalacOptions = Seq("-deprecation", "-unchecked", "-feature")

  lazy val commonSettings = Seq(
    version := "1.0",
    organization := "com.payit",
    scalaVersion := "2.11.4",
    resolvers ++= Seq(
      "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases",
      "Sonatype Releases"  at "http://oss.sonatype.org/content/repositories/releases",
      "spray repo" at "http://repo.spray.io/",
      "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"
    ),
    scalacOptions ++= _scalacOptions,
    scalacOptions in Test ++= Seq("-Yrangepos")
  )  
  
  lazy val parent = Project(
    id = "payit-parent", 
    base = file("."),
    aggregate = Seq(validation, specs),
    settings = commonSettings ++ Seq(
      name := "payit-validation",
      libraryDependencies ++= Seq(
      ),
      publishLocal := {},
      publish := {}
    )
  )    
  
  lazy val validation = Project(id = "payit-validation", base = file("validation"),
    settings = commonSettings ++ Seq(
      name := "payit-validation",
      libraryDependencies ++= Seq(
      )
    )
  ).dependsOn(specs)

  lazy val specs = Project(id = "payit-specs", base = file("specs"),
    settings = commonSettings ++ Seq(
      name := "payit-specs",
      libraryDependencies ++= Seq(
        "org.specs2" %% "specs2-core" % "3.6" % "compile"
      )
    )
  )
          
}