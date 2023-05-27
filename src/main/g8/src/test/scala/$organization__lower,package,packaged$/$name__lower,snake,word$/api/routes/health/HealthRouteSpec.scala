package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.health

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.ApiTestUtils.getJsonBody
import io.circe.generic.auto._
import sttp.tapir.server.ziohttp.ZioHttpInterpreter
import zio.http.{Path, Request, Status, URL}
import zio.mock.Expectation._
import zio.test.Assertion.equalTo
import zio.test._

object HealthRouteSpec extends ZIOSpecDefault {

  def spec =
    suite("health route should")(
      test("be ok") {
        val req = Request.get(url = URL(Path.decode("health")))

        val healthInfo = HealthInfo(true)

        val app = HealthRoute
          .get
          .map(route => ZioHttpInterpreter().toHttp(List(route)))
          .flatMap(_.runZIO(req))
          .provide(
            HealthCheckerMock.Check(value(healthInfo)),
            HealthRouteImpl.layer
          )

        val result = for {
          response <- app
          statusCode = response.status
          body <- getJsonBody[HealthInfo](response)
        } yield (statusCode, body)

        assertZIO(result)(equalTo((Status.Ok, healthInfo)))
      },
      test("fail when not healthy") {
        val req = Request.get(url = URL(Path.decode("health")))

        val healthInfo = HealthInfo(false)
        val app = HealthRoute
          .get
          .map(route => ZioHttpInterpreter().toHttp(List(route)))
          .flatMap(_.runZIO(req))
          .provide(
            HealthCheckerMock.Check(failure(healthInfo)),
            HealthRouteImpl.layer
          )

        val result = for {
          response <- app
          statusCode = response.status
          body <- getJsonBody[HealthInfo](response)
        } yield (statusCode, body)

        assertZIO(result)(equalTo((Status.InternalServerError, healthInfo)))
      }
    )

}
