package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.config.Config
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.errors.Errors.{Error, HttpServerError}
import zio._
import zio.http.{Server, ServerConfig}
import zio.macros.accessible

import java.net.InetSocketAddress

@accessible
trait HttpServer {
  def start(): IO[Error, Unit]
}

case class HttpServerImpl(config: Config, apiRoutes: ApiRoutes) extends HttpServer {
  override def start(): IO[Error, Unit] =
    for {
      _   <- ZIO.logInfo("Starting http server...")
      api <- apiRoutes.routes()
      _ <- Server
        .serve(api)
        .provide(
          ServerConfig.live(
            ServerConfig(
              address = new InetSocketAddress(config.api.host, config.api.port)
            )
          ),
          Server.live
        )
        .mapError(e => HttpServerError(e))
    } yield ()
}

object HttpServerImpl {
  val layer: URLayer[Config & ApiRoutes, HttpServer] = ZLayer.fromFunction(HttpServerImpl(_, _))
}
