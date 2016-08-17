package osgidemo.impl.util

import java.net.URL
import java.util.Enumeration

import akka.osgi.BundleDelegatingClassLoader
import org.osgi.framework.{Bundle, BundleContext}


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
    //println("1####" + name)
    super.findResource(name)
  }

  override def findResources(name: String): Enumeration[URL] = {
    /*import scala.collection.JavaConversions._
    val resources = bundle.getResources(name)
    if (resources!=null) {
      println("2####" + name)
      for (n <- resources) {
        println(n.toString)
      }
      println(resources)
    }*/
    super.findResources(name)
  }

}
