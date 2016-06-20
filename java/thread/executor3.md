---
title: Executor によるタスク処理 (3) Future オブジェクトによりタスクの実行結果を取得する
created: 2014-07-15
---

`Future` オブジェクトを使用すると、`ExecutorService` によって別スレッドで実行されたタスクの結果を取得したり、外からそのタスクを終了したりすることができます。
`Future` オブジェクトを取得するには、`execute()` メソッドの代わりに、`submit()` メソッドを使用してタスクを実行します。

```
<T> Future<T> ExecutorService#submit(Callable<T> task)
```

`Thread#start()` や `Executor#execute()` では、タスクとして `Runnable` オブジェクトを指定しますが、`ExecutorService#submit()` では、通常 `Callable` オブジェクトを渡します。
`Callable` インタフェースは `Runnable` インタフェースの `run()` メソッドに似た、`call()` メソッドを定義しており、タスクの実行結果を任意の型で返すことができるようになっています。

```java
public interface Callable<V> {
    V call() throws Exception;
}
```

例えば、以下のように `Integer` オブジェクトを返すタスクを定義した場合、

```java
class AdderTask implements Callable<Integer> { ... }
```

`Integer` を取得する `Future` オブジェクトを下記のように取得できます。

```java
Future<Integer> future1 = executorService.submit(new AdderTask(1, 2));
```

`Future` オブジェクトを使用すると、下記のような操作を外から実行できます。

- `V get()` -- タスクの終了を待機し、結果を取得する。
- `V get(long timeout, TimeUnit unit)` -- 同上。ただし、タイムアウト時間を経過すると `TimeoutException` を発生して抜ける。
- `boolean cancel(boolean interrupt)` -- タスクの実行をキャンセル。すでに実行されている場合は実行しちゃうかもしれない。

