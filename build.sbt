import com.typesafe.sbt.osgi.OsgiKeys._
import osgifelix.OsgiFelixPlugin.autoImport._

defaultSingleProjectSettings

scalaVersion := "2.11.7"

/*
Manifest-Version: 1.0
Bnd-LastModified: 1444978986881
Build-Jdk: 1.8.0_51
Built-By: user
Bundle-Blueprint: OSGI-INF/blueprint/blueprint-example.xml
Bundle-ManifestVersion: 2
Bundle-Name: Example Blueprint
Bundle-SymbolicName: com.stackabuse.example.stackabuse-example-blueprint
Bundle-Version: 0.0.1
Created-By: Apache Maven Bundle Plugin
Export-Package: com.stackabuse.example;uses:="org.apache.camel";version=
 "0.0.1"
Import-Package: org.apache.camel;version="[2.12,3)",org.json,org.osgi.se
 rvice.blueprint;version="[1.0.0,2.0.0)"
Require-Capability: osgi.ee;filter:="(&(osgi.ee=JavaSE)(version=1.6))"
Tool: Bnd-3.0.0.201509101326
 */

OsgiKeys.additionalHeaders := Map(
  "Bundle-Blueprint" -> "OSGI-INF/blueprint/blueprint.xml"
)

libraryDependencies ++= Seq(
  "org.slf4j" % "slf4j-api" % "1.7.12",
  "org.slf4j" % "slf4j-simple" % "1.7.12",
  "org.slf4j" % "jcl-over-slf4j" % "1.7.12",
  "org.slf4j" % "log4j-over-slf4j" % "1.7.12",
  "com.typesafe.akka" %% "akka-osgi" % "2.4.0",
  // Blueprint
  // http://stackoverflow.com/questions/24938214/is-apache-aries-running-in-felix
  "org.apache.felix" % "org.apache.felix.configadmin" % "1.8.0",
  "org.ops4j.pax.logging" % "pax-logging-api" % "1.4",
  "org.ops4j.pax.logging" % "pax-logging-service" % "1.4" exclude("log4j", "log4j"),
  "org.apache.aries.blueprint" % "org.apache.aries.blueprint" % "1.1.0",
  "org.apache.aries.blueprint" % "org.apache.aries.blueprint.cm" % "1.0.7",
  //"org.apache.aries.blueprint" % "org.apache.aries.blueprint.noosgi" % "1.1.1",
  "org.apache.aries" % "org.apache.aries.util" % "1.1.0",
  "org.apache.aries.proxy" % "org.apache.aries.proxy" % "1.0.1",
  "org.apache.camel" % "camel-core-osgi" % "2.16.0")

// Bundle-Blueprint: OSGI-INF/blueprint/*.xml

lazy val scalacheck = "org.scalacheck" %% "scalacheck" % "1.12.0"

libraryDependencies += scalacheck % Test

//osgiDependencies := packageReqs("org.apache.aries.blueprint", "org.slf4j", "akka", "akka.osgi")

osgiDependencies in Compile := bundleReqs("org.apache.camel.camel-core-osgi", "org.apache.aries.blueprint.cm",
  "org.apache.aries.blueprint.core", "com.typesafe.akka.osgi", "org.apache.aries.blueprint")

// This appends these import packages to the end
// I need sun.misc, dont know about the rest
OsgiKeys.importPackage := Seq(
  "sun.misc",
  "!aQute.bnd.annotation.*",
  "*"
)

bundleActivator := Some("hoqtec.impl.Activator")

exportPackage += "hoqtec"

privatePackage := Seq("hoqtec.impl")

bundleVersion := "1.0.0"
