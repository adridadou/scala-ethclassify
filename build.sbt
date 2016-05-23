name := """sbt-ethclassify"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.7"

resolvers += "EthereumJ Repository from Bintray" at "http://dl.bintray.com/ethereum/maven"

resolvers += "sonatype" at "https://oss.sonatype.org/content/repositories/releases"

resolvers += "Local Maven Repository" at "file://"+Path.userHome.absolutePath+"/.m2/repository"

libraryDependencies ++= Seq(
  "org.ipfs" % "api" % "0.0.1-SNAPSHOT",
  "org.mapdb" % "mapdb" % "2.0-beta13",
  "org.adridadou" % "eth-contract-api" % "0.3",
  filters,
  specs2 % Test
)


fork in run := true
