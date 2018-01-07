---
title: リソースは finally ブロックで閉じる
date: "2010-08-28"
---

データベース接続に限らず、I/O 処理にかかわるオブジェクトを使用し終わった後は、通常はリソース解放するための明示的な close 処理が必要です。
例外機構をもつ言語において、close 処理は finally ブロック内で実行するのがセオリーです。
そうしないと、例外発生時に close が実行されないケースが出てきてしまいます。

このあたりの具体例は、下記のサイトでまとめられています。
この記事では、セマフォの解放も finally ブロック内で行うべきと記述されています。

* [Javaの理論と実践: 良いハウスキーピング習慣を身につける](http://www.ibm.com/developerworks/jp/java/library/j-jtp03216/index.html)

例えば、Java の JDBC では、オープンした Connection オブジェクト、Statement オブジェクトを明示的に閉じる必要があります。
JDBC を使ったプログラミングでは、これらのオブジェクトの閉じ忘れによる不具合が非常に多いようです。
JDBC ドライバの実装はベンダによって様々なので、ある実装ではコネクションの閉じ忘れによってリークが発生するのに、別の実装では発生しなかったりすることがあります。
こういった事情により、潜在的なリソースリークの不具合が含まれていることに気付かないことが多いのです。
幸いにも、最近の静的解析ツールでは、このような DB コネクションの閉じ忘れを検出してくれるようになっています。

具体的な例として、JDBC の Connection オブジェクト、Statement オブジェクトを閉じるコードは以下のような try ～ finally 構成になっていなければいけません。
ここでは SQLException をメソッドの呼び出し元に伝搬させていますが、もちろんメソッド内でハンドルしてしまっても構いません。

```java
public void doSomething() throws SQLException {
    Connection con = getConnection();
    Statement stmt = null;
    try {
        stmt = con.createStatement();
        ResultSet resultSet = stmt.executeQuery("SELECT * FROM tbl");
        // ... Use resultSet here ...
    }
    finally {
        try {
            if (stmt != null) {
                stmt.close();
            }
        }
        finally {
            con.close();
        }
    }
}
```

上記のサンプルコードでは、Connection のオープン自体に失敗した場合は、finally ブロックを実行する必要がないので、Connection のオープン処理は try ブロックの外で行っています。
ResultSet は Statement が閉じられるときに自動的に閉じられると Java API 仕様で明記されているので、それを信じて、上記では明示的に ResultSet のクローズ処理は行っていません。
