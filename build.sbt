name := "msggen"
version := "1.0"
scalaVersion := "2.12.6"
val scalacompatversion = "2.12"
val spec2version = "4.2.0"
libraryDependencies ++= Seq(
"com.google.code.gson" % "gson" % "2.8.5",
"org.apache.poi" % "poi" % "3.15-beta2",
"org.apache.poi" % "poi-ooxml" % "3.15-beta2",
"org.apache.poi" % "poi-ooxml-schemas" % "3.15-beta2",
"com.typesafe.akka" % "akka-actor_2.12" % "[2.5.16,)",
"org.scala-lang" % "scala-library" % "2.12.6",
"org.apache.logging.log4j" % "log4j-api-scala_2.12" % "11.0",
"org.apache.logging.log4j" % "log4j-api" % "2.11.0",
"org.apache.logging.log4j" % "log4j-core" % "2.11.0",
"junit" %  "junit" % "4.12" % "test",
"org.scalatest" %  "scalatest_2.12" % "3.0.5" % "test",
"org.specs2" %  "specs2-core_2.12" % spec2version % "test",
"org.specs2" %  "specs2-junit_2.12" % spec2version % "test",
"org.scalamock" %  "scalamock_2.12" % "4.4.0" % "test",
"org.scalacheck" %  "scalacheck_2.12" % "1.14.1" % "test",
  "com.typesafe" % "config" % "1.3.2",
  "org.apache.kafka" % "kafka-clients" % "1.0.0")