name         := "SparkMe Project"
version      := "1.0"
organization := "pl.japila"

scalaVersion := "2.11.12"

libraryDependencies += "org.apache.spark" %% "spark-core" % "2.4.0"
resolvers += Resolver.mavenLocal