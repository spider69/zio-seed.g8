package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.health

import zio.macros.accessible
import zio.{IO, ULayer, ZIO, ZLayer}

@accessible
trait HealthChecker {
  def check: IO[HealthInfo, HealthInfo]
}

case class HealthCheckerImpl() extends HealthChecker {
  override def check: IO[HealthInfo, HealthInfo] =
    for {
      _ <- ZIO.logTrace("Health check requested")
      health = HealthInfo(true)
      _ <- ZIO.when(health.isNotHealthy)(ZIO.fail(health))
    } yield health
}

object HealthCheckerImpl {
  val layer: ULayer[HealthChecker] = ZLayer.fromFunction(() => HealthCheckerImpl())
}
