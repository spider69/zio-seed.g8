package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.swagger

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.build.BuildInfo
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.config.Config
import sttp.tapir.AnyEndpoint
import sttp.tapir.server.ServerEndpoint
import sttp.tapir.swagger.SwaggerUIOptions
import sttp.tapir.swagger.bundle.SwaggerInterpreter
import zio.macros.accessible
import zio.{IO, Task, URLayer, ZIO, ZLayer}
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.errors.Errors.Error

@accessible
trait SwaggerBuilder {
  def build(endpoints: List[AnyEndpoint]): IO[Error, List[ServerEndpoint[Any, Task]]]
}

case class SwaggerBuilderImpl(config: Config) extends SwaggerBuilder {
  override def build(endpoints: List[AnyEndpoint]): IO[Error, List[ServerEndpoint[Any, Task]]] =
    ZIO.succeed(
      SwaggerInterpreter(
        swaggerUIOptions = SwaggerUIOptions(
          pathPrefix = List("swagger"),
          yamlName = "api.yaml",
          contextPath = Nil,
          useRelativePaths = true
        )
      ).fromEndpoints[Task](
        endpoints = endpoints,
        title = "$name$ api",
        version = BuildInfo.version
      )
    )
}

object SwaggerBuilderImpl {
  val layer: URLayer[Config, SwaggerBuilder] = ZLayer.fromFunction(SwaggerBuilderImpl(_))
}
