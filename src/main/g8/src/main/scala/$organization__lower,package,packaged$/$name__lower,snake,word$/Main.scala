package $organization;format="lower,package"$.$name;format="lower,snake,word"$

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.health.{HealthCheckerImpl, HealthRouteImpl}
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.version.VersionRouteImpl
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.swagger.SwaggerBuilderImpl
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.{ApiRoutesImpl, HttpServer, HttpServerImpl, ZioServer}
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.config.Configuration
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.metrics.{MetricsExporter, MetricsExporterImpl}
import zio.logging.backend.SLF4J
import zio.metrics.prometheus.Registry
import zio.metrics.prometheus.exporters.Exporters
import zio.{ULayer, ZIO, ZIOAppDefault}

object Main extends ZIOAppDefault {
  override val bootstrap: ULayer[Unit] =
    zio.Runtime.removeDefaultLoggers ++ SLF4J.slf4j

  def run: ZIO[Any, Any, Unit] = {
    val app = for {
      _ <- ZIO.logInfo("Service starting...")
      _ <- HttpServer.start() raceFirst MetricsExporter.run
    } yield ()

    app
      .provide(
        // config
        Configuration.layer,
        // metrics
        Registry.live,
        Exporters.live,
        // MetricsImpl.layer,
        MetricsExporterImpl.layer,
        // api
        SwaggerBuilderImpl.layer,
        HealthCheckerImpl.layer,
        HealthRouteImpl.layer,
        VersionRouteImpl.layer,
        ApiRoutesImpl.layer,
        ZioServer.configLayer,
        ZioServer.layer,
        HttpServerImpl.layer
      )
  }
}
