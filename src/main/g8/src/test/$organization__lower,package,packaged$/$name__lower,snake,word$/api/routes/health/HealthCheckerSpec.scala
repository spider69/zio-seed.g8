package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.health

import zio.test.Assertion.equalTo
import zio.test.{assertZIO, ZIOSpecDefault}

object HealthCheckerSpec extends ZIOSpecDefault {

  def spec =
    suite("health checker should")(
      test("check health correctly") {
        val healthInfo = HealthInfo(true)
        val app = HealthChecker
          .check
          .provide(
            HealthCheckerImpl.layer
          )
        assertZIO(app)(equalTo(healthInfo))
      }
    )

}
