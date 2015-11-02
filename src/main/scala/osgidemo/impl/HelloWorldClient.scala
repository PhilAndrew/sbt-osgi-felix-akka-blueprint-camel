package osgidemo.impl

import osgidemo.HelloWorldService

/**
  * Created by user on 16/10/2015.
  */
class HelloWorldClient {
  var service: HelloWorldService = null

  def startup():Unit = { println("Client startup") }

  def getHelloWorldService(): HelloWorldService = {
    println("getHelloWorldService")
    service
  }

  def setHelloWorldService(s: HelloWorldService): Unit = {
    println("getHelloWorldService2")
    service = s; }
}
