addCommandAlias(
  "styleCheck",
  "scalafmtSbtCheck; scalafmtCheckAll; scalafixAll --check"
)
addCommandAlias(
  "styleFix",
  "scalafixAll; scalafmtSbt; scalafmtAll"
)
addCommandAlias(
  "updatesCheck",
  "reload plugins; dependencyUpdates; reload return; dependencyUpdates"
)

onLoadMessage +=
  s"""|
      |     List of defined aliases
      | styleCheck    scalafmt + scalafix check
      | styleFix      scalafix + scalafmt
      | updatesCheck  print plugins and libs deps updates
  """.stripMargin