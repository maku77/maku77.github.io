---
title: Python で SQLite データベースを使用する
created: 2007-01-25
---

python では標準で **sqlite3 モジュール**を使用することができます。

SQLite3 データベースへの接続
====
`sqlite3.connect()` でデータベースファイルを指定すると、データベースへの接続（`sqlite3.Connection` オブジェクト）を取得できます。
指定したデータベースファイルが存在しない場合は新規に作成されます。

```python
import sqlite3

# Create a 'Connection' object.
conn = sqlite3.connect('sample.db')
```


テーブルの作成
====
SQL コマンドを実行するには、`Connection#cursor()` により `sqlite3.Cursor` オブジェクトを作成し、`execute()` メソッドを呼び出します。
次の例では、データベースに新しいテーブル `memo` を作成しています。

```python
# Create a 'Cursor' object from 'Connection' object.
cur = conn.cursor()

# Create a table
cur.execute('''CREATE TABLE memo
    (date TEXT, title TEXT, body TEXT)''')
```


テーブルにレコードを追加
====
テーブルを作成するのと同様に、`Cursor#execute()` を使ってレコードを追加できます。

```python
# Insert a record
cur.execute("""INSERT INTO memo
    VALUES('2007-01-01', 'Memo1', 'Body1')""")
```

DB-API の `?` 文字によるパラメータ置換（プレースホルダ機能）を使えば、各列の値を保持したタプルを渡すことによってレコードを追加することができます。

```
t = ('2007-01-01', 'Memo1', 'Body1')
cur.execute('INSERT INTO memo VALUES(?,?,?)', t)
```

例えば、下記のようにループで tuple を処理すれば、複数のレコードを一度に追加できます。

```
for t in (('2007-01-01', 'Memo1', 'Body1'),
    ('2007-01-02', 'Memo2', 'Body2'),
    ('2007-01-03', 'Memo3', 'Body3')):
    cur.execute('INSERT INTO memo VALUES(?,?,?)', t)
```

一方で、以下のように `%` を使って SQL クエリ文字列を作成してしまうと、SQL インジェクション攻撃に対して脆弱性が残ってしまう可能性が高いので、DB-API の `?` を使ったパラメータ置換を行うようにしたほうが安全です。

#### よくない例
```python
t = ('2007-01-01', 'Memo1', 'Body1')
cur.execute("INSERT INTO memo VALUES('%s','%s','%s')", t)
```


テーブルからレコードを検索
====
`Cursor#execute()` により SELECT 文を実行した後は、`Cursor` オブジェクトをイテレータとして用いることにより、検索結果を順番に取得していくことができます。


レコード検索の基本（Cursor によるイテレート）
----
#### sample.py
```python
cur = conn.cursor()
cur.execute('SELECT * FROM memo')
for row in cur:
    print(row)
```

#### 実行結果
```python
(u'2007-01-01', u'Memo1', u'Body1')
(u'2007-01-02', u'Memo2', u'Body2')
(u'2007-01-03', u'Memo3', u'Body3')
```


検索結果をひとつだけ取得する (Cursor#fetchone())
----
`Cursor#fetchone()` を使うと、検索結果を 1 つだけタプルで取得できます。

#### sample.py
```python
cur.execute('SELECT * FROM memo')
result = cur.fetchone()
print(result)
```

#### 実行結果
```python
(u'2007-01-01', u'Memo1', u'Body1')
```


検索結果をリストで取得する (Cursor#fetchall())
----
`Cursor` オブジェクトを使って検索結果をイテレートするのではなく、リストデータとして一度に読み取ってしまうこともできます。

#### sample.py
```python
cur.execute('SELECT * FROM memo')
results = cur.fetchall()
print(results)
```

#### 実行結果
```python
[(u'2007-01-01', u'Memo1', u'Body1'), (u'2007-01-02', u'Memo2', u'Body2'), (u'2007-01-03', u'Memo2', u'Body3')]
```

最大 n 個の検索結果をリストで取得する (Cursor#fetchmany(n))
----
`Cursor#fetchmany` メソッドを使用すると、指定した数だけレコードを取得できます。

```python
>>> cursor.execute('SELECT * FROM memo')
>>> print(cursor.fetchmany(2))
[(u'Title 1', u'Body 1'), (u'Title 2', u'Body 2')]
```

