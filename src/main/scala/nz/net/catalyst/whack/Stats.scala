package nz.net.catalyst.whack

import akka.stm._

object Stats {

  val ctr = Ref(0)
  val totalTime = Ref(0l)

  def incrCtr {
    atomic {
      ctr alter (_ + 1)
    }
  }

  def addTime(time: Long) {
    atomic {
      totalTime alter (_ + time)
      incrCtr
    }
  }

  def printStats {

    val currentCtr = atomic {
      ctr.get
    }
    val currentTotalTime = atomic {
      totalTime.get
    }

    if (currentCtr > 0) {
      println("total ms: " + currentTotalTime)
      println("counted: " + currentCtr)
      println("ave ms: " + (currentTotalTime :Double)/(currentCtr :Double))
    }
  }

}
