---
title: "Python で SQLite データベースを扱う"
url: "p/g5xg2nc/"
date: "2007-01-25"
tags: ["Python", "SQLite"]
aliases: /python/sqlite.html
---

Python は標準ライブラリとして __`sqlite3`__ モジュールを備えています。

SQLite3 データベースへの接続
----

__`sqlite3.connect`__ 関数でデータベースファイルを指定すると、SQLite3 データベースへの接続（`sqlite3.Connection` オブジェクト）を取得できます。
指定したデータベースファイルが存在しない場合は新規に作成されます。

{{< code lang="python" title="SQLite DB へ接続する" >}}
import sqlite3

# Create a Connection object.
conn = sqlite3.connect("sample.db")
{{< /code >}}


テーブルの作成
----

SQL コマンドを実行するには、__`Connection#cursor`__ メソッドで `sqlite3.Cursor` オブジェクトを作成し、__`execute`__ メソッドを呼び出します。
次の例では、データベースに新しい `memo` テーブルを作成しています。

{{< code lang="python" title="memo テーブルを作成する" >}}
# Create a Cursor object from Connection object.
cur = conn.cursor()

# Create a table
cur.execute("""CREATE TABLE memo (date TEXT, title TEXT, body TEXT)""")
{{< /code >}}


テーブルにレコードを追加
----

テーブルを作成するのと同様に、__`Cursor#execute`__ メソッドを使ってレコードを追加できます。

{{< code lang="python" title="レコードを追加する" >}}
# Insert a record
cur.execute("""INSERT INTO memo VALUES('2007-01-01', 'Memo1', 'Body1')""")
{{< /code >}}

DB-API の __`?`__ 文字によるパラメータ置換（プレースホルダ機能）を使えば、各列の値を保持したタプルを渡すことによってレコードを追加することができます。

```python
t = ("2007-01-01", "Memo1", "Body1")
cur.execute("INSERT INTO memo VALUES(?,?,?)", t)
```

例えば、下記のようにループでタプルを処理すれば、複数のレコードを一度に追加できます。

```python
tuples = (
    ("2007-01-01", "Memo1", "Body1"),
    ("2007-01-02", "Memo2", "Body2"),
    ("2007-01-03", "Memo3", "Body3"),
)

for t in tuples:
    cur.execute("INSERT INTO memo VALUES(?,?,?)", t)
```

一方で、以下のように __`%`__ を使って SQL クエリ文字列を作成してしまうと、SQL インジェクション攻撃に対して脆弱性が残ってしまう可能性が高いので、DB-API の `?` を使ったパラメータ置換を行うようにしたほうが安全です。

{{< code lang="python" title="危険な方法" >}}
t = ("2007-01-01", "Memo1", "Body1")
cur.execute("INSERT INTO memo VALUES('%s','%s','%s')", t)
{{< /code >}}


テーブルからレコードを検索
----

`Cursor#execute` メソッドで SELECT 文を実行した後は、`Cursor` オブジェクトをイテレータとして用いることにより、検索結果を順番に取得していくことができます。

### レコード検索の基本（Cursor によるイテレート）

{{< code lang="python" title="sample.py" >}}
cur = conn.cursor()
cur.execute("SELECT * FROM memo")
for row in cur:
    print(row)
{{< /code >}}

{{< code lang="python" title="実行結果" >}}
(u"2007-01-01", u"Memo1", u"Body1")
(u"2007-01-02", u"Memo2", u"Body2")
(u"2007-01-03", u"Memo3", u"Body3")
{{< /code >}}

### 検索結果をひとつだけ取得する (Cursor#fetchone())

__`Cursor#fetchone`__ メソッドを使うと、検索結果を 1 つだけタプルで取得できます。

{{< code lang="python" title="sample.py" >}}
cur.execute("SELECT * FROM memo")
result = cur.fetchone()
print(result)
{{< /code >}}

{{< code lang="python" title="実行結果" >}}
(u"2007-01-01", u"Memo1", u"Body1")
{{< /code >}}

### 検索結果をリストで取得する (Cursor#fetchall())

`Cursor` オブジェクトを使って検索結果をイテレートするのではなく、リストデータとして一度に読み取ってしまうこともできます。

{{< code lang="python" title="sample.py" >}}
cur.execute("SELECT * FROM memo")
results = cur.fetchall()
print(results)
{{< /code >}}

{{< code lang="python" title="実行結果" >}}
[(u"2007-01-01", u"Memo1", u"Body1"), (u"2007-01-02", u"Memo2", u"Body2"), (u"2007-01-03", u"Memo2", u"Body3")]
{{< /code >}}

### 最大 n 個の検索結果をリストで取得する (Cursor#fetchmany(n))

__`Cursor#fetchmany`__ メソッドを使用すると、指定した数だけレコードを取得できます。

{{< code lang="python" title="sample.py" >}}
cursor.execute("SELECT * FROM memo")
print(cursor.fetchmany(2))
{{< /code >}}

{{< code lang="python" title="実行結果" >}}
[(u"Title 1", u"Body 1"), (u"Title 2", u"Body 2")]
{{< /code >}}

