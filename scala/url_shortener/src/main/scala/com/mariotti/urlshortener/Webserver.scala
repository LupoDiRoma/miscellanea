package com.mariotti.urlshortener

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import akka.Done
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport._
import spray.json.DefaultJsonProtocol._
import com.mariotti.urlshortener.model.Mapping
import com.mariotti.urlshortener.dao.MapperReader
import com.mariotti.urlshortener.dao.MapperWriter
import com.mariotti.urlshortener.model.Stats
import org.json4s._
import org.json4s.native.JsonMethods._
import org.json4s.native.Serialization
import org.json4s.native.Serialization.{ read, write }
import akka.http.scaladsl.Http
import scala.io.StdIn

object WebServer {

  implicit val system = ActorSystem("my-system")
  implicit val materializer = ActorMaterializer()
  // needed for the future flatMap/onComplete in the end
  implicit val executionContext = system.dispatcher

  def main(args: Array[String]) {
    def getFullUrl(short: String): String = {
      Configuration.PROTOCOL + Configuration.HOST + ":" + Configuration.PORT + "/" + short
    }
    val route: Route =
      path("short") {
        get {
          parameters("url") { (url) =>
            var shortned = ""
            try {
              shortned = MapperWriter.put(url)
            } catch {
              case t: Throwable =>
                // TODO replace this with a logger
                t.printStackTrace()
            }
            complete(getFullUrl(shortned))
          }
        }
      } ~
        path("stats" / Segment) { (shorturl) =>
          get {
            var stats: Stats = null
            var ser: String = ""
            try {
              implicit val formats = Serialization.formats(NoTypeHints)
              stats = MapperReader.readStats(shorturl)
              ser = write(stats)
            } catch {
              case t: Throwable =>
                // TODO replace this with a logger
                t.printStackTrace()
            }
            complete(ser)
          }
        } ~
        path(Segment) { short =>
          get {
            var url = ""
            try {
              url = MapperReader.readMapping(short)
              if (url != null)
                MapperWriter.bump(short)
            } catch {
              case t: Throwable =>
                // TODO replace this with a logger
                t.printStackTrace()
            }
            complete(url)
          }
        }

    val bindingFuture = Http().bindAndHandle(route, Configuration.HOST, Configuration.PORT)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ => system.terminate()) // and shutdown when done
  }
}