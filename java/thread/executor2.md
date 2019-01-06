---
title: "Executor によるタスク処理 (2) ExecutorService インタフェースで Executor を停止する"
date: "2014-07-11"
---

`Executors` クラスの `Executor` ファクトリメソッド（`Executors.newCachedThreadPool()` など）が返すオブジェクトは、`ExecutorService` インタフェースを実装しています。
`ExecutorService` インタフェースの `shutdown()`、`shutdownNow()` メソッドを使うと、サービスを停止して、それ以上タスクを受け付けないようになります。

- `ExecutorService#shutdown()` -- それ以上タスクを受け付けない。キューイングされたタスクは実行される。
- `ExecutorService#shutdownNow()` -- それ以上タスクを受け付けない。キューイングされたタスクを破棄する。

`shutdown()` 後に、さらにタスクを追加しようとすると、実行時例外 (RuntimeException) である `java.util.concurrent.RejectedExecutionException` が発生します。

```java
ExecutorService executor = Executors.newCachedThreadPool();
executor.execute(new MyTask("task1"));
executor.execute(new MyTask("task2"));
executor.shutdown();
executor.execute(new MyTask("task3"));  // RejectedExecutionException 発生！
executor.execute(new MyTask("task4"));
```

上記の例では、`shutdown()` 前にキューに積まれたタスク `task1`、`task2` は最後まで実行されますが、`shutdown()` 後にキューに積もうとしたタスク `task3`、`task4` は実行されません。
`ExecutorService` がすでにシャットダウンされているかを調べるメソッドも用意されています。

```java
if (executor.isShutdown()) {
    // シャットダウン済み
}
```

