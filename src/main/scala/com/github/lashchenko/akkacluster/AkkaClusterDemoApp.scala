package com.github.lashchenko.akkacluster

import akka.actor.{Actor, ActorSystem, Props}
import akka.cluster.singleton.{ClusterSingletonManager, ClusterSingletonManagerSettings}
import akka.routing.FromConfig
import com.typesafe.config.ConfigFactory
import kamon.Kamon
import kamon.metric.instrument.Counter

object AkkaClusterDemoApp extends App {
  Kamon.start()

  val cfg = ConfigFactory.load().getConfig("akka-cluster-seed")
  implicit val system = ActorSystem("KamonTestClusterSystem", cfg)
  val router = system.actorOf(FromConfig.props(Props[TestWorker]), "workerRouter")
  val routerRemoteOnly = system.actorOf(FromConfig.props(Props[TestWorkerRemoteOnly]), "workerRouterRemoteOnly")
//    val scheduler = system.actorOf(Props[TestScheduler], "workerScheduler")

  system.actorOf(
    ClusterSingletonManager.props(
      singletonProps = Props(classOf[TestScheduler]),
      terminationMessage = "BB",
      settings = ClusterSingletonManagerSettings(system)),
    name = "workerScheduler")

  System.in.read()
  Kamon.shutdown()
}

object AkkaClusterRandomDemoApp extends App {

  Kamon.start()

  val cfg = ConfigFactory.load().getConfig("akka-cluster-other")
  implicit val system = ActorSystem("KamonTestClusterSystem", cfg)
  val router = system.actorOf(FromConfig.props(Props[TestWorker]), "workerRouter")
  val routerRemoteOnly = system.actorOf(FromConfig.props(Props[TestWorkerRemoteOnly]), "workerRouterRemoteOnly")
//  val scheduler = system.actorOf(Props[TestScheduler], "workerScheduler")

  system.actorOf(
    ClusterSingletonManager.props(
      singletonProps = Props(classOf[TestScheduler]),
      terminationMessage = "BB",
      settings = ClusterSingletonManagerSettings(system)),
    name = "workerScheduler")

  System.in.read()
  Kamon.shutdown()
}

object AkkaClusterMetrics {
  val workerCounter: Counter = Kamon.metrics.counter("worker-counter")
  val workerRemoveOnlyCounter: Counter = Kamon.metrics.counter("worker-remote-only-counter")
  val schedulerCounter: Counter = Kamon.metrics.counter("scheduler-counter")
}

class TestWorker extends Actor {

  println(">>> TestWorker <<<")

  override def receive: Receive = {
    case msg =>
      println("TestWorker " + msg)
      AkkaClusterMetrics.workerCounter.increment(1)
  }
}

class TestWorkerRemoteOnly extends Actor {

  println(">>> TestWorkerRemoteOnly <<<")

  override def receive: Receive = {
    case msg =>
      println("TestWorkerRemoteOnly " + msg)
      AkkaClusterMetrics.workerRemoveOnlyCounter.increment(1)
  }
}

class TestScheduler extends Actor {

  println(">>> TestScheduler <<<")
  import context._

  import scala.concurrent.duration._
  system.scheduler.schedule(0.second, 1.second, self, "DoIt")
  override def receive: Receive = {
    case msg @ "DoIt" =>
      println("TestScheduler " + msg)
      AkkaClusterMetrics.schedulerCounter.increment(1)
      system.actorSelection("/user/workerRouterRemoteOnly") ! msg
      system.actorSelection("/user/workerRouter") ! msg
  }
}

