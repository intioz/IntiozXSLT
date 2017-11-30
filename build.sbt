name := "IntiozXSLT"

organization := "intioz"

version := "0.0.1-Beta"

scalaVersion := "2.12.3"

 
enablePlugins(JavaAppPackaging)



libraryDependencies ++= {
  val akkaV       = "2.5.6"
  val akkaHttpV   = "10.0.10"
  val scalaTestV  = "3.0.1"
  Seq(
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "com.typesafe.akka" %% "akka-stream" % akkaV,
    "com.typesafe.akka" %% "akka-http" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-spray-json" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-testkit" % akkaHttpV,
    "com.typesafe.akka" %% "akka-http-xml" % akkaHttpV, 
    "com.typesafe.akka" %% "akka-slf4j" % "2.5.6"
  )
}


