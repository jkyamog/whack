name := "Whack"

version := "1.0"

organization := "nz.net.catalyst"

scalaVersion := "2.9.1"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies ++= Seq(
	"com.typesafe.akka" % "akka-actor" % "2.0",
	"com.typesafe.akka" % "akka-agent" % "2.0",
	"org.perf4j" % "perf4j" % "0.9.13",
	"ch.qos.logback" % "logback-classic" % "0.9.28" % "runtime",
	"org.slf4j" % "slf4j-api" % "1.6.1"
)
