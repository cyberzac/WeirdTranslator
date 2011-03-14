import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {

/*
  val camelRepo = "Camel Repo" at "https://repository.apache.org/content/repositories/snapshots/"
  val camel_version = "2.7-SNAPSHOT"
*/
  val camel_version = "2.6.0"

  val camelCore = "org.apache.camel" % "camel-core" % camel_version withSources ()
  val camelScala = "org.apache.camel" % "camel-scala" % camel_version withSources ()
  val camelXmpp = "org.apache.camel" % "camel-xmpp" % camel_version withSources ()

  val dispatch_version = "0.7.8"
  val dispatch =  "net.databinder" %% "dispatch-http" % dispatch_version withSources()
  val logback_version = "0.9.27"
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logback_version
  val slf4j_version = "1.6.1"
  val slf4j = "org.slf4j" % "slf4j-api" % slf4j_version

  val lift_version = "2.2"
  val lift_json = "net.liftweb" % "lift-json_2.8.0" % lift_version
  val lift_util = "net.liftweb" % "lift-util_2.8.0" % lift_version

  val specs = "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7" % "test"
}
