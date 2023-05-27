package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.errors.Errors.Error
import zio._
import zio.http.Server
import zio.macros.accessible

import java.net.InetSocketAddress

@accessible
trait HttpServer {
  def start(): IO[Error, Unit]
}

case class HttpServerImpl(server: Server, apiRoutes: ApiRoutes) extends HttpServer {
  override def start(): IO[Error, Unit] =
    for {
      _   <- ZIO.logInfo("Starting http server...")
      api <- apiRoutes.routes()
      _ <- server.install(api)
    } yield ()
}

object HttpServerImpl {
  val layer: URLayer[Server & ApiRoutes, HttpServer] = ZLayer.fromFunction(HttpServerImpl(_, _))
}
