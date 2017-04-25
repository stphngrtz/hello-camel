# Performance Tests

```
         -->         -->
[JMeter]     [Jetty]     [Queue]
         <--         <--
```

Setup:
- MacBook Pro (Late 2016, 2.9 GHz i7, 16 GB RAM) running on battery
- Camel was run from the IDE (IntelliJ IDEA CE) with default settings
- Queues were started as Docker containers with default settings
- Message size was about 1000 chars (see [performance-tests.jmx](performance-tests.jmx))

All tests have been run without clustering, optimization or configuration and with the default serializer.
You will look at "out of the box" numbers.

## RabbitMQ

### 1-1-1000
Threads: 1, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min  Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (with tracing)  1000     3    3       6    8    11   1    150  0.0%   270.3       60.3     338.4
Run #2 (with tracing)  1000     1    1       3    5     6   0     54  0.0%   497.5      111.3     622.9
Run #1 (w/o tracing)   1000     1    1       1    2     2   0     71  0.0%   840.3      187.5    1052.1
Run #2 (w/o tracing)   1000     1    1       1    2     2   0      8  0.0%   856.3      191.5    1071.9

Summary Report
                       Samples  Avg  Min  Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (with tracing)  1000     3    1    150  5.1       0.0%   270.3       60.3      338.5     228.5
Run #2 (with tracing)  1000     1    0     54  2.1       0.0%   497.5      111.3      622.9     229.0
Run #1 (w/o tracing)   1000     1    0     71  2.3       0.0%   840.3      187.5     1052.1     228.5
Run #2 (w/o tracing)   1000     1    0      8  0.5       0.0%   856.2      191.5     1071.9     229.0
```

### 10-1-1000
Threads: 10, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min  Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (w/o tracing)   10000    2    1       3    5    17   0    457  0.0%   3373.8      755.9   4223.9
Run #1 (w/o tracing)   10000    0    0       1    1    2    0     14  0.0%   7112.4     1597.5   8904.4

Summary Report
                       Samples  Avg  Min  Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (w/o tracing)   10000    2    0    457  6.6       0.0%   3373.8      755.9    4223.9    229.4
Run #2 (w/o tracing)   10000    0    0     14  0.8       0.0%   7112.4     1597.5    8904.4    230.0
```

### 100-1-1000
Threads: 100, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min  Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (w/o tracing)   100000   13   7       18   28   101  0    1383 0.0%   6722.2     1512.8   8415.9
Run #2 (w/o tracing)   Timeout Exceptions within RabbitMQ Channel Pool...

Summary Report
                       Samples  Avg  Min  Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (w/o tracing)   100000   13   0    1383 40.9      0.0%   6722.2     1512.8    8415.9    230.4
Run #2 (w/o tracing)   Timeout Exceptions within RabbitMQ Channel Pool...
```

## Kafka

### 1-1-1000
Threads: 1, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min  Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (w/o tracing)   1000     3    3       4    5    7    1    435  0.0%   287.0       95.7     359.3
Run #2 (w/o tracing)   1000     2    2       3    3    4    1     38  0.0%   474.8      159.1     594.5

Summary Report
                       Samples  Avg  Min  Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (w/o tracing)   1000     3    1    435  13.7      0.0%   287.0       95.7     359.3     341.3
Run #2 (w/o tracing)   1000     2    1    38    1.3      0.0%   474.8      159.1     594.5     343.0
```

### 10-1-1000
Threads: 10, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min  Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (w/o tracing)   10000    6    5       10   12   25   2    458  0.0%   1446.5     485.0    1811.0
Run #2 (w/o tracing)   10000    3    3        5    5    9   1     33  0.0%   2428.9     818.3    3040.9

Summary Report
                       Samples  Avg  Min  Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (w/o tracing)   10000    6    2    458  7.5       0.0%   1446.5     485.0     1811.0    343.3
Run #2 (w/o tracing)   10000    3    1     33  1.8       0.0%   2428.9     818.3     3040.9    345.0
```

### 100-1-1000
Threads: 100, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min  Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (w/o tracing)   100000   14   13      20   26   40   1    519  0.0%   6560.8     2212.6   8213.8
Run #2 (w/o tracing)   100000   10    9      11   12   19   0   1020  0.0%   8810.6     2985.6  11030.4

Summary Report
                       Samples  Avg  Min  Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (w/o tracing)   100000   14   1    519   9.6      0.0%   6560.8     2212.6    8213.8    345.3
Run #2 (w/o tracing)   100000   10   0   1020  31.7      0.0%   8810.6     2985.6   11030.4    347.0
```

## ActiveMQ

### 1-1-1000
Threads: 1, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min  Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (w/o tracing)   1000     15   9       12   14   20   5   5504  0.0%    64.1       14.3     80.2
Run #2 (w/o tracing)   1000      9   9       11   15   19   4     48  0.0%   105.7       23.6    132.4

Summary Report
                       Samples  Avg  Min  Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (w/o tracing)   1000     15   5   5504  173.7     0.0%    64.1      14.3       80.2      228.4
Run #2 (w/o tracing)   1000      9   4     48    2.9     0.0%   105.7       3.6      132.4      229.0
```

### 10-1-1000
Threads: 10, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min  Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (w/o tracing)   10000    85   80      92   97   119  52  5526  0.0%   115.8      25.9     144.9
Run #2 (w/o tracing)   10000    78   78      93   99   115   4   283  0.0%   125.8      28.1     156.7

Summary Report
                       Samples  Avg  Min  Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (w/o tracing)   10000    85   52  5526  157.2     0.0%   115.8      25.9      144.9     229.4
Run #2 (w/o tracing)   10000    78    4   283   14.3     0.0%   125.8      28.1      156.7     230.0
```

### 100-1-1000
Threads: 100, Ramp-up: 1, Loop: 1000

```
Aggregate Report
                       Samples  Avg  Median  90%  95%  99%  Min   Max  Error  Throughput r.KB/sec s.KB/sec
Run #1 (w/o tracing)   100000   824  816     886  910  974  655  6298  0.0%   121.1      27.2     151.6
Run #2 (w/o tracing)    58441   945  800     874 1355 2944   11 44913  0.1%   105.7      24.3     132.1

Summary Report
                       Samples  Avg  Min   Max  Std.Dev.  Error  Thoughput  r.KB/sec  s.KB/sec  Avg. Bytes
Run #1 (w/o tracing)   100000   824  655  6298  159.0     0.0%   121.1      27.2      151.6     230.4
Run #2 (w/o tracing)    58441   945   11 44913 1651.0     0.1%   105.7      24.3      132.1     235.3
```

Errors occured at around 158000 messages. It seems that camel was not able to produce the message to the ActiveMQ broker. When stopping camel, there were still some messages pending.
```
INFO org.apache.camel.impl.DefaultShutdownStrategy - Waiting as there are still 88 inflight and pending exchanges to complete, timeout in 300 seconds. Inflights per route: [route1 = 88]
```

## Comparison
- https://dzone.com/articles/exploring-message-brokers
- https://www.cloudkarafka.com/blog/2016-12-05-apachekafka-vs-rabbitmq.html
- https://yurisubach.com/2016/05/19/kafka-or-rabbitmq/
- https://www.quora.com/What-are-the-differences-between-Apache-Kafka-and-RabbitMQ
- https://www.galado.com/2017/01/rabbitmq-vs-kafka/
