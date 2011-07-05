package nz.net.catalyst.whack

import scala.io.Source

import akka._
import actor.Actor
import event.EventHandler

import java.net._
import java.io._
import java.util.Map

import org.perf4j.StopWatch


class HttpActor extends Actor {

  def receive = {
    case (urlStr: String, respValidator: ResponseValidator) => {
      
      val stopWatch = new StopWatch();

      val url = new URL(urlStr)
      val in = url.openStream
      val response = Source.createBufferedSource(in, 
                                                 Source.DefaultBufSize, 
                                                 null, 
                                                 () => in.close())
      val responseStr = response.mkString

      EventHandler.debug(this, responseStr)

      stopWatch.stop

      if (respValidator.validate(responseStr))
        Stats.addTime(stopWatch.getElapsedTime) // only count validated responses
      else
        EventHandler.error(this, "invalid response")

    }

    case _ => EventHandler.warning(this, "received unknown message")
  }
}



