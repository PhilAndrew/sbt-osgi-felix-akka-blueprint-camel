package osgidemo.impl.util

import java.io.{ByteArrayOutputStream, InputStream, StringWriter}
import java.net.URL
import java.util.{Collections, Enumeration}
import java.util.zip.ZipInputStream

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

  private def getAllBundleResources(name: String) = {
    val bundles = bundle.getBundleContext.getBundles.toList
    val resources: Seq[java.net.URL] = bundles.flatMap((bundle: Bundle) => {
      val resource = bundle.getResource(name)
      if (resource==null) None else Some(resource)
    })
    resources
  }

  override def findResource(name: String): URL = {
    val resources = getAllBundleResources(name)
    if (resources.size > 0)
      resources.head
    else
      super.findResource(name)
  }

  override def findResources(name: String): Enumeration[URL] = {
    import collection.JavaConverters._
    val resources = getAllBundleResources(name)
    if (resources.size > 0)
      Collections.enumeration(resources.asJava)
    else
      super.findResources(name)
  }
}
