package osgidemo.impl

import akka.actor.Actor.Receive
import akka.actor.{Actor, Props, ActorSystem}
import org.osgi.framework.BundleContext
import akka.osgi.ActorSystemActivator

case class SomeMessage()

class SomeActor extends Actor {
  override def receive: Receive = {
    case _ => println("received message in Actor")
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