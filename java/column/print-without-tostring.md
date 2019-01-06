---
title: "System.out.println() でオブジェクトを出力するときは toString() しない"
date: "2005-06-27"
---

任意のオブジェクトの内容を `System.out.println` で出力する場合は、オブジェクトそのものを渡してしまうのが一番楽で安全です。
あらかじめ対象オブジェクトの `toString()` を実行し、得られた文字列を `System.out.println` に渡している例を見かけますが、あまりメリットはありません。

~~~ java
System.out.println(obj.toString());  // toString で NullPointerException の可能性
System.out.println(obj);             // obj が null なら null と表示してくれる
~~~

`System.out.println`（実際は `PrintStream#println`）は、`null` が渡された場合に `null` という文字列を出力してくれるようになっています。
自力で `toString()` する場合は、`toString()` 実行時の `NullPointerException` を考慮しておかなければいけませんが、オブジェクトをそのまま `System.out.println` に渡すようにすれば、そのようなケアは必要なくなります（もちろん、`null` と表示されても問題なければですが）。

ちなみに、`PrintStream#println(Object)` の実装は下記のような感じになっています。

~~~ java
public void println(Object x) {
    String s = String.valueOf(x);
    synchronized (this) {
        print(s);
        newLine();
    }
}
~~~

上記の、`String.valueOf(x)` は、`x` が `null` である場合に `null` という文字列を返すので、結果的に `System.out.println` は `null` という文字列を出力することになります。

