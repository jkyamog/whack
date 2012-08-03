package nz.net.catalyst.whack

import akka._
import akka.actor._
import akka.routing.RoundRobinRouter
import akka.actor.Scheduler
import java.util.concurrent.TimeUnit
import akka.util.duration._
import akka.pattern.gracefulStop
import akka.dispatch.Await
import akka.dispatch.Future

object HttpRunner {
	
  implicit val system = ActorSystem("app")	
  
  def main(args: Array[String]) {
  	// periodically print the stats
  	val statsPrinter = system.actorOf(Props[StatsPrinter]) 
  	system.scheduler.schedule(1000 milliseconds, 1000 milliseconds, statsPrinter, "print")
  	
  	val actors = for (i <- 1 to 10) yield system.actorOf(Props[HttpActor])
   
  	val router = system.actorOf(Props[HttpActor].withRouter(RoundRobinRouter(routees = actors)))
   
  	for (i <- 1 to 10000) {
  		router ! ("http://localhost/", httpResponseValidator)
  	}
  	
  	val actorFutures = actors map ( a => gracefulStop(a, 10 seconds))
  	
  	val future = Future.sequence(actorFutures).flatMap(_ => gracefulStop(statsPrinter, 10 seconds))
  	
  	future.onComplete(_ => system.shutdown)
  	
  }

}

object httpResponseValidator extends ResponseValidator {
  def validate(responseCode: Int, response: String): Boolean = {
    if (responseCode > 300) false
    else true
  }
}