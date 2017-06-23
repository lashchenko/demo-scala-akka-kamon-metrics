import sbt.Keys._

resolvers ++= Seq(
  "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots",
  "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases",
  "Kamon Repository Snapshots" at "http://snapshots.kamon.io"
)

val kamonVersion = "0.6.6"
val akkaVersion = "2.4.14"

val aspectjDeps = Seq(
  "org.aspectj" % "aspectjweaver" % "1.8.9"
)

val kamonDeps = Seq(
  "io.kamon" %% "kamon-autoweave" % "0.6.5",
  "io.kamon" %% "kamon-core" % kamonVersion,
  "io.kamon" %% "kamon-akka-remote-2.4" % kamonVersion,
  "io.kamon" %% "kamon-akka-2.4" % kamonVersion,
  "io.kamon" %% "kamon-log-reporter" % kamonVersion
)

val akkaDeps = Seq(
  "com.typesafe.akka" %% "akka-cluster" % akkaVersion,
  "com.typesafe.akka" %% "akka-cluster-tools" % akkaVersion
)

val allDeps = kamonDeps ++ akkaDeps ++ aspectjDeps

val project = Project(
  id = "demo-scala-akka-kamon-metrics",
  base = file("."),
  settings = Seq(
    name := "demo-scala-akka-kamon-metrics",
    scalaVersion := "2.11.11",

    libraryDependencies ++= allDeps,

    parallelExecution in Test := false
  )
)
