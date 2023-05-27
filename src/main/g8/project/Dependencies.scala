import sbt._

object Dependencies {
  object Versions {
    // logging
    lazy val ZioLoggingVersion             = "2.1.13"
    lazy val LogbackVersion                = "1.4.7"
    lazy val LogbackLogstashEncoderVersion = "7.3"

    // zio
    lazy val ZioVersion       = "2.0.13"
    lazy val ZioConfigVersion = "3.0.7"

    // test
    lazy val ZioMockVersion = "1.0.0-RC11"

    // metrics
    lazy val ZioMetricsVersion = "2.0.1"

    // http
    lazy val ZHttpVersion = "0.0.5"
    lazy val TapirVersion = "1.4.0"

    // utils
    lazy val CodecsVersion = "1.15"
  }

  import Versions._

  lazy val Logging = Seq(
    "dev.zio"             %% "zio-logging-slf4j"        % ZioLoggingVersion,
    "ch.qos.logback"       % "logback-classic"          % LogbackVersion,
    "net.logstash.logback" % "logstash-logback-encoder" % LogbackLogstashEncoderVersion
  )

  lazy val ZioMetrics = Seq(
    "dev.zio" %% "zio-metrics-prometheus" % ZioMetricsVersion
  )

  lazy val Zio = Seq(
    "dev.zio" %% "zio"         % ZioVersion,
    "dev.zio" %% "zio-streams" % ZioVersion,
    "dev.zio" %% "zio-macros"  % ZioVersion
  )

  lazy val ZioTest = Seq(
    "dev.zio" %% "zio-test"          % ZioVersion     % "test",
    "dev.zio" %% "zio-test-sbt"      % ZioVersion     % "test",
    "dev.zio" %% "zio-test-magnolia" % ZioVersion     % "test",
    "dev.zio" %% "zio-mock"          % ZioMockVersion % "test"
  )

  lazy val ZioConfig = Seq(
    "dev.zio" %% "zio-config"          % ZioConfigVersion,
    "dev.zio" %% "zio-config-magnolia" % ZioConfigVersion,
    "dev.zio" %% "zio-config-typesafe" % ZioConfigVersion
  )

  lazy val Tapir = Seq(
    "com.softwaremill.sttp.tapir" %% "tapir-zio-http-server"   % TapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-json-circe"        % TapirVersion,
    "com.softwaremill.sttp.tapir" %% "tapir-swagger-ui-bundle" % TapirVersion
  )

  lazy val ZioHttp = Seq(
    "dev.zio" %% "zio-http" % ZHttpVersion
  )

  lazy val ApacheCodecs = Seq(
    "commons-codec" % "commons-codec" % CodecsVersion
  )
}
