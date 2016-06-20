---
title: Executor によるタスク処理 (1) Executor インタフェースを使用する
created: 2014-07-11
---

Executor の基本
----

Java 1.5 (Tiger) で、`java.util.concurrent.Executor` インタフェースが追加されました。
`Executor` は下記のような単純なインタフェースを提供し、与えられたタスクをキューイングし、何らかのタイミングで実行していきます。

```java
void execute(Runnable command)
```

`Executor` はこのインタフェースにより、渡されたタスクが内部的にどのように並列処理されるかを隠蔽します。
例えば、複数のスレッドプールを保持する `Executor` インスタンスを使えば、渡したタスクは複数のスレッドで処理されていきます。
一般的によく使いそうな `Executor` の実装は、`Executors` クラスのファクトリメソッドにより生成することができます（ファクトリクラスは `Executor` ではなく `Executors` であることに注意）。たとえば、次のようなファクトリメソッドが用意されており、同時実行するスレッドの数や、タスクの実行タイミングなどの要件により、どのタイプの `Executor` オブジェクトを生成するかを決めます。

- ExecutorService オブジェクトの生成:
  - `Executors.newSingleThreadExecutor()` -- 1 つのスレッドでタスクを 1 つずつ処理する。
  - `Executors.newFixedThreadPool(int size)` -- 指定された数のスレッドでタスクを並列処理する。
  - `Executors.newCachedThreadPool()` -- キュー内のタスクを処理するのに必要な数だけスレッドを生成する。スレッドは再利用される。
- ScheduledExecutorService オブジェクトの生成:
  - `Executors.newScheduledThreadPool()` -- 指定したスケジュールでタスクを処理する（一定時間ごとに処理するなど）。
  - `Executors.newSingleThreadScheduledExecutor()` -- 同上。

前の 3 つは、`ExecutorService` インタフェース、後ろの 2 つは `ScheduledExecutorService` インタフェースを実装したオブジェクトを返します。
`ExecutorService` は `Executor` のサブインタフェース、`ScheduledExecutorService` は `ExecutorService` のサブインタフェースです。
インタフェースの継承関係を簡単に描くと下記のような感じです。

```
Executor <== ExecutorService <== ScheduledExecutorService
```

Executor の使用例
----

下記のサンプルでは、スレッドを内部で 2 つ保持する `Executor` を作成し (`Executors.newFixedThreadPool(2)`)、3 つのタスクを同時に実行要求 (`execute`) しています。

#### Main.java

```java
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class MyTask implements Runnable {
    private final String name;
    public MyTask(String name) {
        this.name = name;
    }
    @Override
    public void run() {
        for (int i = 0; i < 3; ++i) {
            System.out.println(name);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                System.out.println(this.name + " is interrupted");
                return;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        Executor executor = Executors.newFixedThreadPool(2);
        executor.execute(new MyTask("task1"));
        executor.execute(new MyTask("task2"));
        executor.execute(new MyTask("task3"));
    }
}
```

最初にタスクキューに `MyTask` が 3 つ全て入りますが、内部のスレッドは 2 つだけなので、以下のように、最初に 2 つのタスクが同時実行されます。スレッドに空きができ次第、3 つ目のタスクが開始されます。

#### 実行結果

```
task1
task2
task1
task2
task1
task2
task3
task3
task3
```

