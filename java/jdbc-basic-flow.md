---
title: JDBC による DB 操作の流れ
date: "2010-08-29"
---

JDBC を使ったデータベースの処理は、以下のような流れで進んでいきます。

1. Class.forName() を使って任意の JDBC ドライバを登録
2. DriverManager を使って DB コネクションを確立（```Connection``` オブジェクトを取得）
3. Connection オブジェクトを使って SQL statement を作成（```Statement``` オブジェクトを作成）
4. Statement オブジェクトを使ってクエリを実行し、実行結果を取得（```ResultSet``` オブジェクトを取得）

```Connection```, ```Statement```, ```ResultSet``` は、処理が終わったら ```close()``` する必要があります。
各オブジェクトのオープンとクローズの関係を、分かりやすいようにインデントで対にして示すと以下のようになります。

```
あらかじめ Class.forName(...) で JDBC ドライバを登録しておく

Connection connection = DirverManager.getConnection(...)
    Statement statement = connection.createStatement()
        ResultSet resultSet = statement.executeQuery(...)
            resultSet を使った処理
        resultSet.close()
    statement.close()
connection.close()
```

上記の例では、SQL 文を実行するために ```Statement.executeQuery()``` を呼んでいますが、実行する SQL の種類によってメソッドが異なります。

* INSERT, DELETE, UPDATE 文を実行する場合
  * ```int Statement#executeUpdate(...)```
* SELECT 文を実行する場合
  * ```ResultSet Statement#executeStatement(...)```

Statement と ResultSet のオープン、クローズ関係
====
```Statement``` がクローズされると、その ```Statement``` からオープンされた ```ResultSet``` も自動的にクローズされます（API 仕様）。
1 つの ```Statement``` から複数の ```ResultSet``` を同時にオープンすることはできません。
すでに ```ResultSet``` をオープンしている状態で、```Statement``` の ```execution``` メソッドを呼び出すと、自動的にその ```ResultSet``` はクローズされ、新しい ```ResultSet``` が生成されます。

