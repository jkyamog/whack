name := "Whack"

version := "1.0"

organization := "nz.net.catalyst"

scalaVersion := "2.9.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"se.scalablesolutions.akka" % "akka-actor" % "1.3.1",
	"se.scalablesolutions.akka" % "akka-stm" % "1.3.1",
	"org.perf4j" % "perf4j" % "0.9.13",
	"org.codehaus.jackson" % "jackson-mapper-asl" % "1.6.4",
	"ch.qos.logback" % "logback-classic" % "0.9.28" % "runtime"
)