package nz.net.catalyst.whack

import scala.io.Source

import akka._
import akka.actor._

import java.net._
import java.io._
import java.util.Map

import org.perf4j.StopWatch


class HttpActor extends Actor with ActorLogging {

  def receive = {
    case (urlStr: String, respValidator: ResponseValidator) => {
      
      val stopWatch = new StopWatch();

      val url = new URL(urlStr)
      
      val conn = url.openConnection.asInstanceOf[HttpURLConnection]
      
      val response = Source.createBufferedSource(conn.getInputStream, 
                                                 Source.DefaultBufSize, 
                                                 null, 
                                                 () => conn.getInputStream.close())
      val responseStr = response.mkString

      log.debug(responseStr)

      stopWatch.stop

      if (respValidator.validate(conn.getResponseCode, responseStr))
        Stats.addTime(stopWatch.getElapsedTime) // only count validated responses
      else
        log.error("invalid response")

    }

    case _ => log.warning("received unknown message")
  }
}



