---
title: Executor によるタスク処理 (4) 既存の Thread から Future オブジェクトを取得する
created: 2014-07-12
---

`Future` オブジェクトは通常 `ExecutorService#submit()` に `Callable` オブジェクトを渡すことで取得しますが、従来の `Thread` オブジェクトを使って同様のことを行う手段も提供されています。
そのためには、`Callable` オブジェクトをラップした `FutureTask` オブジェクトを `Thread` に渡します。

```java
FutureTask<String> myTask = new FutureTask<>(new Callable() {...});
```

`FutureTask` は、`run()` メソッドを実装しているため、ラップした `Callable` オブジェクトを `Thread` に渡せるようになります。
`FutureTask` は `Future` オブジェクトとして扱うことができるため、`Thread` でのタスクの実行結果を外から取得できます。

#### Main.java

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

class MyCallable implements Callable<String> {
    @Override
    public String call() {
        return "Hello";
    }
}

public class Main {
    public static void main(String[] args) {
        FutureTask<String> myTask = new FutureTask<>(new MyCallable());
        new Thread(myTask).start();
        try {
            System.out.println(myTask.get());  //=> "Hello"
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
```

