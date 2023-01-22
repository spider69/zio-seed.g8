package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.health.HealthRoute
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.version.VersionRoute
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.swagger.SwaggerBuilder
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.errors.Errors.Error
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio._
import zio.http.HttpApp
import zio.macros.accessible

@accessible
trait ApiRoutes {
  def routes(): IO[Error, HttpApp[Any, Throwable]]
}

case class ApiRoutesImpl(
  swaggerBuilder: SwaggerBuilder,
  healthRoute: HealthRoute,
  versionRoute: VersionRoute
) extends ApiRoutes {
  override def routes(): IO[Error, HttpApp[Any, Throwable]] =
    for {
      health  <- healthRoute.get
      version <- versionRoute.get
      routes = List(health, version)
      swagger <- swaggerBuilder.build(routes.map(_.endpoint))
      api = ZioHttpInterpreter().toHttp(routes ++ swagger)
    } yield api
}

object ApiRoutesImpl {
  val layer: URLayer[SwaggerBuilder & HealthRoute & VersionRoute, ApiRoutes] = ZLayer.fromFunction(ApiRoutesImpl(_, _, _))
}
