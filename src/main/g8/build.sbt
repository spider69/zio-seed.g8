ThisBuild / version      := sys.env.getOrElse("RELEASE_VERSION", "0.0.0")
ThisBuild / organization := "$organization;format="lower,package"$"

scalaVersion          := "$scala_version$"
maxErrors             := 5
watchTriggeredMessage := Watch.clearScreenOnTrigger
scalacOptions ++= Seq(
  "-Xfatal-warnings",
  "-deprecation",
  "-unchecked",
  "-feature",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:postfixOps",
  "-Ymacro-annotations",
  "-Wunused",
  "-Wconf:cat=unused:info"
)

// format
scalafmtConfig := file(".scalafmt.conf")

// linter
scalafixConfig    := Some(file(".scalafix.conf"))
semanticdbEnabled := true
semanticdbVersion := scalafixSemanticdb.revision

// testing
testFrameworks += new TestFramework("zio.test.sbt.ZTestFramework")

// coverage
coverageExcludedPackages := ""

// packaging
enablePlugins(JavaAppPackaging)
Compile / packageDoc / mappings := Seq()
makeBatScripts                  := Seq()
executableScriptName            := "service"
bashScriptExtraDefines ++= Seq(
  """addJava "-Dconfig.file=\${app_home}/../conf/application.conf"""",
  """addJava "-Dlogback.configurationFile=\${app_home}/../conf/logback.xml""""
)

// build info
enablePlugins(BuildInfoPlugin)

lazy val `$name;format="norm"$` = 
  project
    .in(file("."))
    .settings(
      name := "$name$",
      buildInfoKeys := Seq[BuildInfoKey](
        name,
        version,
      ),
      buildInfoOptions += BuildInfoOption.BuildTime,
      buildInfoPackage := "$organization;format="lower,package"$.$name;format="lower,snake,word"$.build",
      libraryDependencies ++= Seq(
        Dependencies.Logging,
        Dependencies.Zio,
        Dependencies.ZioHttp,
        Dependencies.Tapir,
        Dependencies.ZioTest,
        Dependencies.ZioConfig,
        Dependencies.ZioMetrics,
        Dependencies.ApacheCodecs
      ).flatten
    )
