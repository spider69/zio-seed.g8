enablePlugins(ScriptedPlugin)

ThisBuild / scalaVersion := "2.12.17"

name := "zio-template-project"

addCommandAlias("test", "g8Test")

scriptedLaunchOpts ++= List(
  "-Xms1024m", 
  "-Xmx1024m",
  "-XX:ReservedCodeCacheSize=128m",
  "-Xss2m",
  "-Dfile.encoding=UTF-8"
)
