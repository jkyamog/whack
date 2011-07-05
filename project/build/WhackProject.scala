import sbt._

class WhackProject(info: ProjectInfo) extends DefaultProject(info) with AkkaProject {
//  val httpclient = "org.apache.httpcomponents" % "httpclient" % "4.0.3"
  val jacksonasl = "org.codehaus.jackson" % "jackson-mapper-asl" % "1.6.4"
  val perf4j = "org.perf4j" % "perf4j" % "0.9.13"
  val akkaStm = akkaModule("stm")
  val akkaSlf4j = akkaModule("slf4j")
  lazy val logback = "ch.qos.logback" % "logback-classic" % "0.9.28" % "runtime"
}
