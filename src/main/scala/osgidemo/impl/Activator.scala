package osgidemo.impl

import akka.osgi.ActorSystemActivator
import akka.actor.{Actor, Props, ActorSystem}
import akka.sample.osgi.internal.Table
import akka.sample.osgi.service.DiningHakkersServiceImpl
import akka.sample.osgi.api.DiningHakkersService
import akka.event.{ LogSource, Logging }
import org.osgi.framework.{ ServiceRegistration, BundleContext }

case class SomeMessage()

class SomeActor extends Actor {
  override def receive: Receive = {
    case _ => {
      println("received message in Actor")
    }
  }
}

class Activator extends ActorSystemActivator {

  def configure(context: BundleContext, system: ActorSystem) {
    // optionally register the ActorSystem in the OSGi Service Registry
    registerService(context, system)

    val someActor = system.actorOf(Props[SomeActor], name = "someName")
    someActor ! SomeMessage
  }

}