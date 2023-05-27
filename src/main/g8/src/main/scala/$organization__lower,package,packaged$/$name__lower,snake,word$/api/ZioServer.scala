package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api

import $organization;format="lower,package"$.$name;format="lower,snake,word"$.config.Config
import zio.http.Server
import zio.http.Server.{Config => ServerConfig}
import zio.{RLayer, ZLayer}

object ZioServer {

  val configLayer: RLayer[Config, ServerConfig] = ZLayer.fromFunction { (config: Config) =>
    ServerConfig
      .default
      .binding(config.api.host, config.api.port)
  }

  val layer: ZLayer[ServerConfig, Throwable, Server] = Server.live
}
