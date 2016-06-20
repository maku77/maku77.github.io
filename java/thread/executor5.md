---
title: Executor によるタスク処理 (5) ScheduledExecutorService によるタスクのスケジュール
created: 2014-07-12
---

`Executors` クラスが提供する、`Executor` インスタンスのファクトリのうち、下記の 2 つは、`ScheduledExecutorService` オブジェクトを生成します。

```java
Executors.newScheduledThreadPool()
Executors.newSingleThreadScheduledExecutor()
```

`ScheduledExecutorService` のインタフェースを使用すると、以下のようなスケジューリングされたタスク実行を行えます。

```java
// 指定された時間が経過してからタスク実行
schedule(Callable<V> callable, long delay, TimeUnit unit)

// 指定された時間ごとにタスク実行
scheduleAtFixedRate(Runnable command, long initialDelay, long period, TimeUnit unit)

// 指定された時間ごとにタスク実行（前タスクの終了時点からの時間で計測）
scheduleWithFixedDelay(Runnable command, long initialDelay, long delay, TimeUnit unit)
```

下記の例では、`ScheduledExecutorService` を使って、タスクを 3 秒後に実行するように制御しています。
結果として、`Future` オブジェクトの `get()` は 3 秒間待機することになります。

#### Main.java

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class MyTask implements Callable<String> {
    @Override
    public String call() {
        return "Hello";
    }
}

public class Main {
    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
        ScheduledFuture<String> future = scheduler.schedule(new MyTask(), 3, TimeUnit.SECONDS);
        scheduler.shutdown();
        try {
            System.out.println(future.get());  // "Hello", obtained in 3 seconds
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

