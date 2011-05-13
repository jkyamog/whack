package nz.net.catalyst.whack

import scala.io.Source

import akka._
import actor.Actor
import event.EventHandler

import java.net._
import java.io._
import java.util.Map


class HttpActor extends Actor {

  def receive = {
    case (urlStr: String, respValidator: ResponseValidator) => {

      val t1 = System.currentTimeMillis

      val url = new URL(urlStr)
      val in = url.openStream
      val response = Source.createBufferedSource(in, 
                                                 Source.DefaultBufSize, 
                                                 null, 
                                                 () => in.close())
      val responseStr = response.mkString

      EventHandler.debug(this, responseStr)

      val t2 = System.currentTimeMillis
      val totalTime = t2 - t1

      if (respValidator.validate(responseStr))
        Stats.addTime(totalTime) // only count validated responses
      else
        EventHandler.error(this, "invalid response")

    }

    case _ => EventHandler.warning(this, "received unknown message")
  }
}



