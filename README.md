# Demo scala akka kamon.io metrics

Both `AkkaClusterDemoApp` and `AkkaClusterRandomDemoApp` contain scheduler and 2 routers (round-robin-pool).
The scheduler actor sends message to both routers every second.

With kamon usage the message can be send to router that configured as `allow-local-routees = off`.

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
|                             worker-counter  =>  0                                                |
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

