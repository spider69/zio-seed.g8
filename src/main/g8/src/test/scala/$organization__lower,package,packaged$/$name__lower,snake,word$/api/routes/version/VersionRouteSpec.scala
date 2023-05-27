package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.version

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.ApiTestUtils.getJsonBody
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio.http.{Path, Request, Status, URL}
import zio.test.Assertion.equalTo
import zio.test._

object VersionRouteSpec extends ZIOSpecDefault {

  def spec =
    suite("version route should")(
      test("return version") {
        val req = Request.get(url = URL(Path.decode("version")))

        val app = VersionRoute
          .get
          .map(route => ZioHttpInterpreter().toHttp(List(route)))
          .flatMap(_.runZIO(req))
          .provide(VersionRouteImpl.layer)

        val result = for {
          response <- app
          statusCode = response.status
          body <- getJsonBody[String](response)
          isEmpty = body.isEmpty
        } yield (statusCode, isEmpty)

        assertZIO(result)(equalTo(Status.Ok, false))
      }
    )

}
