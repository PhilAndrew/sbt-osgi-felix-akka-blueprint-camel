package osgidemo.impl

import java.util.{Dictionary, Properties}

import akka.actor.{ActorRef, ActorSystem, ExtendedActorSystem, Props}
import akka.camel.ContextProvider
import akka.osgi.OsgiActorSystemFactory
import com.typesafe.config.{Config, ConfigFactory}
import org.apache.camel.blueprint.BlueprintCamelContext
import org.apache.camel.impl.DefaultCamelContext
import org.osgi.framework.{BundleContext, ServiceRegistration}
import osgidemo.impl.util.MyOsgiActorSystemFactory
//remove if not needed
import scala.collection.JavaConversions._

object AkkaCamelContextProvider {
  @volatile var contextProvider: DefaultCamelContext = null
}

final class AkkaCamelContextProvider extends ContextProvider {
  override def getContext(system: ExtendedActorSystem): DefaultCamelContext = {
    AkkaCamelContextProvider.contextProvider
  }
}

class AkkaBean {
  def getSystem = system

  var bundleContext: BundleContext = _
  var camelContext: BlueprintCamelContext = _

  def setBundleContext(bcontext: BundleContext) {
    this.bundleContext = bcontext
  }

  def setCamelContext(camelContext: BlueprintCamelContext) {
    this.camelContext = camelContext
  }

  private var system: Option[ActorSystem] = None
  private var registration: Option[ServiceRegistration[_]] = None

  private var akkaCamelBean: ActorRef = _

  def start(): Unit = {
    println("Start 1")
    AkkaCamelContextProvider.contextProvider = camelContext.asInstanceOf[DefaultCamelContext]

    println("Start 2")
    val sysConfig = getActorSystemConfiguration(bundleContext)
    println("Start 3")
    val actorFactory = MyOsgiActorSystemFactory(bundleContext, sysConfig)
    println("Start 4")
    system = Some(actorFactory.createActorSystem(Option(getActorSystemName(bundleContext))))
    println("Start 5")
    //system foreach (addLogServiceListener(context, _))
    system foreach (configure(bundleContext, _))
    println("Start 6 end")
  }

  def stop(): Unit = {
    stopWithContext(bundleContext)
  }

  def configure(context: BundleContext, system: ActorSystem): Unit = {
    // Registers this ActorSystem as a service so other blueprints can use this ActorSystem
    registerService(context, system)
  }

  def getActorSystemConfiguration(context: BundleContext): Config = ConfigFactory.empty
  def getActorSystemName(context: BundleContext): String = "RemoteSystem"

  // @todo Wire up blueprint stop
  def stopWithContext(context: BundleContext): Unit = {
    registration foreach (_.unregister())
    system foreach (_.shutdown())
  }

  def registerService(context: BundleContext, system: ActorSystem): Unit = {
    println("Register 1")
    registration.foreach(_.unregister()) //Cleanup
    val properties = new Properties()
    properties.put("name", system.name)
    registration = Some(context.registerService(classOf[ActorSystem].getName, system,
      properties.asInstanceOf[Dictionary[String, Any]]))
  }
}
