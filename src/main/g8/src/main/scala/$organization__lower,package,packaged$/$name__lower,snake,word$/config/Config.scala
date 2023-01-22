package $organization;format="lower,package"$.$name;format="lower,snake,word"$.config

case class ApiConfig(
  host: String,
  port: Int
)

case class MetricsConfig(
  serverPort: Int
)

case class Config(
  api: ApiConfig,
  metrics: MetricsConfig
)
