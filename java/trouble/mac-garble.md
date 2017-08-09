---
title: Mac で javac の出力が文字化けする
created: 2011-03-24
---

Mac で `javac` コマンドのエラーメッセージが文字化けする場合は、`javac` が日本語メッセージを `Shift-JIS` で出力しようとしている可能性が高いです。

以下のように環境変数を設定して英語メッセージにしてしまうか、

~~~
export LC_ALL=en
~~~

`javac` のオプションで以下のように UTF8 エンコーディングを指定すると文字化けを防ぐことができます。

~~~
$ javac -J-Dfile.encoding=UTF8 HogeHoge.java
~~~

#### 実行例

~~~
$ LC_ALL=en javac Test.java
Test.java:5: unreported exception java.io.IOException; must be caught or declared to be thrown
        throw new IOException();
        ^
1 error
~~~

~~~
$ javac -J-Dfile.encoding=UTF8 Test.java
Test.java:5: 例外 java.io.IOException は報告されません。スローするにはキャッチまたは、スロー宣言をしなければなりません。
        throw new IOException();
        ^
エラー 1 個
~~~

