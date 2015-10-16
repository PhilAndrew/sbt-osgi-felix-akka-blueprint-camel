package hoqtec.impl

import org.osgi.framework.{BundleActivator, BundleContext}
import org.slf4j.LoggerFactory

/**
 * Created by jolz on 24/08/15.
 */
class OsgiBundle extends BundleActivator {

  val logger = LoggerFactory.getLogger(getClass)
  override def start(context: BundleContext): Unit = {
//  println("Start")
    logger.info("Our bundle started")

    //val container = new BlueprintContainerImpl(this.getClass.getClassLoader, resourcePaths)
  }


  override def stop(context: BundleContext): Unit = {

  }

}
