package com.github.lashchenko.plainakka

import akka.actor.{Actor, ActorSystem, Props}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import kamon.Kamon

object PlainAkkaDemoApp extends App {
  Kamon.start()

  val cfg = ConfigFactory.load().getConfig("akka-plain")
  implicit val system = ActorSystem("KamonTestSystemPlainAkka", cfg)
  val router = system.actorOf(FromConfig.props(Props[TestWorker]), "workerRouter")
  val scheduler = system.actorOf(Props[TestScheduler], "workerScheduler")

  System.in.read()
  Kamon.shutdown()
}

object PlainAkkaMetrics {
  val workerCounter = Kamon.metrics.counter("worker-counter")
  val schedulerCounter = Kamon.metrics.counter("scheduler-counter")
}

class TestWorker extends Actor {
  override def receive: Receive = {
    case msg =>
      println("TestWorker " + msg)
      PlainAkkaMetrics.workerCounter.increment(1)
  }
}

class TestScheduler extends Actor {
  import context._

  import scala.concurrent.duration._
  system.scheduler.schedule(0.second, 1.second, self, "DoIt")
  override def receive: Receive = {
    case msg @ "DoIt" =>
      println("TestScheduler " + msg)
      PlainAkkaMetrics.schedulerCounter.increment(1)
      system.actorSelection("/user/workerRouter") ! msg
  }
}


// CRON + actor + router