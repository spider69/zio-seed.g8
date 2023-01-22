package $organization;format="lower,package"$.$name;format="lower,snake,word"$.metrics

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.config.Config
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.errors.Errors.{Error, MetricsError}
import io.prometheus.client.exporter.HTTPServer
import zio._
import zio.macros.accessible
import zio.metrics.prometheus._
import zio.metrics.prometheus.exporters._
import zio.metrics.prometheus.helpers._

@accessible
trait MetricsExporter {
  def run: IO[Error, HTTPServer]
}

case class MetricsExporterImpl(
  config: Config,
  registry: Registry,
  exporters: Exporters
) extends MetricsExporter {

  override def run: IO[Error, HTTPServer] = {
    val metricsServerRunner = for {
      cfg <- ZIO.succeed(config.metrics)
      r   <- getCurrentRegistry()
      _   <- initializeDefaultExports(r).mapError(e => MetricsError(e))
      hs  <- zio.metrics.prometheus.helpers.http(r, cfg.serverPort).mapError(e => MetricsError(e))
    } yield hs

    metricsServerRunner
      .provide(ZLayer.succeed(registry), ZLayer.succeed(exporters))
  }
}

object MetricsExporterImpl {
  val layer: URLayer[Config & Registry & Exporters, MetricsExporter] =
    ZLayer.fromFunction(MetricsExporterImpl(_, _, _))
}
