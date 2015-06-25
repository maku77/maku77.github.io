---
title: 可変オブジェクトのメンバ参照を返さない
created: 2015-05-27
---

メンバ変数として可変オブジェクト (mutable object) を持っているクラスが、getter メソッドでその参照をそのまま返してしまうと、クラス外部からオブジェクトの内容を変更されてしまいます。
プライベートなメンバを外部から守るためにカプセル化しているはずなのに、これでは実際には何も守られておらず、外部からそのクラスの振る舞いを壊されてしまいます。

例えば、下記の `Program` クラスは、メンバ変数として `Date` オブジェクトを持ち、getter メソッドでその参照を返しています。

```java
import java.util.Date;

public class Program {
    private final Date startTime = new Date();
    public Date getStartTime() {
        return startTime;
    }
}
```

Java の Date オブジェクトには、`setTime()` メソッドが存在するため、可変オブジェクトです。
可変オブジェクトの参照を外部から取得できるようにすると、その参照を通して値を変更されてしまいます。

```java
Program program = new Program();
System.out.println(program.getStartTime());  // Wed Jan 1 01:11:54 JST 2014

// メンバ変数への参照を取得し、値を書き換える
Date date = program.getStartTime();
date.setTime(date.getTime() + 1000000);

System.out.println(program.getStartTime());  // Wed Jan 1 01:28:34 JST 2014（破壊された）
```

このような外部からのオブジェクト破壊を防ぐには、プリミティブな値、あるいは不変オブジェクト (immutable object) の参照を返すようにします。

```java
public long getStartTime() {
    return startTime.getTime();
}
```

ちなみに、Java の ```String``` は不変オブジェクトなので return しても問題ありません。

Java には、任意のコレクションを変更できないようにするためのユーティリティが用意されています。
例えば、オブジェクト内部の List オブジェクトを getter で返さないといけない場合は、下記のようにすることで外部からの破壊を防ぐことができます。

```java
public List<Book> getRecommendedBookList() {
    return Collections.unmodifiableList(recommendedBooks);
}
```

