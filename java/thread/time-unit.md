---
title: TimeUnit によって単位時間を明確にする
created: 2014-06-28
---

Java 1.5 (Tiger) からは、単位時間を明確に指定できる `TimeUnit` クラスが導入されています。
`Thread.sleep()` の代わりに、`TimeUnit.sleep()` を使用すれば、スリープ時間を明確にできます。

```java
TimeUnit.MILLISECONDS.sleep(3);  // = Thread.sleep(3)
TimeUnit.SECONDS.sleep(3);       // = Thread.sleep(3*1000);
TimeUnit.MINUTES.sleep(3);       // = Thread.sleep(3*60*1000);
```

`TimeUnit` というプレフィックスが邪魔なときは、`static` インポートして使うのもよいでしょう。

```java
import static java.util.concurrent.TimeUnit.SECONDS;

try {
    SECONDS.sleep(3);
} catch (InterruptedException e) { /* IGNORE */ }
```

`TimeUnit` には、単位時間の変換メソッドも用意されています。

```java
long seconds = TimeUnit.HOURS.toSeconds(1);  // 1時間は何秒か？(=1*60*60)
long millis = TimeUnit.HOURS.toMillis(5);    // 5時間は何ミリ秒か？(=5*60*60*1000)
```

より大きい単位に変換する場合、小数点以下は切り捨てられるようです。
上記は、下記のようにしても同様の結果を得られます。

```java
long seconds = TimeUnit.HOURS.convert(1, TimeUnit.SECONDS);
long millis = TimeUnit.HOURS.convert(5, TimeUnit.MILLISECONDS);
```

`TimeUtil` クラスのオブジェクトは、`java.util.concurrent` パッケージのクラスでパラメータとしてよく使われています。

```java
Executor executor = Executor.newSingleThreadExecutor();
Future<Result> future = executor.submit(new Callable<Result>() {...});
Result result = future.get(100, TimeUnit.MILLISECONDS);  // 100ミリ秒待って結果取得
```

