---
title: "JDBC でプレースホルダを使用して SQL クエリを作成する"
date: "2010-09-06"
---

SQL によるクエリ文字列を生成するときに、プレースホルダの仕組みを利用すると、クエリ文字列の中の任意の部分に対して、後付けで値を設定することができます。
下記のクエリでは、```?``` の部分が置き換え可能であることを示しています。

```sql
INSERT INTO memos VALUES(?, ?);
```

JDBC では、```PreparedStatement``` オブジェクトを使ってプレースホルダを実現することができます。
```PreparedStatement``` オブジェクトは、```Connection``` クラスの ```prepareStatement``` メソッドを呼び出すことで作成します。
```prepareStatement``` に渡したクエリ文字列の中の ```?``` に対応する値は、```setXXX(index, value)``` という形のメソッドで後から設定することができます。
```index``` は入れ替え対象の ```?``` の位置を示しており、1 から数えることに注意してください。

```java
// import java.sql.Connection;
// import java.sql.PreparedStatement;

final String sql = "INSERT INTO memos VALUES(?, ?);";
PreparedStatement stmt = conn.prepareStatement(sql);
try {
    for (int i = 0; i < 5; ++i) {
        stmt.setString(1, "ID " + i);
        stmt.setString(2, "Title " + i);
        stmt.execute();
    }
} finally {
    stmt.close();
}
```

```PrearedStatement``` を使用する 副次的な効果として、文字列結合によって SQL 文を作成するよりも、SQL インジェクションなどの攻撃に対して強くなるという利点もあります。

