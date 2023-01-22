package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.health

import zio.mock.{Mock, Proxy}
import zio.{mock, IO, URLayer, ZIO, ZLayer}

object HealthCheckerMock extends Mock[HealthChecker] {
  object Check extends Effect[Unit, HealthInfo, HealthInfo]

  val compose: URLayer[mock.Proxy, HealthChecker] =
    ZLayer.fromZIO(
      ZIO
        .service[Proxy]
        .map { proxy =>
          new HealthChecker {
            override def check: IO[HealthInfo, HealthInfo] = proxy(Check)
          }
        }
    )
}
