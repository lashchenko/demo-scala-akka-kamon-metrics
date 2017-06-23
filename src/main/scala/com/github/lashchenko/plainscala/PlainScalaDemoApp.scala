package com.github.lashchenko.plainscala

import kamon.Kamon

object PlainScalaDemoApp extends App {
  Kamon.start()

  val someHistogram = Kamon.metrics.histogram("some-histogram")
  val someCounter = Kamon.metrics.counter("some-counter")

  someHistogram.record(42)
  someHistogram.record(50)
  someCounter.increment()
  someCounter.increment(10)

  System.in.read()
  Kamon.shutdown()


  /*

  [INFO] [06/22/2017 12:18:45.560] [kamon-akka.actor.default-dispatcher-3] [akka://kamon/user/kamon-log-reporter]
+--------------------------------------------------------------------------------------------------+
|                                                                                                  |
|                                         Counters                                                 |
|                                       -------------                                              |
|                               some-counter  =>  11                                               |
|                                                                                                  |
|                                                                                                  |
|                                        Histograms                                                |
|                                      --------------                                              |
|  some-histogram                                                                                  |
|    Min: 42           50th Perc: 42             90th Perc: 50             95th Perc: 50           |
|                      99th Perc: 50           99.9th Perc: 50                   Max: 50           |
|                                                                                                  |
|                                                                                                  |
|                                      MinMaxCounters                                              |
|                                    -----------------                                             |
|                                                                                                  |
|                                          Gauges                                                  |
|                                        ----------                                                |
|                                                                                                  |
+--------------------------------------------------------------------------------------------------+

  */
}
