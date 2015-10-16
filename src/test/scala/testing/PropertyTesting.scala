package testing

import hoqtec.impl.OsgiBundle
import org.scalacheck.Properties

/**
 * Created by jolz on 25/08/15.
 */
object PropertyTesting extends Properties("Some props") {

  property("Something") = {
    new OsgiBundle().start(null)
    true
  }
}
