package osgidemo.impl

import org.apache.camel.scala.dsl.builder.RouteBuilder

class CamelScala extends RouteBuilder {
  "direct:a" --> "mock:a"
  "direct:b" to "mock:b"
}