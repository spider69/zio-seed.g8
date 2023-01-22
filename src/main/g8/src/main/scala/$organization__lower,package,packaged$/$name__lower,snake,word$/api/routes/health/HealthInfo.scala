package $organization;format="lower,package"$.$name;format="lower,snake,word"$.api.routes.health

case class HealthInfo(
  healthy: Boolean
) {
  def isHealthy: Boolean = healthy

  def isNotHealthy: Boolean = !isHealthy
}
