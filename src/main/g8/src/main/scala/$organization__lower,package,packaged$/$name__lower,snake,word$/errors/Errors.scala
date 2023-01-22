package $organization;format="lower,package"$.$name;format="lower,snake,word"$.errors

object Errors {
  sealed trait Error

  case class ConfigError(e: Throwable)     extends Error
  case class MetricsError(e: Throwable)    extends Error
  case class HttpServerError(e: Throwable) extends Error
}
