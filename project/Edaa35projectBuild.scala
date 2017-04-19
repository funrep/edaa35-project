import sbt._
import sbt.Keys._

object Edaa35projectBuild extends Build {

  lazy val edaa35project = Project(
    id = "edaa35-project",
    base = file("."),
    settings = Project.defaultSettings ++ Seq(
      name := "edaa35-project",
      organization := "d0x10",
      version := "0.1-SNAPSHOT",
      scalaVersion := "2.12.2"
      // add other settings here
    )
  )
}
