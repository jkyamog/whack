package nz.net.catalyst.whack

import akka.agent.Agent
import akka.actor._
import scala.concurrent.stm._

object Stats {
	
	import HttpRunner.system

  val ctr = Agent(0)
  val totalTime = Agent(0l)

  def incrCtr {
    atomic { txn =>
      ctr send (_ + 1)
    }
  }

  def addTime(time: Long) {
    atomic { txn =>
      totalTime send (_ + time)
      incrCtr
    }
  }

  def printStats {

    val currentCtr = atomic { txn =>
      ctr.get
    }
    val currentTotalTime = atomic { txn =>
      totalTime.get
    }

    if (currentCtr > 0) {
      println("total ms: " + currentTotalTime)
      println("counted: " + currentCtr)
      println("ave ms: " + (currentTotalTime :Double)/(currentCtr :Double))
    }
  }

}

class StatsPrinter extends Actor with ActorLogging {

  def receive = {
    case "print" => {
      Stats.printStats
    }

    case _ => log.warning("received unknown message")
  }
  
  override def postStop = Stats.printStats
	
}
