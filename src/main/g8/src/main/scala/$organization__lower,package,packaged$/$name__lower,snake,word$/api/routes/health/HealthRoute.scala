package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.health

import io.circe.generic.auto._
import sttp.model.StatusCode
import sttp.tapir.generic.auto._
import sttp.tapir.json.circe.jsonBody
import sttp.tapir.ztapir._
import zio.macros.accessible
import zio.{UIO, URLayer, ZIO, ZLayer}

@accessible
trait HealthRoute {
  def get: UIO[ZServerEndpoint[Any, Any]]
}

case class HealthRouteImpl(
  healthChecker: HealthChecker
) extends HealthRoute {

  override def get: UIO[ZServerEndpoint[Any, Any]] =
    ZIO.succeed(
      endpoint
        .get
        .in("health")
        .out(jsonBody[HealthInfo].description("OK. Health check is successful"))
        .errorOut(
          statusCode(StatusCode.InternalServerError)
            .description("ERROR. Server is not healthy")
            .and(jsonBody[HealthInfo].example(HealthInfo(false)))
        )
        .summary("Does health checking of service")
        .zServerLogic(_ => healthChecker.check)
    )
}

object HealthRouteImpl {
  val layer: URLayer[HealthChecker, HealthRoute] = ZLayer.fromFunction(HealthRouteImpl(_))
}
