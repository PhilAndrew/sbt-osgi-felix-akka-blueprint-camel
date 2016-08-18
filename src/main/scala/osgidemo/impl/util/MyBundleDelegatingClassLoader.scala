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

  override def findResource(name: String): URL = {
    val bundles = bundle.getBundleContext.getBundles.toList
    val resources: Seq[java.net.URL] = bundles.flatMap((bundle: Bundle) => {
      val resource = bundle.getResource(name)
      if (resource==null) None else Some(resource)
    })
    if (resources.size > 0)
      resources.head
    else
      super.findResource(name)
  }

  def inputStreamToString(inputStream: java.io.InputStream): String = {
    val result = new ByteArrayOutputStream()
    val buffer = Array.ofDim[Byte](1024)
    var length: Int = 0
    while ({
      length = inputStream.read(buffer)
      length != (-1)
    }) {
      result.write(buffer, 0, length)
    }
    result.toString("UTF-8")
  }

  override def findResources(name: String): Enumeration[URL] = {
    import collection.JavaConverters._
    val bundles = bundle.getBundleContext.getBundles.toList
    val resources: Seq[java.net.URL] = bundles.flatMap((bundle: Bundle) => {
      val resource = bundle.getResource(name)
      if (resource==null) None else Some(resource)
    })
    if (resources.size > 0)
      Collections.enumeration(resources.asJava)
    else
      super.findResources(name)
  }

}
