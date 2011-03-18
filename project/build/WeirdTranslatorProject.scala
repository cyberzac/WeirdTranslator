import sbt._

class Project(info: ProjectInfo) extends DefaultProject(info) {

/*
  val camelRepo = "Camel Repo" at "https://repository.apache.org/content/repositories/snapshots/"
  val camel_version = "2.7-SNAPSHOT"
*/
  val camel_version = "2.6.0"

  val camelCore = "org.apache.camel" % "camel-core" % camel_version withSources () withJavadoc()
  val camelScala = "org.apache.camel" % "camel-scala" % camel_version withSources () withJavadoc()
  val camelXmpp = "org.apache.camel" % "camel-xmpp" % camel_version withSources () withJavadoc()
  val camelMina = "org.apache.camel" % "camel-mina" % camel_version withSources () withJavadoc()



  val dispatch_version = "0.7.8"
  val dispatch =  "net.databinder" %% "dispatch-http" % dispatch_version withSources()
  val logback_version = "0.9.27"
  val logbackClassic = "ch.qos.logback" % "logback-classic" % logback_version
  val slf4j_version = "1.6.1"
  val slf4j = "org.slf4j" % "slf4j-api" % slf4j_version

  val lift_version = "2.2"
  val lift_json = "net.liftweb" % "lift-json_2.8.1" % lift_version
  val lift_util = "net.liftweb" % "lift-util_2.8.1" % lift_version

  val junit = "junit" % "junit" % "4.8.2" % "test"  withJavadoc()
  val mockito = "org.mockito" % "mockito-all" % "1.8.5" % "test" withSources()
  val specs = "org.scala-tools.testing" % "specs_2.8.1" % "1.6.7" % "test"
  val scalaTest = "org.scalatest" % "scalatest" % "1.3" % "test" withSources()
  val camelTest = "org.apache.camel" % "camel-test" % camel_version % "test"  withSources() withJavadoc()

}
