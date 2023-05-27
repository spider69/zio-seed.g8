package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes

import io.circe.Decoder
import zio.http.Response
import zio.{Task, ZIO}

import java.nio.charset.StandardCharsets

object ApiTestUtils {

  def getJsonBody[T](response: Response)(implicit decoder: Decoder[T]): Task[T] = {
    import io.circe.parser.decode

    for {
      bodyAsString <- getTextBody(response)
      decoded      <- ZIO.fromEither(decode[T](bodyAsString))
    } yield decoded
  }

  def getTextBody(response: Response): Task[String] = {
    for {
      body <- response.body.asArray
      bodyAsString = new String(body, StandardCharsets.UTF_8)
    } yield bodyAsString
  }

}
