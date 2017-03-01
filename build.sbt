name := """breakfast-app"""

version := "1.0-SNAPSHOT"

resolvers += Resolver.bintrayRepo("hmrc", "releases")

lazy val root = (project in file(".")).enablePlugins(PlayScala)
routesGenerator := StaticRoutesGenerator

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  jdbc,
  cache,
  ws,
  "uk.gov.hmrc" %% "http-verbs" % "6.3.0",
  "org.scalatestplus.play" %% "scalatestplus-play" % "1.5.1" % Test
)

