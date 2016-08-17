package osgidemo.impl

import java.net.URL
import java.util.Enumeration

import akka.osgi.{BundleDelegatingClassLoader, OsgiActorSystemFactory}
import org.osgi.framework.{Bundle, BundleContext}

import scala.annotation.tailrec


object MyBundleDelegatingClassLoader {
  /*
   * Create a bundle delegating ClassLoader for the bundle context's bundle
   */
  def apply(context: BundleContext): MyBundleDelegatingClassLoader = new MyBundleDelegatingClassLoader(context.getBundle, null)

  def apply(context: BundleContext, fallBackCLassLoader: Option[ClassLoader]): MyBundleDelegatingClassLoader =
    new MyBundleDelegatingClassLoader(context.getBundle, fallBackCLassLoader.orNull)
}


case class MyBundleDelegatingClassLoader(bundle: Bundle, fallBackClassLoader: ClassLoader) extends BundleDelegatingClassLoader(bundle, fallBackClassLoader) {

  override def findResource(name: String): URL = {
    println("####" + name)
    super.findResource(name)
  }

  override def findResources(name: String): Enumeration[URL] = {
    println("####" + name)
    super.findResources(name)
  }

}
