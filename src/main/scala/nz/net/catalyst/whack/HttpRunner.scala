package nz.net.catalyst.whack

import akka._
import akka.actor._
import routing.Routing._
import akka.actor.Scheduler
import java.util.concurrent.TimeUnit
import akka.routing.CyclicIterator

object HttpRunner {
  
  def main(args: Array[String]) {
     // periodically print the stats
     Scheduler.schedule(() => Stats.printStats, 1000, 1000, TimeUnit.MILLISECONDS)
     
     val actors = for(i <- 1 to 20) yield Actor.actorOf[HttpActor].start()
     val loadBalancer = loadBalancerActor(new CyclicIterator(actors)) 
     
     for (i <- 1 to 1000000) {
        loadBalancer ! ("http://localhost", httpResponseValidator)
     }
  }

}

object httpResponseValidator extends ResponseValidator {
  def validate(responseCode: Int, response: String): Boolean = {
    if (responseCode > 300) false
    else true
  }
}