package osgidemo.impl

import akka.actor.{ActorRef, PoisonPill, Props}

/*
Responsible for starting and stopping all actors
 */

class ActorStart {

  var akka: AkkaBean = _

  def setAkka(akka: AkkaBean) = {
    this.akka = akka
  }

  def start(): Unit = {
//    val supervisor = akka.getSystem.get.actorOf(Props[FreeNasSupervisorActor], name="freeNasSupervisor")
//    supervisor ! FreeNasSupervisorActor.Startup
  }

  def stop(): Unit = {
    // @todo Should stop some akka actors volumeConsumer ! PoisonPill
  }
}
