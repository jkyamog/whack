package nz.net.catalyst.whack

import akka._
import akka.actor._
import akka.routing.RoundRobinRouter
import akka.actor.Scheduler
import java.util.concurrent.TimeUnit
import akka.util.duration._

object HttpRunner {
	
  implicit val system = ActorSystem("app")	
  
  def main(args: Array[String]) {
     // periodically print the stats
	 val statsPrinter = system.actorOf(Props[StatsPrinter]) 
     system.scheduler.schedule(1000 milliseconds, 1000 milliseconds, statsPrinter, "print")
     
     val loadbalancer = system.actorOf(Props[HttpActor].withRouter(RoundRobinRouter(nrOfInstances = 20)))
     
     for (i <- 1 to 10000) {
        loadbalancer ! ("http://localhost", httpResponseValidator)
     }
  }

}

object httpResponseValidator extends ResponseValidator {
  def validate(responseCode: Int, response: String): Boolean = {
    if (responseCode > 300) false
    else true
  }
}