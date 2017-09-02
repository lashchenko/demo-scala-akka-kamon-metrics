# Demo scala akka kamon.io metrics

Both `AkkaClusterDemoApp` and `AkkaClusterRandomDemoApp` contain scheduler and 2 routers (round-robin-pool).
The scheduler actor sends message to both routers every second.


Initially this project was created to illustrate an issue related to kamon-akka and akka cluster routers.
With kamon usage the message can be send to router that configured as `allow-local-routees = off` only router configured as `allow-local-routees = on` will not receive any messages. (https://github.com/kamon-io/kamon-akka/issues/17).
The issue can be reproduced with kamon-akka up to 0.6.6 (0.6.7) version - https://github.com/lashchenko/demo-scala-akka-kamon-metrics/tree/kamon-akka-0.6.6-akka-cluster-router-issue

The issue was fixed in kamon-akka 0.6.8 version.
See additional details at https://github.com/kamon-io/kamon-akka/pull/19



### Example

Run AkkaClusterDemoApp (seed node)
```
...
TestScheduler DoIt
TestScheduler DoIt
TestScheduler DoIt
TestScheduler DoIt
TestScheduler DoIt
TestScheduler DoIt
TestScheduler DoIt
TestScheduler DoIt
TestScheduler DoIt
TestScheduler DoIt
...
+--------------------------------------------------------------------------------------------------+
|                                                                                                  |
|                                         Counters                                                 |
|                                       -------------                                              |
|                             worker-counter  =>  5                                                |
|                 worker-remote-only-counter  =>  0                                                |
|                          scheduler-counter  =>  10                                               |
|                                                                                                  |
|                                                                                                  |
|                                        Histograms                                                |
|                                      --------------                                              |
|                                                                                                  |
|                                      MinMaxCounters                                              |
|                                    -----------------                                             |
|                                                                                                  |
|                                          Gauges                                                  |
|                                        ----------                                                |
|                                                                                                  |
+--------------------------------------------------------------------------------------------------+
```

Run AkkaClusterRandomDemoApp (one more node on random port)
```
...
TestWorkerRemoteOnly DoIt
TestWorkerRemoteOnly DoIt
TestWorker DoIt
TestWorkerRemoteOnly DoIt
TestWorkerRemoteOnly DoIt
TestWorker DoIt
TestWorkerRemoteOnly DoIt
TestWorkerRemoteOnly DoIt
TestWorker DoIt
TestWorkerRemoteOnly DoIt
TestWorkerRemoteOnly DoIt
TestWorker DoIt
TestWorkerRemoteOnly DoIt
TestWorkerRemoteOnly DoIt
TestWorker DoIt
...
+--------------------------------------------------------------------------------------------------+
|                                                                                                  |
|                                         Counters                                                 |
|                                       -------------                                              |
|                             worker-counter  =>  5                                                |
|                 worker-remote-only-counter  =>  10                                               |
|                          scheduler-counter  =>  0                                                |
|                                                                                                  |
|                                                                                                  |
|                                        Histograms                                                |
|                                      --------------                                              |
|                                                                                                  |
|                                      MinMaxCounters                                              |
|                                    -----------------                                             |
|                                                                                                  |
|                                          Gauges                                                  |
|                                        ----------                                                |
|                                                                                                  |
+--------------------------------------------------------------------------------------------------+
```

