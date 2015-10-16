package hoqtec.impl

import hoqtec.HelloWorldService

/**
  * Created by user on 16/10/2015.
  */
class HelloWorldServiceImpl extends HelloWorldService {
  def hello(): Unit = {
    println("hello1")
  }

  def startup(): Unit = {
    println("startup1")
  }
}
