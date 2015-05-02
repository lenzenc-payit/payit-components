import sbt._
import Keys._

object ComponentsBuild extends Build {
  
  lazy val commonSettings = Seq(
    version := "1.0",
    scalaVersion := "2.11.4"
  )  
  
  lazy val parent = Project(
    id = "payit-parent", 
    base = file("."),
    aggregate = Seq(validation),
    settings = Project.defaultSettings ++ Seq(
      publishLocal := {},
      publish := {}      
    )
  )    
  
  lazy val validation = Project(id = "payit-validation", base = file("validation")).
    settings(commonSettings: _*).
    settings(
      // other settings
    )
          
}