import com.typesafe.sbt.osgi.OsgiKeys._
import osgifelix.OsgiFelixPlugin.autoImport._

defaultSingleProjectSettings

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.12",
  "org.slf4j" % "slf4j-simple" % "1.7.12",
  "org.slf4j" % "jcl-over-slf4j" % "1.7.12",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.12",
  // Blueprint
  // http://stackoverflow.com/questions/24938214/is-apache-aries-running-in-felix
  "org.apache.aries.blueprint" % "org.apache.aries.blueprint.cm" % "1.0.7",
  "org.apache.aries.proxy" % "org.apache.aries.proxy" % "1.0.1",
  "org.apache.camel" % "camel-core-osgi" % "2.16.0",
  // Now we want camel-blueprint
  "org.apache.camel" % "camel-blueprint" % "2.16.0",
  // Scala DSL for Camel
  "org.scala-lang.modules" %% "scala-xml" % "1.0.3", // Required by camel-scala
  "org.apache.camel" % "camel-scala" % "2.16.0",
  // Akka
  "com.typesafe.akka" %% "akka-osgi" % "2.4.0",
  "com.typesafe.akka" %% "akka-actor" % "2.4.0",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.0",
  "com.typesafe.akka" %% "akka-camel" % "2.4.0",
  "com.typesafe.akka" %% "akka-remote" % "2.4.0")

// Not required
//"org.apache.felix" % "org.apache.felix.configadmin" % "1.8.0",
//"org.ops4j.pax.logging" % "pax-logging-api" % "1.4",
//"org.ops4j.pax.logging" % "pax-logging-service" % "1.4" exclude("log4j", "log4j"),
//"org.apache.aries.blueprint" % "org.apache.aries.blueprint" % "1.1.0",
//"org.apache.aries.blueprint" % "org.apache.aries.blueprint.noosgi" % "1.1.1",
//"org.apache.aries" % "org.apache.aries.util" % "1.1.0",

lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.12.0"

libraryDependencies += scalacheck % Test

//osgiDependencies := packageReqs("org.apache.aries.blueprint", "org.slf4j", "akka", "akka.osgi")

osgiDependencies in Compile := bundleReqs("org.apache.camel.camel-core-osgi",
  "com.typesafe.akka.osgi",
  "com.typesafe.akka.camel",
  "org.apache.camel.camel-blueprint",
  "org.apache.camel.camel-scala")

// This starts the bundles with these symbolic names
osgiDependencies in run := bundleReqs("org.apache.aries.blueprint.core",
  "org.apache.aries.proxy",
  "org.apache.camel.camel-blueprint",
  // Not sure if the next two are needed
  "com.typesafe.akka.actor",
  "com.typesafe.akka.osgi",
  "com.typesafe.akka.camel",
  "com.typesafe.akka.slf4j", "com.typesafe.akka.remote")

// This appends these import packages to the end
// I need sun.misc, dont know about the rest
importPackage := Seq(
  "sun.misc",
  "*"
  //  "!aQute.bnd.annotation.*",
)

bundleActivator := Some("osgidemo.impl.Activator")

exportPackage += "osgidemo"

privatePackage := Seq("osgidemo.impl")

bundleVersion := "1.0.0"

