package $organization;format="lower,package"$.$name;format="lower,snake,word"$.config

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.errors.Errors.ConfigError
import zio.config._
import zio.config.magnolia.descriptor
import zio.config.typesafe.TypesafeConfigSource
import zio.{Layer, ZLayer}
import $organization;format="lower,package"$.$name;format="lower,snake,word"$.errors.Errors.Error

object Configuration {
  val layer: Layer[Error, Config] =
    ZLayer {
      read {
        descriptor[Config].from(
          TypesafeConfigSource.fromResourcePath
        )
      }
    }.mapError(e => ConfigError(e))
}
