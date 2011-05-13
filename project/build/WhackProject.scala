import sbt._

class WhackProject(info: ProjectInfo) extends DefaultProject(info) with AkkaProject {
//  val httpclient = "org.apache.httpcomponents" % "httpclient" % "4.0.3"
  val jacksonasl = "org.codehaus.jackson" % "jackson-mapper-asl" % "1.6.4"
  val akkaStm = akkaModule("stm")
  lazy val logback = "ch.qos.logback" % "logback-classic" % "0.9.28" % "runtime"
}
